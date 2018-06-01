package gui;

import javafx.scene.image.Image;

class ImageFactory{

	private static ImageFactory image_instance = null;

	final ImageViewType only_in = new ImageViewType(new Image("missing.png"));
	final ImageViewType identical = new ImageViewType(new Image("ok.png"));
	final ImageViewType different = new ImageViewType(new Image("warning.gif"));

	public ImageViewType getImage(final DIFFTYPE type) {
		if (type.equals(DIFFTYPE.ONLY_IN)) {
			return only_in;
		} else if (type.equals(DIFFTYPE.IDENTICAL)) {
			return identical;
		} else {
			return different;
		}
	}

	public static ImageFactory getInstance() {
		if (image_instance == null) {
			image_instance = new ImageFactory();
		}

		return image_instance;
	}

}
