package gui;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DirectoriesDifferences extends Task<List<String>> {
	ImageFactory image = new ImageFactory();
	
	static File path;
	TreeView<Object> tree;
	TreeView<Object> secondTree;
	HashMap<String, DIFFTYPE> diffMap;
	static int maxValue = DirectoriesComaprison.count;
	static int currentValue = 0;

	public static File getPath() {
		return path;
	}

	public static void setPath(final File path) {
		DirectoriesDifferences.path = path;
	}

	public void markDifferences(final TreeView<Object> tree, final TreeView<Object> second,
			final HashMap<String, DIFFTYPE> diffMap, final File f) {
		this.tree = tree;
		this.diffMap = diffMap;
		this.secondTree = second;
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
	public void diffTree(final TreeItem<Object> parent, final HashMap<String, DIFFTYPE> differencesMap)
			throws Exception {
		/**
		 * This function is used only for showing files differences Leaf = File
		 */
		if (parent.isLeaf()) {

			/**
			 * A new graphic will be set depending of value map entry
			 */

			DIFFTYPE type = differencesMap.get(parent.getValue());
			if (type == null) {
				System.out.println("Eroare " + parent.getValue());
			} else if (type.equals(DIFFTYPE.IDENTICAL)) {
				parent.setGraphic(image.getImage(DIFFTYPE.IDENTICAL).clone());
			} else if (type.equals(DIFFTYPE.ONLY_IN)) {
				parent.setGraphic(image.getImage(DIFFTYPE.ONLY_IN).clone());
			} else if (type.equals(DIFFTYPE.DIFFERENT)) {
				parent.setGraphic(image.getImage(DIFFTYPE.DIFFERENT).clone());
			}

			currentValue++;
			this.load(parent.getValue().toString());
			this.updateProgress(currentValue, maxValue);
		} else {
			if (parent.getChildren() != null) {
				for (TreeItem<Object> treeItem : parent.getChildren()) {
					diffTree(treeItem, differencesMap);
				}
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

}
