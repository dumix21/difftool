package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class ComparisonProperties extends Application {
	
	boolean default_SetUp = true;
	boolean filePermission = false;
	boolean dirPermission = false;
	boolean chooseComparisonTypes = false;
	
	TitledPane fileTitled;
	TitledPane dirTitled;
	
	RadioButton defaultSetUp;
	RadioButton customize;
	RadioButton currentDir;
	RadioButton allDirs;
	RadioButton allFiles;
	RadioButton customFiles;
	
	CheckBox code;
	CheckBox a_v;
	CheckBox txt;
	
	Button submit;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		final ToggleGroup group = new ToggleGroup();
		final ToggleGroup dirGroup = new ToggleGroup();
		final ToggleGroup fileGroup = new ToggleGroup();
		
		BorderPane layout = new BorderPane();
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setHgap(5);
		grid.setVgap(5);
		layout.setCenter(grid);
		
		currentDir = new RadioButton("Current Directory");
		currentDir.setToggleGroup(dirGroup);
		allDirs = new RadioButton("Recursively through all");
		allDirs.setToggleGroup(dirGroup);
		allFiles = new RadioButton("Compare all files");
		allFiles.setToggleGroup(fileGroup);
		customFiles = new RadioButton("Choose types");
		customFiles.setToggleGroup(fileGroup);
		a_v = new CheckBox("Audio/Video");
		code = new CheckBox(".java/.cpp/.h");
		txt = new CheckBox(".txt/.doc");
		
		GridPane dirPane = new GridPane();
		dirPane.setVgap(4);
		dirPane.setHgap(4);
		dirPane.setPadding(new Insets(5,5,5,5));
		dirPane.add(currentDir, 1, 0);
		dirPane.add(allDirs, 0, 0);
		dirTitled = new TitledPane("Directories", dirPane);
//		setPermissionForSetUp(true);
//		initializeState();
		
		GridPane filePane = new GridPane();
		filePane.setVgap(4);
		filePane.setHgap(4);
		filePane.setPadding(new Insets(5,5,5,5));
		filePane.add(allFiles, 0, 0);
		filePane.add(customFiles, 1, 0);
		filePane.add(a_v, 1, 1);
		filePane.add(code, 1, 2);
		filePane.add(txt, 1, 3);
		fileTitled = new TitledPane("Files", filePane);
		
		setPermissionForSetUp(true);
		
		defaultSetUp = new RadioButton("Default set up");
		defaultSetUp.setToggleGroup(group);
		defaultSetUp.setSelected(true);

		customize = new RadioButton("Customize");
		customize.setToggleGroup(group);
		
		submit = new Button("Submit changes");
		
		GridPane.setConstraints(defaultSetUp, 0, 0);
		GridPane.setConstraints(customize, 0, 1);
		GridPane.setConstraints(dirTitled, 1, 2);
		GridPane.setConstraints(fileTitled, 1, 3);
		GridPane.setConstraints(submit, 1, 4);
		grid.getChildren().addAll(defaultSetUp, customize, dirTitled, fileTitled, submit);
		
		primaryStage.setHeight(300);
		primaryStage.setWidth(400);
		primaryStage.setTitle("Customize your comparison preferences");
		Scene scene= new Scene(layout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
			
		});
		
		customFiles.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				a_v.setDisable(false);
				txt.setDisable(false);
				code.setDisable(false);
				chooseComparisonTypes = false;
				
			}
			
		});
		
		allFiles.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				chooseComparisonTypes = true;
				a_v.setDisable(true);
				txt.setDisable(true);
				code.setDisable(true);
				
			}
			
		});
		
		customize.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setPermissionForSetUp(false);
			}
			
		});
		
		defaultSetUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setPermissionForSetUp(true);
			}
			
		});
		
	}
	
	public void initializeState() {
		dirTitled.setDisable(true);
		fileTitled.setDisable(true);
	}
	
	public void setPermissionForSetUp(final boolean value) {
		default_SetUp = value;
		dirPermission = !value;
		filePermission = !value;
		dirTitled.setDisable(value);
		fileTitled.setDisable(value);
		allDirs.setSelected(true);
		allFiles.setSelected(true);
		a_v.setDisable(true);
		txt.setDisable(true);
		code.setDisable(true);
	}
	

}
