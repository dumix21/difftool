package gui;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class DirectoryViewer {
	
	private static DirectoryViewer directory_instance = null;
	
	File firstDir;
	File lastDir;
	
	public Button getLeftPath() {
		return leftPath;
	}

	public Button getRightPath() {
		return rightPath;
	}

	public Button leftPath = new Button("Get path1");
	public Button rightPath = new Button("Get path2");
	
	public TreeViewHelper getLeftHelper() {
		return leftHelper;
	}

	public void setLeftHelper(TreeViewHelper leftHelper) {
		this.leftHelper = leftHelper;
	}

	public TreeItem<Object> getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(TreeItem<Object> leftParent) {
		this.leftParent = leftParent;
	}

	public TreeViewHelper getRightHelper() {
		return rightHelper;
	}

	public void setRightHelper(TreeViewHelper rightHelper) {
		this.rightHelper = rightHelper;
	}

	public TreeItem<Object> getRightParent() {
		return rightParent;
	}

	public void setRightParent(TreeItem<Object> rightParent) {
		this.rightParent = rightParent;
	}

	TreeViewHelper leftHelper = new TreeViewHelper();
	TreeItem<Object> leftParent = new TreeItem<Object>();
	
	TreeViewHelper rightHelper = new TreeViewHelper();
	TreeItem<Object> rightParent = new TreeItem<Object>();
	
	final TextField rightText = new TextField("");
	final TextField leftText = new TextField("");
	
	public TextField getRightText() {
		return rightText;
	}

	public TextField getLeftText() {
		return leftText;
	}

	public File getFirstDir() {
		return firstDir;
	}

	public void setFirstDir(File firstDir) {
		this.firstDir = firstDir;
	}

	public File getLastDir() {
		return lastDir;
	}

	public void setLastDir(File lastDir) {
		this.lastDir = lastDir;
	}

	public static DirectoryViewer getInstance() {
		if(directory_instance == null) {
			directory_instance = new DirectoryViewer();
		}
		
		return directory_instance;
	}
	
	public GridPane getDirectoryView(Stage window) {
		GridPane grid = new GridPane();
		

		
		
		
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(9);
		
		
		leftText.setPromptText("Introduce the path here");
		leftText.setPrefColumnCount(52);
		leftText.getText();
		GridPane.setConstraints(leftText, 0, 0);
		grid.getChildren().add(leftText);
		
		
		GridPane.setConstraints(leftPath, 1, 0);
		grid.getChildren().add(leftPath);
		
		GridPane.setConstraints(leftHelper.getTreeView(), 0, 1, 2, 1);
		grid.getChildren().add(leftHelper.getTreeView());
		leftHelper.getTreeView().setPrefSize(window.getWidth()/2-30, 600);
		
		
		
		rightText.setPromptText("Introduce the path here");
		rightText.setPrefColumnCount(52);
		rightText.getText();
		GridPane.setConstraints(rightText, 2, 0);
		grid.getChildren().add(rightText);
		
		
		GridPane.setConstraints(rightPath, 3, 0);
		grid.getChildren().add(rightPath);
		
		GridPane.setConstraints(rightHelper.getTreeView(), 2, 1, 2, 1);
		rightHelper.getTreeView().setPrefSize(window.getWidth()/2-30, 600);
		grid.getChildren().add(rightHelper.getTreeView());
		
		final FileAndDirectoryController leftControler = new FileAndDirectoryController(leftText, leftHelper, window, leftParent);
		leftPath.setOnAction(leftControler.getHandler());
		
		final FileAndDirectoryController rightControler = new FileAndDirectoryController(rightText, rightHelper, window, rightParent);
		rightPath.setOnAction(rightControler.getHandler());
		
		
		return grid;
	}
}