package guiDiff;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiffPanel extends Application {
	 final static Menu menu1 = new Menu("File");
	 final static Menu menu2 = new Menu("Options");
	 final static Menu menu3 = new Menu("Help");

	public static void main(String[] args) {
		 launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Diff Tool");
		StackPane rootPane = new StackPane();
		GridPane root = new GridPane();
		root.setPadding(new Insets(3));
		
		root.setHgap(5);
		root.setVgap(5);
		
		Label leftLink = new Label("Left Link");
		Label rightLink = new Label("Right Link");
		root.add(leftLink,0,3);
		root.add(rightLink, 1, 3);

        MenuBar menuBar = new MenuBar();

        VBox vBox = new VBox(menuBar);

        
        rootPane.getChildren().setAll(vBox,root);
        Scene scene = new Scene(rootPane, 960, 600);
        Scene scene1 = new Scene(rootPane, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		
	}

}
