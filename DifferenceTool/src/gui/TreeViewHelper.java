package gui;

import java.io.File;


import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

public class TreeViewHelper {
	TreeView<String> treeView = new TreeView<String>();
	public void createTree(File file, CheckBoxTreeItem<String> parent) {
	    if (file.isDirectory()) {
	        CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(file.getName());
	        parent.getChildren().add(treeItem);
	        for (File f : file.listFiles()) {
	            createTree(f, treeItem);
	        }
	    } else if ("txt".equals((file.toString().substring(file.toString().lastIndexOf(".") + 1)))) {
	        parent.getChildren().add(new CheckBoxTreeItem<>(file.getName()));
	    }
	}
	
	public void displayTreeView(String inputDirectoryLocation) {
	    // Creates the root item.
	    CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>(inputDirectoryLocation);

	    // Hides the root item of the tree view.
	    treeView.setShowRoot(false);

	    // Creates the cell factory.
	    treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

	    // Get a list of files.
	    File fileInputDirectoryLocation = new File(inputDirectoryLocation);
	    File fileList[] = fileInputDirectoryLocation.listFiles();

	    // create tree
	    for (File file : fileList) {
	        createTree(file, rootItem);
	    }

	    treeView.setRoot(rootItem);
	}

	public TreeView<String> getTreeView() {
		return treeView;
	}
	
	

}
