package algorithms.diff;

import java.util.Vector;

import algorithms.lcs.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import options.Options;

public class Diff {
	
	//Vectorul diferentelor dintre doua siruri;	
	private Vector<DiffObj> differences;       
        private Options options;
        private int minimumLenght;	

	public Diff() {
		options = new Options();
        minimumLenght = 1;
	}        
    
        public Options getOptions(){
            return options;
        }
        
        public void setMinimumLenght (int minimumLenght ){
            this.minimumLenght = minimumLenght;
        }
        
  
        
        //Prin aceasta supraincarcare in primul text se coloreaza insertiile
        //iar in al doilea text deletiile
        public boolean processDiff(final String sComp1,final String sComp2, TextFlow s1_output, TextFlow s2_output){
            
            String s1 = options.processInputs(sComp1);
            String s2 = options.processInputs(sComp2);                        
            
        //Se extrag diferentele dintre cele doua siruri    
            processWithLCS(s1,s2);       
        
        //Contruiesc un vector de deletii si un vector de insertii
        Vector<DiffObj> deletions = new Vector<DiffObj>();
        Vector<DiffObj> insertions = new Vector<DiffObj>();

        for(DiffObj diffObj : differences){
            if (diffObj.getDiffType() == DiffObj_Type.DELETION){
                deletions.add(diffObj);
            }
            else if(diffObj.getDiffType() == DiffObj_Type.INSERTION){
                insertions.add(diffObj);
            }
        }
        
        //Colararea Deletiilor        
        Highlight(s1, deletions, s1_output, options.getDeletionColor());
        Highlight(s2, insertions, s2_output, options.getInsertionColor()); 
        
        return true;        
    }
        
        //Prin aceasta supraincarcare se genereaza meta-sirul caracteristic LCS
        public boolean processDiff(final String sComp1,final String sComp2, TextFlow output){

            String s1 = options.processInputs(sComp1);
            String s2 = options.processInputs(sComp2); 
            
            Vector<LCS.ExtendedChar> LCSString = computeLCSString(s1, s2);
                        
            for(LCS.ExtendedChar e : LCSString){
                if(e.getDifferenceType() == LCS.DIFF_TYPE.NONE){
                    Text text = new Text();
                    char[] c = {e.getCharacter()};
                    text.setText(new String(c));
                    text.setFill(Color.BLACK);
                    output.getChildren().add(text);
                }
                else if(e.getDifferenceType() == LCS.DIFF_TYPE.DELETION){
                    Text text = new Text();
                    char[] c = {e.getCharacter()};
                    text.setText(new String(c));
                    text.setFill(options.getDeletionColor());
                    output.getChildren().add(text);
                }
                else if (e.getDifferenceType() == LCS.DIFF_TYPE.INSERTION){
                    Text text = new Text();
                    char[] c = {e.getCharacter()};
                    text.setText(new String(c));
                    text.setFill(options.getInsertionColor());
                    output.getChildren().add(text);
                }                
            }
            return true;
        }
        
        private void Highlight(String text ,Vector <DiffObj> differences, 
                             TextFlow output, Color color) {                
        int differenceStart;       
        int differenceEnd;
        if (differences.size() >= 2){        
        //Colorarea textului dinainte de prima diferenta
        differenceStart = differences.get(0).getStartPosition();
            if(differenceStart != 0){
                Text textBeforeDeletions = new Text();
                textBeforeDeletions.setText(text.substring(0, differenceStart));
                textBeforeDeletions.setFill(Color.BLACK);
                output.getChildren().add(textBeforeDeletions);
            }
            int i;
            for(i = 0; i < differences.size(); i++){
                Text deletedText = new Text();
                deletedText.setText(text.substring(differences.get(i).getStartPosition(),
                                    differences.get(i).getEndPosition() +1 ));
                deletedText.setFill(color);
                output.getChildren().add(deletedText);
                if(i != differences.size()-1){
                    Text textBetweenDeletions = new Text();
                    differenceStart = differences.get(i+1).getStartPosition();                    
                    textBetweenDeletions.setText(text.substring(differences.get(i).getEndPosition() +1
                                                             , differenceStart));
                    textBetweenDeletions.setFill(Color.BLACK);
                    output.getChildren().add(textBetweenDeletions);
                }                
            }
                //Textul de dupa ultima diferenta ramane intodeauna necolorat
                if(differences.get(i-1).getEndPosition() != text.length()){
                    Text lastCommonText = new Text();
                    lastCommonText.setText(text.substring(differences.get(i-1).getEndPosition() + 1,
                                           text.length()));
                    lastCommonText.setFill(Color.BLACK);
                    output.getChildren().add(lastCommonText);
             }
        }
        else if (differences.size() == 1){
            differenceStart = differences.get(0).getStartPosition();
            differenceEnd = differences.get(0).getEndPosition(); 
            
            Text textBeforeDeletion = new Text();
            textBeforeDeletion.setText(text.substring(0, differenceStart));
            textBeforeDeletion.setFill(Color.BLACK);
            Text deletedText = new Text();
            deletedText.setText(text.substring(differenceStart, differenceEnd+1));
            deletedText.setFill(color);
            Text textAfterDeletion = new Text();        
            textAfterDeletion.setText(text.substring(differenceEnd +1, text.length()));  
            output.getChildren().addAll(textBeforeDeletion, deletedText, textAfterDeletion);
        }        
        //Daca nu exista nici o deletie textul este tiparait nemodificat
        else{
            Text unchangedText = new Text();
            unchangedText.setText(text);
            output.getChildren().add(unchangedText);
        }
    }
	
	// +++++++++++++++++++++++++++++
	
	private void processWithLCS(final String sComp1, final String sComp2) {
		differences = new Vector<DiffObj>();		
		LCS lcs = new LCS(sComp1, sComp2, minimumLenght);
		lcs.populateDifferenceVector(differences);		
	}
        
        private Vector<LCS.ExtendedChar> computeLCSString(final String sComp1, final String sComp2){
            LCS lcs = new LCS(sComp1, sComp2, minimumLenght);
            return lcs.returnLCSString();
        }
	
	public void Process(final String [] sComp) {
		// TODO: compare multiple strings
		// TODO: return a DiffObj or a formatted output
	}
        
        public Vector<DiffObj> returnDifferences(){
            return differences;
        }
	
	//----------------FUNCTII DE SUPORT, UTILIZATE IN DEZVOLARE----------------
	
	public void printDifferenceVector() {
		for(DiffObj diffObj : differences) {
			//System.out.println(diffObj.getClass().getName());
			if(diffObj.getClass().getName() == "diff.DiffObjDeletion") {
				System.out.println("Deletie identificata");
				System.out.println("Start position: " + diffObj.getStartPosition());
				System.out.println("End position: " + diffObj.getEndPosition());
				System.out.println();
			}
			else if (diffObj.getClass().getName() == "diff.DiffObjInsertion") {
				System.out.println("Insertie identificata");
				System.out.println("Start position: " + diffObj.getStartPosition());
				System.out.println("End position: " + diffObj.getEndPosition());
				System.out.println();
			}
		}
	}
	
	
	// ++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++
	
	// ++++ Date ++++
	
	
}
