package gui;

import java.io.File;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuBarFactory {
	
	boolean optionDirectory = false; // For file false; For directories true
	DirectoryViewer directoryView = DirectoryViewer.getInstance();;
	GridPane directoryPane = new GridPane();
	GridPane filePane = new GridPane();
	
	public MenuBar getMenuBar(final BorderPane layout, final Stage window) {
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
		
		// By default, the tool is set up for files type
		file.setSelected(true);

		menuOptions.getItems().addAll(file, folder);

		file.setToggleGroup(tGroup);
		folder.setToggleGroup(tGroup);
		
		/**
		 * Each time we select file option from menu bar, 
		 * a pane with two buttons for selecting paths for files
		 * and two text areas will be returned and set in the main layout
		 */
		file.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				layout.getChildren().remove(directoryPane);
				optionDirectory = false;

				FileViewer fileView = FileViewer.getInstance();
				filePane = fileView.changePaneToInputs(window);
				
//				filePane = FileViewer.getInstance().changePaneToDiffs(window);
//				layout.setCenter(filePane);

				layout.setCenter(filePane);

			}

		});
		
		/**
		 * Each time we select directory option from menu bar, 
		 * a pane with two buttons for selecting paths for directories
		 * and two tree viewers will be returned and set in the main layout
		 */
		folder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				layout.getChildren().remove(filePane);
				optionDirectory = true;

				directoryPane = directoryView.getDirectoryView(window);

				layout.setCenter(directoryPane);

			}

		});
		
		/**
		 * action for info section
		 */
		about.setOnAction(new EventHandler<ActionEvent>() {

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
		
		/**
		 * This action is handled in two ways:
		 * 1. directories comparison
		 * 2. files comparison
		 */
		compare.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				/**
				 * First branch is used for directories
				 * if option for directories is set as true and both directories are chosen
				 * those tree views will be compared
				 */
				if (optionDirectory && ((directoryView.getFirstDir().isDirectory() && (directoryView.getLastDir().isDirectory())))) {
					TreeView<Object> tv1 = directoryView.getLeftHelper().getTreeView();
					TreeView<Object> tv2 = directoryView.getRightHelper().getTreeView();
					DirectoriesComaprison compare = new DirectoriesComaprison(directoryView.getFirstDir(), directoryView.getLastDir(), tv1,
							tv2);
					try {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								/**
								 * Comparison for chosen directories
								 */
								try {
									
						            compare.getDiff(compare.getLeftDir(), compare.getRightDir());
						                   
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								/**
								 * This map is used to store compared files from those two directories compared
								 * The key is given by file name and the value by difference
								 * Value can be:
								 * 1. identical, if the checkSum( you can see this function in DirectorieComparison class)
								 * 	  for both files is equal and have the same name
								 * 
								 * 2. only in,   if a file was found only in one directory
								 * 
								 * 3. different, if files have the same name, but the content is different
								 * 
								 */
								HashMap<String, String> newMap = compare.getMapDiff();
								
								directoryView.getProgressBar().setProgress(0);
								directoryView.getProgressIndicator().setProgress(0);
								
								DirectoriesDifferences dd = new DirectoriesDifferences();
								
								TreeViewHelper treeHelper = new TreeViewHelper();
								TreeView<Object> auxView = directoryView.rightHelper.getTreeView();
								
								/**
								 * After the trees were filtered by differences, the new ones
								 * will replace the old ones
								 */
								dd.markDifferences(auxView,directoryView.leftHelper.getTreeView(), newMap, new File(directoryView.getRightText().toString()));
//								treeHelper.setTreeView((dd.markDifferences(auxView, newMap,
//										new File(directoryView.getRightText().toString()))));
								treeHelper.setTreeView(dd.returningFirstTree());
								directoryView.setRightHelper(treeHelper);

//								auxView = directoryView.leftHelper.getTreeView();
//								dd.markDifferences(auxView, newMap, new File(directoryView.getLeftText().toString()));
//								treeHelper.setTreeView(dd.markDifferences(auxView, newMap,
//										new File(directoryView.getLeftText().toString())));
								treeHelper.setTreeView(dd.returningSecondTree());
								directoryView.setLeftHelper(treeHelper);
								
								directoryView.getProgressBar().progressProperty().unbind();
								directoryView.getProgressBar().progressProperty().bind(dd.progressProperty());
								directoryView.getProgressIndicator().progressProperty().unbind();
								 
					               // Bind progress property.
								directoryView.getProgressIndicator().progressProperty().bind(dd.progressProperty());
					 
					               // Unbind text property for Label.
								directoryView.getStatusLabel().textProperty().unbind();
					 
					               // Bind the text property of Label
					                // with message property of Task
								directoryView.getStatusLabel().textProperty().bind(dd.messageProperty());
								
								dd.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
					                       new EventHandler<WorkerStateEvent>() {
					 
					                           @Override
					                           public void handle(WorkerStateEvent t) {
					                               directoryView.getStatusLabel().textProperty().unbind();
					                               directoryView.getStatusLabel().setText("Loaded: " + DirectoriesComaprison.count);
					                           }});
								
					            new Thread(dd).start();
					            
								
								
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (!optionDirectory) {
					filePane = FileViewer.getInstance().changePaneToDiffs(window);
					layout.setCenter(filePane);
				}

			}

		});

		
		return menuBar;
	}
}
