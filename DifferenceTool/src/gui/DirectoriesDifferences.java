package gui;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

public class DirectoriesDifferences extends Task<List<String>> {
	ImageFactory image = ImageFactory.getInstance();
	
	static File path;
	TreeView<Object> tree;
	TreeView<Object> secondTree;
	TreeItem<Object> leftRoot;
	TreeItem<Object> rightRoot;
	HashMap<String, DIFFTYPE> diffMap;
	static int maxValue = DirectoriesComaprison.count;
	static int currentValue = 0;

	public static File getPath() {
		return path;
	}

	public static void setPath(final File path) {
		DirectoriesDifferences.path = path;
	}

	public void markDifferences(TreeView<Object> tree, TreeView<Object> second,
			final HashMap<String, DIFFTYPE> diffMap, final File f) {
		this.tree = tree;
		this.diffMap = diffMap;
		this.secondTree = second;
		leftRoot=tree.getRoot();
		rightRoot=secondTree.getRoot();
	}

	public TreeView<Object> returningFirstTree() {
		tree.refresh();
		return tree;
	}

	public TreeView<Object> returningSecondTree() {
		secondTree.refresh();
		return secondTree;
	}

	/**
	 * 
	 * @param parent
	 * @param differencesMap
	 * 
	 *            Recursive function used to traverse all directories/files from the
	 *            parent directory
	 * @throws Exception
	 */
	public void diffTree(TreeItem<Object> parent, final HashMap<String, DIFFTYPE> differencesMap)
			throws Exception {
		/**
		 * This function is used only for showing files differences Leaf = File || empty directory
		 */
		if (parent.isLeaf()) {

			/**
			 * A new graphic will be set depending of map value entry
			 */

			DIFFTYPE type = differencesMap.get(parent.getValue());
			if (type.equals(DIFFTYPE.NEW_DIR)) {
				parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.EMPTY_DIR)));
			} else if (type.equals(DIFFTYPE.IDENTICAL)) {
				parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.IDENTICAL)));
			} else if (type.equals(DIFFTYPE.ONLY_IN)) {
				parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.ONLY_IN)));
			} else if (type.equals(DIFFTYPE.DIFFERENT)) {
				parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.DIFFERENT)));
			}

			currentValue++;
			this.load(parent.getValue().toString());
			this.updateProgress(currentValue, maxValue);
			
		} else {
			if(!rootCheck(parent)) {
				DIFFTYPE tip = differencesMap.get(parent.getValue());
				if(tip.equals(DIFFTYPE.DIFFERENT_DIR)) {
					parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.DIFFERENT_DIR)));
				}else if(tip.equals(DIFFTYPE.IDENTICAL_DIR)) {
					parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.IDENTICAL_DIR)));
				}else {
					parent.setGraphic(new ImageView(image.getImage(DIFFTYPE.NEW_DIR)));
				}
			}
			
				for (TreeItem<Object> treeItem : parent.getChildren()) {
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

	private void load(String file) throws Exception {
		this.updateMessage("Loading: " + file);
		Thread.sleep(200);
	}
	
	public boolean rootCheck(TreeItem<Object> root) {
		if(root.equals(leftRoot)||root.equals(rightRoot)) {
			return true;
		}
		return false;
	}

}
