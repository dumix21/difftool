package gui;

import javafx.scene.image.Image;

class ImageFactory{

	private static ImageFactory image_instance = null;

	final Image only_in = new Image("missing.png");
	final Image identical = new Image("ok.png");
	final Image different = new Image("warning.gif");
	
	final Image identicFolder = new Image("folderOk.png");
	final Image missingFolder = new Image("ms.png");
	final Image emptyFolder = new Image("zero.png");
	final Image diffFolder = new Image("ms.png");

	public Image getImage(final DIFFTYPE type) {
		if (type.equals(DIFFTYPE.ONLY_IN)) {
			return only_in;
		} else if (type.equals(DIFFTYPE.IDENTICAL)) {
			return identical;
		} else if(type.equals(DIFFTYPE.DIFFERENT)) {
			return different;
		} else if (type.equals(DIFFTYPE.IDENTICAL_DIR)) {
			return identicFolder;
		}else if(type.equals(DIFFTYPE.EMPTY_DIR)) {
			return emptyFolder;
		}else if(type.equals(DIFFTYPE.DIFFERENT_DIR)) {
			return diffFolder;
		}else {
			return emptyFolder;
		}
	}

	public static ImageFactory getInstance() {
		if (image_instance == null) {
			image_instance = new ImageFactory();
		}

		return image_instance;
	}

}
