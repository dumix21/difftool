package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DiffGUI extends Application {

	Stage window;
	Scene primaryScene;
	GridPane directoryPane = new GridPane();
	GridPane filePane = new GridPane();
	DirectoryViewer directoryView;
	
	boolean type = false; // For file false; For directories true
	
	
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setMaximized(true);
		
		BorderPane layout = new BorderPane();
		primaryScene=new Scene(layout);
		
		MenuBar menuBar = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");
        Menu menuOptions = new Menu("Options");
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        menuHelp.getItems().add(about);
        
        menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);
        MenuItem compare = new MenuItem("Compare");
        menuFile.getItems().add(compare);
        RadioMenuItem file = new RadioMenuItem("File");
        RadioMenuItem folder = new RadioMenuItem("Directory");
        ToggleGroup tGroup = new ToggleGroup();
        
        
        layout.setTop(menuBar);
        
        file.setToggleGroup(tGroup);
        folder.setToggleGroup(tGroup);
        
        file.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
				layout.getChildren().remove(directoryPane);
				type = false;
				
				FileViewer fileView = FileViewer.getInstance();
				filePane = fileView.getFileView(window);
				
				layout.setCenter(filePane);
				
			}
        	
        });
        
        folder.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
				layout.getChildren().remove(filePane);
				type = true;
				
				directoryView = DirectoryViewer.getInstance();
				directoryPane = directoryView.getDirectoryView(window);
				
				layout.setCenter(directoryPane);
				
			}
        	
        });
        
        // By default, the tool is set up for files type
        file.setSelected(true);
        
        menuOptions.getItems().addAll(file,folder);
        
        FileViewer fileView = FileViewer.getInstance();
		filePane = fileView.getFileView(window);
		
		layout.setCenter(filePane);
        
		
		window.setTitle("Diff Tool");
		window.setScene(primaryScene);
		window.show();
		
		about.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About Diff Tool");
				alert.setHeaderText("DiffTool is the visual file comparison (diff). Use it to compare source code,"
						+ " \n web pages and other text files with native application performance.");
				alert.setContentText("From \"Options\" menu item you can select what type of comparison do you need."
						+ "\nFor file/folder selection, you should paste the absolute path and that click on "
						+ "\n\"Get path\" button for importing the file or use just the button to select "
						+ "\nmanually.");

				alert.showAndWait();
				
			}
			
		});
		
		compare.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO Implement handler for diff generator
				DirectoryViewer dw = DirectoryViewer.getInstance();
				if(type&&((dw.getFirstDir().isDirectory()&&(dw.getLastDir().isDirectory())))) {
					TreeView<Object> tv1 = directoryView.getLeftHelper().getTreeView();
					TreeView<Object> tv2 = directoryView.getRightHelper().getTreeView();
					DirectoriesComaprison compare = new DirectoriesComaprison(dw.getFirstDir(), dw.getLastDir(), tv1, tv2);
					try
					{
						compare.getDiff(compare.getLeftDir(),compare.getRightDir());
					}
					catch(IOException ie)
					{
						ie.printStackTrace();
					}
				}
				
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
