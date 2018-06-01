package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DiffGUI extends Application {

	Stage window;
	Scene primaryScene;
	GridPane filePane = new GridPane();
	BorderPane layout = new BorderPane();

	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setMaximized(true);

		primaryScene = new Scene(layout);

		/**
		 * Adding the menu bar on the top of layout
		 */
		MenuBarFactory menuBar = new MenuBarFactory();
		layout.setTop(menuBar.getMenuBar(layout, window));

		/**
		 * As default, the files comparison pane is set
		 */
		FileViewer fileView = FileViewer.getInstance();
		filePane = fileView.getDefaultFileView(window);

		layout.setCenter(filePane);

		window.setTitle("Diff Tool");
		window.setScene(primaryScene);
		window.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
