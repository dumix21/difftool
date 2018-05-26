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
	
	/**
	 * 
	 * @return
	 * This two getters are used handle the event
	 */
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
	
	public void setLeftPath(Button leftPath) {
		this.leftPath = leftPath;
	}

	public void setLeftText(String leftText) {
		this.leftText = new TextField(leftText);
	}
	
	public void setRightText(String rightText) {
		this.rightText = new TextField(rightText);
	}

	TextField rightText = new TextField("");
	TextField leftText = new TextField("");
	
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
	
	/**
	 * 
	 * @param window
	 * @return
	 * This function returns a pane which contains :
	 * 1. Two button for getting the paths
	 * 2. Two text fields in which is set the corresponding path
	 * 3. Two trees for representing the parent directories
	 */
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
		
		/**
		 * Event handlers for each button
		 */
		final FileAndDirectoryController leftControler = new FileAndDirectoryController(leftText, leftHelper, window, leftParent);
		leftPath.setOnAction(leftControler.getHandler());
		
		final FileAndDirectoryController rightControler = new FileAndDirectoryController(rightText, rightHelper, window, rightParent);
		rightPath.setOnAction(rightControler.getHandler());
		
		final FileAndDirectoryController leftCopy = new FileAndDirectoryController("left", leftHelper, window, leftParent);
		leftText.setOnKeyPressed(leftCopy.getKeyHandler());
		
		final FileAndDirectoryController rightCopy = new FileAndDirectoryController("right", rightHelper, window, rightParent);
		rightText.setOnKeyPressed(rightCopy.getKeyHandler());
		
		return grid;
	}
}
