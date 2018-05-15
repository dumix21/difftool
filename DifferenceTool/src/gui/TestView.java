package gui;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TestView extends Application{
	public static void main(String args[]) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TreeViewHelper helper = new TreeViewHelper();
//		TreeView tree = new TreeView();
		CheckBoxTreeItem<String> parent = new CheckBoxTreeItem<String>();
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File("d:");
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(primaryStage);
		
		
		helper.createTree(selectedDirectory, parent);
		helper.displayTreeView(selectedDirectory.toString());
		
		VBox root = new VBox();
		root.getChildren().add(helper.getTreeView());
		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
	}

}
