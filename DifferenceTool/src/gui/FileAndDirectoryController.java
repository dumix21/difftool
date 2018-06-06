package gui;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Marius class used event handlers for files/directories comparison
 */
public class FileAndDirectoryController {

	private EventHandler<ActionEvent> EH;
	private EventHandler<KeyEvent> KeyEH;
	File selectedDirectory;
	private final String sPath ="D:\\";

	public File getSelectedDirectory() {
		return selectedDirectory;
	}

	public void setSelectedDirectory(final File selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
	}

	/**
	 * 
	 * @param text
	 * @param helper
	 * @param window
	 * @param parent
	 * 
	 *            Handler for directories
	 */
	public FileAndDirectoryController(final TextField text, final TreeViewHelper helper, final Stage window,
			final TreeItem<Object> parent) {
		EH = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryViewer dw = DirectoryViewer.getInstance();
				String ob = event.getSource().toString();

				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setInitialDirectory(new File(sPath));
				selectedDirectory = chooser.showDialog(window);

				if (selectedDirectory != null) {

					text.setText(selectedDirectory.getAbsolutePath());

					helper.createTree(selectedDirectory, parent);
					helper.displayTreeView(selectedDirectory.toString());

					if (ob.contains("Get path1")) {
						dw.setFirstDir(new File(dw.getLeftText().getText()));
					} else {
						dw.setLastDir(new File(dw.getRightText().getText()));
					}

				}

			}

		};
	}

	/**
	 * 
	 * @param text
	 * @param textArea
	 * @param window
	 * 
	 *            Handler for files
	 */
	public FileAndDirectoryController(final TextField text, @SuppressWarnings("rawtypes") final TableView textArea, final Stage window, ObservableList<FileText> oList) {
		EH = new EventHandler<ActionEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(ActionEvent event) {
//				TextFileReader reader = new TextFileReader();
				FileChooser fileChoose;
				fileChoose = new FileChooser();
				File file = fileChoose.showOpenDialog(window);
				text.setText(file.getAbsolutePath());
				
				FileTextTable ftt = new FileTextTable();
				ftt.setNumber(textArea, file);
				for(FileText f:ftt.getData()) {
					oList.add(f);
					System.out.println(f.toString());
				}
//				textArea.setText(null);
//				for (String s : reader.read(file)) {
//					textArea.appendText(s + "\n");
//				}
			}

		};
	}

	/**
	 * 
	 * @param textName
	 * @param helper
	 * @param window
	 * @param parent
	 * 
	 *            Handler for directories (copy path method)
	 */
	public FileAndDirectoryController(final String textName, final TreeViewHelper helper, final Stage window,
			final TreeItem<Object> parent) {
		KeyEH = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					DirectoryViewer dw = DirectoryViewer.getInstance();
					File file;
					String path;
					if (textName.equals("left")) {
						path = dw.getLeftText().getText();
					} else {
						path = dw.getRightText().getText();
					}
					file = new File(path);
					if (file.isDirectory()) {

						selectedDirectory = file;
						helper.createTree(selectedDirectory, parent);
						helper.displayTreeView(selectedDirectory.toString());

						if (textName.equals("left")) {
							dw.setFirstDir(new File(dw.getLeftText().getText()));
						} else {
							dw.setLastDir(new File(dw.getRightText().getText()));
						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setContentText("Please chose a directory for " + textName + " path");
						alert.showAndWait();
						if (textName.equals("left")) {
							dw.setLeftText("");
						} else {
							dw.setRightText("");
						}
					}
				}

			}

		};
	}

	/**
	 * 
	 * @param text
	 * @param window
	 * @param textArea
	 * 
	 *            Handler for files ( copy path method)
	 */
	public FileAndDirectoryController(final String text, final Stage window, @SuppressWarnings("rawtypes") final TableView textArea, ObservableList<FileText> oList) {
		KeyEH = new EventHandler<KeyEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					FileViewer fw = FileViewer.getInstance();
					File file;
					String path;
					if (text.equals("left")) {
						path = fw.getLeftText().getText();
					} else {
						path = fw.getRightText().getText();
					}
					file = new File(path);

					FileTextTable ftt = new FileTextTable();
					ftt.setNumber(textArea, file);
					for(FileText f:ftt.getData()) {
						oList.add(f);
					}

				}
			}

		};
	}

	public EventHandler<KeyEvent> getKeyHandler() {
		return KeyEH;
	}

	public EventHandler<ActionEvent> getHandler() {
		return EH;
	}
}
