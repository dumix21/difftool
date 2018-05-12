package gui;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DiffGUI extends Application {

	Stage window;
	Scene primaryScene;
	/**
	 *  Values : true  - for files
	 *  	     false - for directories
	 */
	boolean comparationType;
	FileChooser leftFile;
	FileChooser rightFile;
	TextFileReader reader = new TextFileReader();
	TextArea leftTextArea = new TextArea();
	TextArea rightTextArea = new TextArea();
	
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setMaximized(true);
		GridPane grid = new GridPane();
		
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
        RadioMenuItem folder = new RadioMenuItem("Folder");
        ToggleGroup tGroup = new ToggleGroup();
        
        
        layout.setTop(menuBar);
        
        file.setToggleGroup(tGroup);
        folder.setToggleGroup(tGroup);
        
        // By default, the tool is set up for files type
        file.setSelected(true);
        comparationType=true;
        
        
        menuOptions.getItems().addAll(file,folder);
		
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(9);
		
		final TextField leftText = new TextField();
		leftText.setPromptText("Introduce the path here");
		leftText.setPrefColumnCount(52);
		leftText.getText();
		GridPane.setConstraints(leftText, 0, 0);
		grid.getChildren().add(leftText);
		
		Button leftPath = new Button("Get path");
		GridPane.setConstraints(leftPath, 1, 0);
		grid.getChildren().add(leftPath);
		
		
		GridPane.setConstraints(leftTextArea, 0, 1, 2, 1);
		grid.getChildren().add(leftTextArea);
		leftTextArea.setPrefSize(window.getWidth()/2-30, 600);
		
		
		final TextField rightText = new TextField();
		rightText.setPromptText("Introduce the path here");
		rightText.setPrefColumnCount(52);
		rightText.getText();
		GridPane.setConstraints(rightText, 2, 0);
		grid.getChildren().add(rightText);
		
		Button rightPath = new Button("Get path");
		GridPane.setConstraints(rightPath, 3, 0);
		grid.getChildren().add(rightPath);
		
		GridPane.setConstraints(rightTextArea, 2, 1, 2, 1);
		rightTextArea.setPrefSize(window.getWidth()/2-30, 600);
		grid.getChildren().add(rightTextArea);
		
		layout.setCenter(grid);
		window.setTitle("Diff Tool");
		window.setScene(primaryScene);
		window.show();
		
		leftPath.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				leftFile = new FileChooser();
				File file = leftFile.showOpenDialog(window);
				leftText.setText(file.getAbsolutePath());
				
				leftTextArea.setText(null);
				for(String s:reader.read(file)) {
					leftTextArea.appendText(s+"\n");
				}
				
			}
			
		});
		
		rightPath.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				rightFile = new FileChooser();
				File file = rightFile.showOpenDialog(window);
				rightText.setText(file.getAbsolutePath());
				
				rightTextArea.setText(null);
				for(String s:reader.read(file)) {
					rightTextArea.appendText(s+"\n");
				}
			}
			
		});
		
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
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
