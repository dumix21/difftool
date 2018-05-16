package gui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

class DirectoryViewer {
	
	private static DirectoryViewer directory_instance = null;
	
	public static DirectoryViewer getInstance() {
		if(directory_instance == null) {
			directory_instance = new DirectoryViewer();
		}
		
		return directory_instance;
	}
	
	public GridPane getDirectoryView(Stage window) {
		GridPane grid = new GridPane();
		
		TreeViewHelper leftHelper = new TreeViewHelper();
		CheckBoxTreeItem<String> leftParent = new CheckBoxTreeItem<String>();
		
		TreeViewHelper rightHelper = new TreeViewHelper();
		CheckBoxTreeItem<String> rightParent = new CheckBoxTreeItem<String>();
		
		
		
		
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
		
		GridPane.setConstraints(leftHelper.getTreeView(), 0, 1, 2, 1);
		grid.getChildren().add(leftHelper.getTreeView());
		leftHelper.getTreeView().setPrefSize(window.getWidth()/2-30, 600);
		
		
		final TextField rightText = new TextField();
		rightText.setPromptText("Introduce the path here");
		rightText.setPrefColumnCount(52);
		rightText.getText();
		GridPane.setConstraints(rightText, 2, 0);
		grid.getChildren().add(rightText);
		
		Button rightPath = new Button("Get path");
		GridPane.setConstraints(rightPath, 3, 0);
		grid.getChildren().add(rightPath);
		
		
		GridPane.setConstraints(rightHelper.getTreeView(), 2, 1, 2, 1);
		rightHelper.getTreeView().setPrefSize(window.getWidth()/2-30, 600);
		grid.getChildren().add(rightHelper.getTreeView());
		
		leftPath.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Select first directory");
				File defaultDirectory = new File("d:");
				chooser.setInitialDirectory(defaultDirectory);
				File selectedDirectory = chooser.showDialog(window);

				leftText.setText(selectedDirectory.getAbsolutePath());
				
				
				leftHelper.createTree(selectedDirectory, leftParent);
				leftHelper.displayTreeView(selectedDirectory.toString());
				
			}
			
		});
		
		rightPath.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Select second directory");
				File defaultDirectory = new File("d:");
				chooser.setInitialDirectory(defaultDirectory);
				File selectedDirectory = chooser.showDialog(window);

				rightText.setText(selectedDirectory.getAbsolutePath());
				
				
				rightHelper.createTree(selectedDirectory, rightParent);
				rightHelper.displayTreeView(selectedDirectory.toString());
			}
			
		});
		
		return grid;
	}
}
