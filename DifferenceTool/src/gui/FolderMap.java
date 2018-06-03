package gui;

import java.io.File;
import java.util.HashMap;

public class FolderMap extends HashMap <String, File> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isEqual = true;
    public void SetNotEqual() {
        isEqual = false;
    }
    public boolean IsEqual() {
        return isEqual;
    }
}
