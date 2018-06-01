package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewType extends ImageView implements Cloneable {
	
	public ImageViewType(Image image) {
	        super(image);
	}
	

	public ImageViewType clone() throws CloneNotSupportedException {
		return (ImageViewType) super.clone();
	}
}
