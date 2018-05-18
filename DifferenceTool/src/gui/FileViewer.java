package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

class FileViewer {
	
	private static FileViewer file_instance = null;
	
	public static FileViewer getInstance() {
		if(file_instance == null) {
			file_instance = new FileViewer();
		}
		
		return file_instance;
	}
	
	public GridPane getFileView(Stage window) {
		
		TextArea leftTextArea = new TextArea();
		TextArea rightTextArea = new TextArea();
		
		GridPane grid = new GridPane();
		
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
		
		Button previous = new Button("<< Previous");
		Group bg = new Group();
		
		Button next = new Button(" Next >>     ");
		
		bg.getChildren().addAll(previous,next);
		
		HBox buttonsPane = new HBox();
		
		buttonsPane.getChildren().add(previous);
		buttonsPane.getChildren().add(next);
		
		buttonsPane.setAlignment(Pos.CENTER);
		GridPane.setConstraints(buttonsPane, 0, 2, 4, 1);
		
		grid.getChildren().add(buttonsPane);
		
		FileAndDirectoryController leftController = new FileAndDirectoryController(leftText, leftTextArea, window);
		leftPath.setOnAction(leftController.getHandler());
		
		FileAndDirectoryController rightController = new FileAndDirectoryController(rightText, rightTextArea, window);
		rightPath.setOnAction(rightController.getHandler());
		
		
		
		return grid;
	}
}
