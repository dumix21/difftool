package algorithms.diff;

public class DiffObj {
	
	private int startPosition;
	private int endPosition;
        private DiffObj_Type diffType;
       
        //TODO: eliminarea setterelor deoarece obiectul nu mai este modificat dupa creatie
	
	public DiffObj(int startPosition, int endPosition, DiffObj_Type diffType) {		
		this.startPosition = startPosition;
		this.endPosition = endPosition;
                this.diffType = diffType;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
        
        public DiffObj_Type getDiffType(){
            return diffType;
        }
        
        public void setDiffType(DiffObj_Type diffType){
            this.diffType = diffType;
        }
        
        

}
