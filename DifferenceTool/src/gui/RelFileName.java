package gui;

public class RelFileName {
	String sName;
	String sPath;
	
	public RelFileName(final String sName, final String sPath) {
		this.sName=sName;
		this.sPath=sPath;
	}
	
	public boolean equals(final RelFileName obj) {
		if(this.sName.equals(obj.sName)) {
			if(this.sPath.equals(obj.sPath)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
