package gui;

import javafx.scene.text.Text;

public class FileText {

    private String text;
    @SuppressWarnings("unused")
	private Text txt = new Text();
    

    FileText(String value) {
        this.text = value;
    }
    
    public FileText(Text txt){
    	this.txt = txt;
    }

    public String getFirstName() {
        return text;
    }

    public void setFirstName(String value) {
    text=value;
    }
    
    public String toString() {
    	return text;
    }

    
}