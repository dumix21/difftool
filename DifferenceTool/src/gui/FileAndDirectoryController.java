package gui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Marius
 *  class used event handlers for files/directories comparison
 */
public class FileAndDirectoryController {
	
		private EventHandler<ActionEvent> EH;
		File selectedDirectory;
		
		public File getSelectedDirectory() {
			return selectedDirectory;
		}

		public void setSelectedDirectory(File selectedDirectory) {
			this.selectedDirectory = selectedDirectory;
		}
		
		/**
		 * 
		 * @param text
		 * @param helper
		 * @param window
		 * @param parent
		 * 
		 * Handler for directories
		 */
		public FileAndDirectoryController(TextField text, TreeViewHelper helper, Stage window,TreeItem<Object> parent) {
			EH = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					DirectoryViewer dw = DirectoryViewer.getInstance();
					String ob = event.getSource().toString();
					
					DirectoryChooser chooser = new DirectoryChooser();
					selectedDirectory = chooser.showDialog(window);

					text.setText(selectedDirectory.getAbsolutePath());
					
					helper.createTree(selectedDirectory, parent);
					helper.displayTreeView(selectedDirectory.toString());
					
					if(ob.contains("Get path1")) {
						dw.setFirstDir(new File(dw.getLeftText().getText()));
					}
					else {
						dw.setLastDir(new File(dw.getRightText().getText()));
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
		 * Handler for files
		 */
		public FileAndDirectoryController(TextField text, TextArea textArea, Stage window) {
			EH = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TextFileReader reader = new TextFileReader();
					FileChooser leftFile;
					leftFile = new FileChooser();
					File file = leftFile.showOpenDialog(window);
					text.setText(file.getAbsolutePath());
					
					textArea.setText(null);
					for(String s:reader.read(file)) {
						textArea.appendText(s+"\n");
					}
				}
				
			};
		}
		
		public EventHandler<ActionEvent> getHandler() {
			return EH;
		}
	}
