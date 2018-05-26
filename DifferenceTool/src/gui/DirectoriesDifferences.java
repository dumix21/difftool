package gui;

import java.io.File;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DirectoriesDifferences {
	
	static File path;
	
	public static File getPath() {
		return path;
	}

	public static void setPath(File path) {
		DirectoriesDifferences.path = path;
	}

	public TreeView<Object> markDifferences(TreeView<Object> tree, HashMap<String, String> diffMap, File f){
		DirectoriesDifferences df = new DirectoriesDifferences();
		df.diffTree(tree.getRoot(), diffMap);
		tree.refresh();
		return tree;
	}
	
	/**
	 * 
	 * @param parent
	 * @param differencesMap
	 * 
	 * Recursive function used to traverse all directories/files from the parent directory
	 */
	public void diffTree(TreeItem<Object> parent, HashMap<String, String> differencesMap) {
		/**
		 * This function is used only for showing files differences
		 *  Leaf = File
		 */
		if(parent.isLeaf()) {
			
			/**
			 * A new graphic will be set depending of value map entry
			 */
			
			String type = differencesMap.get(parent.getValue());
			if(type.equals("identical")){
				ImageView img = new ImageView(new Image("ok.png"));
				parent.setGraphic(img);
			}else if(type.contains("only in")) {
				ImageView img = new ImageView(new Image("missing.png"));
				parent.setGraphic(img);
			}else if(type.equals("different")) {
				ImageView img = new ImageView(new Image("warning.gif"));
				parent.setGraphic(img);
			}
		}
		else {
			for(TreeItem<Object> treeItem : parent.getChildren()) {
				diffTree(treeItem, differencesMap);
			}
		}
	}
	
	
}
