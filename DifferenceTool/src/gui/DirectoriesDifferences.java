package gui;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DirectoriesDifferences extends Task<List<String>> {
	
	static File path;
	TreeView<Object> tree;
	TreeView<Object> secondTree;
	HashMap<String, String> diffMap;
	static int maxValue = DirectoriesComaprison.count;
	static int currentValue = 0;
	
	public static File getPath() {
		return path;
	}

	public static void setPath(final File path) {
		DirectoriesDifferences.path = path;
	}

	public void markDifferences(final TreeView<Object> tree,final TreeView<Object> second, final HashMap<String, String> diffMap, final File f){
		this.tree = tree;
		this.diffMap = diffMap;
		this.secondTree = second;
	}
	
	public TreeView<Object> returningFirstTree(){
		tree.refresh();
		return tree;
	}
	
	public TreeView<Object> returningSecondTree(){
		secondTree.refresh();
		return secondTree;
	}
	
	/**
	 * 
	 * @param parent
	 * @param differencesMap
	 * 
	 * Recursive function used to traverse all directories/files from the parent directory
	 * @throws Exception 
	 */
	public void diffTree(final TreeItem<Object> parent, final HashMap<String, String> differencesMap) throws Exception {
		/**
		 * This function is used only for showing files differences
		 *  Leaf = File
		 */
		if(parent.isLeaf()) {
			
			/**
			 * A new graphic will be set depending of value map entry
			 */
			
			final String type = differencesMap.get(parent.getValue());
			if(type==null) {
				System.out.println("Eroare " + parent.getValue());
			}
			else if(type.equals("identical")){
				ImageView img = new ImageView(new Image("ok.png"));
				parent.setGraphic(img);
			}else if(type.contains("only in")) {
				ImageView img = new ImageView(new Image("missing.png"));
				parent.setGraphic(img);
			}else if(type.equals("different")) {
				ImageView img = new ImageView(new Image("warning.gif"));
				parent.setGraphic(img);
			}
			
			currentValue++;
			this.load(parent.getValue().toString());
			this.updateProgress(currentValue, maxValue);
		}
		else {
			for(TreeItem<Object> treeItem : parent.getChildren()) {
				diffTree(treeItem, differencesMap);
			}
		}
		
		
		
	}

	@Override
	protected List<String> call() throws Exception {
		this.diffTree(tree.getRoot(), diffMap);
		this.diffTree(secondTree.getRoot(), diffMap);
		return null;
	}
	
	private void load(String file) throws Exception{
		this.updateMessage("Loading: "+file);
		Thread.sleep(200);
	}
	
}
