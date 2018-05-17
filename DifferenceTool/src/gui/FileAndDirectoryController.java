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

public class FileAndDirectoryController {

		private EventHandler<ActionEvent> EH;
		
		//Get path handler for directories
		public FileAndDirectoryController(TextField text, TreeViewHelper helper, Stage window,TreeItem<Object> parent) {
			EH = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					DirectoryChooser chooser = new DirectoryChooser();
					File selectedDirectory = chooser.showDialog(window);

					text.setText(selectedDirectory.getAbsolutePath());
					
					helper.createTree(selectedDirectory, parent);
					helper.displayTreeView(selectedDirectory.toString());
					
				}
				
			};
		}
		
		//Get path handler for files
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
