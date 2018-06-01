package gui;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class DirectoryViewer {

	private static DirectoryViewer directory_instance = null;

	File firstDir;
	File lastDir;

	final Label label = new Label("Load files:");
	final Button startButton = new Button("Start");
	ProgressBar progressBar = new ProgressBar(0);
	ProgressIndicator progressIndicator = new ProgressIndicator(0);
	Label statusLabel = new Label();

	TextField rightText = new TextField("");
	TextField leftText = new TextField("");
	
	public Button leftPath = new Button("Get path1");
	public Button rightPath = new Button("Get path2");
	
	TreeViewHelper leftHelper = new TreeViewHelper();
	TreeItem<Object> leftParent = new TreeItem<Object>();

	TreeViewHelper rightHelper = new TreeViewHelper();
	TreeItem<Object> rightParent = new TreeItem<Object>();

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(final ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

	public void setProgressIndicator(final ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(final Label statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * 
	 * @return This two getters are used handle the event
	 */
	public Button getLeftPath() {
		return leftPath;
	}

	public Button getRightPath() {
		return rightPath;
	}

	public TreeViewHelper getLeftHelper() {
		return leftHelper;
	}

	public void setLeftHelper(final TreeViewHelper leftHelper) {
		this.leftHelper = leftHelper;
		leftHelper.refresh();
	}

	public TreeItem<Object> getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(final TreeItem<Object> leftParent) {
		this.leftParent = leftParent;
	}

	public TreeViewHelper getRightHelper() {
		return rightHelper;
	}

	public void setRightHelper(final TreeViewHelper rightHelper) {
		this.rightHelper = rightHelper;
		rightHelper.refresh();
	}

	public TreeItem<Object> getRightParent() {
		return rightParent;
	}

	public void setRightParent(final TreeItem<Object> rightParent) {
		this.rightParent = rightParent;
	}

	public void setLeftPath(final Button leftPath) {
		this.leftPath = leftPath;
	}

	public void setLeftText(final String leftText) {
		this.leftText = new TextField(leftText);
	}

	public void setRightText(final String rightText) {
		this.rightText = new TextField(rightText);
	}

	public TextField getRightText() {
		return rightText;
	}

	public TextField getLeftText() {
		return leftText;
	}

	public File getFirstDir() {
		return firstDir;
	}

	public void setFirstDir(final File firstDir) {
		this.firstDir = firstDir;
	}

	public File getLastDir() {
		return lastDir;
	}

	public void setLastDir(final File lastDir) {
		this.lastDir = lastDir;
	}

	public static DirectoryViewer getInstance() {
		if (directory_instance == null) {
			directory_instance = new DirectoryViewer();
		}

		return directory_instance;
	}

	/**
	 * 
	 * @param window
	 * @return This function returns a pane which contains : 1. Two button for
	 *         getting the paths 2. Two text fields in which is set the
	 *         corresponding path 3. Two trees for representing the parent
	 *         directories
	 */
	public GridPane getDirectoryView(final Stage window) {

		GridPane grid = new GridPane();

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(9);

		leftText.setPromptText("Introduce the path here");
		leftText.setMaxWidth(window.getWidth() / 2 - 102);
		leftText.setMinWidth(window.getWidth() / 2 - 102);
		leftText.getText();
		GridPane.setConstraints(leftText, 0, 0);
		grid.getChildren().add(leftText);

		GridPane.setConstraints(leftPath, 1, 0);
		grid.getChildren().add(leftPath);

		GridPane.setConstraints(leftHelper.getTreeView(), 0, 1, 2, 1);
		grid.getChildren().add(leftHelper.getTreeView());
		leftHelper.getTreeView().setPrefSize(window.getWidth() / 2 - 30, window.getHeight() - 150);

		rightText.setPromptText("Introduce the path here");
		// rightText.setPrefColumnCount(52);
		rightText.setMinWidth(window.getWidth() / 2 - 102);
		rightText.setMaxWidth(window.getWidth() / 2 - 102);
		rightText.getText();
		GridPane.setConstraints(rightText, 2, 0);
		grid.getChildren().add(rightText);

		GridPane.setConstraints(rightPath, 3, 0);
		grid.getChildren().add(rightPath);

		GridPane.setConstraints(rightHelper.getTreeView(), 2, 1, 2, 1);
		rightHelper.getTreeView().setPrefSize(window.getWidth() / 2 - 30, window.getHeight() - 150);
		grid.getChildren().add(rightHelper.getTreeView());

		statusLabel.setMinWidth(250);
		statusLabel.setTextFill(Color.BLUE);

		HBox progressPane = new HBox();
		progressPane.getChildren().addAll(label, progressBar, progressIndicator, statusLabel);
		progressPane.setSpacing(10);
		progressPane.setAlignment(Pos.CENTER);

		GridPane.setConstraints(progressPane, 0, 2, 4, 1);

		grid.getChildren().add(progressPane);

		/**
		 * Event handlers for each button
		 */
		final FileAndDirectoryController leftControler = new FileAndDirectoryController(leftText, leftHelper, window,
				leftParent);
		leftPath.setOnAction(leftControler.getHandler());

		final FileAndDirectoryController rightControler = new FileAndDirectoryController(rightText, rightHelper, window,
				rightParent);
		rightPath.setOnAction(rightControler.getHandler());

		final FileAndDirectoryController leftCopy = new FileAndDirectoryController("left", leftHelper, window,
				leftParent);
		leftText.setOnKeyPressed(leftCopy.getKeyHandler());

		final FileAndDirectoryController rightCopy = new FileAndDirectoryController("right", rightHelper, window,
				rightParent);
		rightText.setOnKeyPressed(rightCopy.getKeyHandler());

		return grid;
	}
}
