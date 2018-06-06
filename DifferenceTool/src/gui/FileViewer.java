package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import algorithms.diff.Diff;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.concurrent.Future;

class FileViewer {

	private static FileViewer file_instance = null;

	private final static String CSS_WHITE_BACKGROUND = "-fx-background: rgb(255,255,255);";

	@SuppressWarnings("unused")
	private ViewState viewState;

	private GridPane grid;

	@SuppressWarnings("rawtypes")
	private TableView leftTableView;
	@SuppressWarnings("rawtypes")
	private TableView rightTableView;
	
	 private  ObservableList<FileText> dataLeft;
	 private  ObservableList<FileText> dataRight;

	public void setDataLeft(ObservableList<FileText> dataLeft) {
		this.dataLeft = dataLeft;
	}

	public void setDataRight(ObservableList<FileText> dataRight) {
		this.dataRight = dataRight;
	}

	private TextFlow leftOutput;
	private TextFlow rightOutput;
	private TextFlow aggregateOutout;

	TextField leftText = new TextField();
	TextField rightText = new TextField();

	public TextField getLeftText() {
		return leftText;
	}

	public void setLeftText(final String leftText) {
		this.leftText = new TextField(leftText);
	}

	public TextField getRightText() {
		return rightText;
	}

	public void setRightText(final String rightText) {
		this.rightText = new TextField(rightText);
	}

	private FileViewer() {
		// By default, show text inputs
		viewState = ViewState.SHOW_INPUTS;
		initialize();
	}

	public static FileViewer getInstance() {
		if (file_instance == null) {
			file_instance = new FileViewer();
		}

		return file_instance;
	}

	private void initialize() {
		grid = new GridPane();

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(9);
	}

	// Default view for application start up
	public GridPane getDefaultFileView(final Stage window) {
		return getInputsViewState(window);
	}

	private GridPane getDiffsViewState(final Stage window, final TextFlow leftOutput, final TextFlow rightOutput,
			final TextFlow aggreggateOutput) {

		StackPane leftTextOutput = new StackPane();
		StackPane rightTextOutput = new StackPane();
		StackPane aggregateTextOutput = new StackPane();

		leftTextOutput.getChildren().add(leftOutput);
		rightTextOutput.getChildren().add(rightOutput);
		aggregateTextOutput.getChildren().add(aggregateOutout);

		// Left scroll pane configuration
		ScrollPane leftPane = new ScrollPane(leftTextOutput);
		GridPane.setConstraints(leftPane, 0, 0);
		grid.getChildren().add(leftPane);
		leftPane.setPrefSize(window.getWidth() / 2 - 30, 600);
		leftPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		leftPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);

		leftPane.setPadding(new Insets(20, 15, 15, 15));
		leftPane.setStyle(CSS_WHITE_BACKGROUND);

		// Right scroll pane configuration
		ScrollPane rightPane = new ScrollPane(rightTextOutput);
		GridPane.setConstraints(rightPane, 1, 0);
		grid.getChildren().add(rightPane);
		rightPane.setPrefSize(window.getWidth() / 2 - 30, 600);
		rightPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		rightPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);

		rightPane.setPadding(new Insets(20, 15, 15, 15));
		rightPane.setStyle(CSS_WHITE_BACKGROUND);

		// Aggregate scroll pane configuration
		ScrollPane aggregatePane = new ScrollPane(aggregateTextOutput);
		GridPane.setConstraints(aggregatePane, 0, 1);
		GridPane.setColumnSpan(aggregatePane, 2);
		grid.getChildren().add(aggregatePane);
		aggregatePane.setPrefSize(window.getWidth() - 70, 350);
		aggregatePane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		aggregatePane.setHbarPolicy(ScrollBarPolicy.ALWAYS);

		aggregatePane.setPadding(new Insets(30, 10, 10, 10));
		aggregatePane.setStyle(CSS_WHITE_BACKGROUND);

		return grid;
	}

	/**
	 * 
	 * @param window
	 * @return This function returns a pane which contains : 1. Two button for
	 *         getting the paths 2. Two text fields in which is set the
	 *         corresponding path 3. Two text areas for showing corresponding texts
	 */
	private GridPane getInputsViewState(final Stage window) {
		leftTableView = new TableView<>();
		rightTableView = new TableView<>();

		leftText.setPromptText("Introduce the path here");
		leftText.setPrefColumnCount(52);
		// leftText.setMaxWidth(window.getWidth()/2 - 103);
		// leftText.setMinWidth(window.getWidth()/2 - 103);

		leftText.getText();
		GridPane.setConstraints(leftText, 0, 0);
		grid.getChildren().add(leftText);

		Button leftPath = new Button("Get path");
		GridPane.setConstraints(leftPath, 1, 0);
		grid.getChildren().add(leftPath);

		GridPane.setConstraints(leftTableView, 0, 1, 2, 1);
		grid.getChildren().add(leftTableView);
		leftTableView.setPrefSize(window.getWidth() / 2 - 30, 600);

		rightText.setPromptText("Introduce the path here");
		rightText.setPrefColumnCount(52);
		// rightText.setMaxWidth(window.getWidth()/2 - 103);
		// rightText.setMinWidth(window.getWidth()/2 - 103);

		rightText.getText();
		GridPane.setConstraints(rightText, 2, 0);
		grid.getChildren().add(rightText);

		Button rightPath = new Button("Get path");
		GridPane.setConstraints(rightPath, 3, 0);
		grid.getChildren().add(rightPath);

		GridPane.setConstraints(rightTableView, 2, 1, 2, 1);
		rightTableView.setPrefSize(window.getWidth() / 2 - 30, 600);
		grid.getChildren().add(rightTableView);

		Button previous = new Button("<< Previous");
		Group bg = new Group();

		Button next = new Button(" Next >>     ");

		bg.getChildren().addAll(previous, next);

		HBox buttonsPane = new HBox();

		buttonsPane.getChildren().add(previous);
		buttonsPane.getChildren().add(next);

		buttonsPane.setAlignment(Pos.CENTER);
		GridPane.setConstraints(buttonsPane, 0, 2, 4, 1);

		grid.getChildren().add(buttonsPane);

		/**
		 * Event handler for each button
		 */
		FileAndDirectoryController leftController = new FileAndDirectoryController(leftText, leftTableView, window, dataLeft);
		leftPath.setOnAction(leftController.getHandler());

		FileAndDirectoryController rightController = new FileAndDirectoryController(rightText, rightTableView, window, dataRight);
		rightPath.setOnAction(rightController.getHandler());

		FileAndDirectoryController leftLink = new FileAndDirectoryController("left", window, leftTableView, dataLeft);
		leftText.setOnKeyPressed(leftLink.getKeyHandler());

		FileAndDirectoryController rightLink = new FileAndDirectoryController("right", window, rightTableView, dataRight);
		rightText.setOnKeyPressed(rightLink.getKeyHandler());

		return grid;
	}

	public GridPane changePaneToInputs(final Stage window) {
		initialize();
		viewState = ViewState.SHOW_INPUTS;
		return getInputsViewState(window);
	}

	public GridPane changePaneToDiffs(final Stage window) {
		initialize();
		viewState = ViewState.SHOW_DIFFS;

		// Compute Differences
		leftOutput = new TextFlow();
		rightOutput = new TextFlow();
		aggregateOutout = new TextFlow();

		Diff d1 = new Diff();
		Diff d2 = new Diff();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

		List<Callable<Boolean>> tasks = new ArrayList<>();
		tasks.add(() -> d1.processDiff(dataLeft, dataRight, leftOutput, rightOutput));
		tasks.add(() -> d2.processDiff(dataLeft, dataRight, aggregateOutout));
		try {
			List<Future<Boolean>> comparasions = executor.invokeAll(tasks);
			comparasions.parallelStream().forEach(c -> {
				try {
					c.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		return getDiffsViewState(window, leftOutput, rightOutput, aggregateOutout);
	}

	// TODO - no longer presents utility. Remove at a latter stage
	private enum ViewState {
		SHOW_INPUTS, SHOW_DIFFS,
	}
}
