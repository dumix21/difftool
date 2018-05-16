package gui;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.filechooser.FileSystemView;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TreeViewHelper {
	TreeView<Object> treeView = new TreeView<>();
	public void createTree(File file, TreeItem<Object> parent) {
	    if (file.isDirectory()) {
	    	
	    	javax.swing.Icon icon = FileSystemView.getFileSystemView().getSystemIcon( file );

            BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(), 
                icon.getIconHeight(), 
                BufferedImage.TYPE_INT_ARGB
            );
            icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
            
            Image fxImage = SwingFXUtils.toFXImage(
                    bufferedImage, null
                );
            ImageView imageView = new ImageView(fxImage);
	    	
	        TreeItem<Object> treeItem = new TreeItem<>(file.getName(), imageView);
	        
//	        treeItem.setExpanded(true);
	        
	        parent.getChildren().add(treeItem);
	        
	        for (File f : file.listFiles()) {
	            createTree(f, treeItem);
	        }
	    } else 
	    {
	    	javax.swing.Icon icon = FileSystemView.getFileSystemView().getSystemIcon( file );

            BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(), 
                icon.getIconHeight(), 
                BufferedImage.TYPE_INT_ARGB
            );
            icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
            
            Image fxImage = SwingFXUtils.toFXImage(
                    bufferedImage, null
                );
            ImageView imageView = new ImageView(fxImage);
	    	
	    	
	    	TreeItem<Object> newItem = new TreeItem<>(file.getName(), imageView);
	    	parent.getChildren().add(newItem);
	    }
	}
	
	public void displayTreeView(String inputDirectoryLocation) {
	    // Creates the root item.
		File inputFile = new File(inputDirectoryLocation);
		javax.swing.Icon icon = FileSystemView.getFileSystemView().getSystemIcon( inputFile );

        BufferedImage bufferedImage = new BufferedImage(
            icon.getIconWidth(), 
            icon.getIconHeight(), 
            BufferedImage.TYPE_INT_ARGB
        );
        icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        
        Image fxImage = SwingFXUtils.toFXImage(
                bufferedImage, null
            );
        ImageView imageView = new ImageView(fxImage);
        
	    TreeItem<Object> rootItem = new TreeItem<>(inputDirectoryLocation, imageView);//

	    // Hides the root item of the tree view.
	    treeView.setShowRoot(true);

	    // Creates the cell factory.
	    treeView.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
	        @Override
	        public TreeCell<Object> call(TreeView<Object> p) {
	            return new TextFieldTreeCell<Object>(new StringConverter<Object>(){

	                @Override
	                public String toString(Object object) {
	                    return object.toString();
	                }

	                @Override
	                public Object fromString(String string) {
	                    return new Object();
	                }
	            });
	        }
	    });

	    // Get a list of files.
	    File fileInputDirectoryLocation = new File(inputDirectoryLocation);
	    File fileList[] = fileInputDirectoryLocation.listFiles();

	    // create tree
	    for (File file : fileList) {
	        createTree(file, rootItem);
	    }
	    rootItem.setExpanded(true);

	    treeView.setRoot(rootItem);
	}

	public TreeView<Object> getTreeView() {
		return treeView;
	}
	
	

}
