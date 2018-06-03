package gui;

import javafx.scene.image.Image;

class ImageFactory{

	private static ImageFactory image_instance = null;

	final Image only_in = new Image("missing.png");
	final Image identical = new Image("ok.png");
	final Image different = new Image("warning.gif");
	final Image diffFolder = new Image("differentFolder.png");
	final Image identicFolder = new Image("identicalFolder.png");

	public Image getImage(final DIFFTYPE type, final boolean isFile) {
		if (type.equals(DIFFTYPE.ONLY_IN) && isFile) {
			return only_in;
		} else if (type.equals(DIFFTYPE.IDENTICAL) && isFile) {
			return identical;
		} else if(type.equals(DIFFTYPE.DIFFERENT) && isFile) {
			return different;
		} else if (type.equals(DIFFTYPE.IDENTICAL) && !isFile) {
			return identicFolder;
		} else {
			return diffFolder;
		}
	}

	public static ImageFactory getInstance() {
		if (image_instance == null) {
			image_instance = new ImageFactory();
		}

		return image_instance;
	}

}
