/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package options;

import algorithms.ParseOptions;

import javafx.scene.paint.Color;
/**
 *
 * @author kamur
 */
public class Options {
            
    private Color insertionColor;
    private Color deletionColor;    
    private boolean eliminateDiacritics;
    
    public Options() {        
        insertionColor = Color.ORANGE;
        deletionColor = Color.RED;
        eliminateDiacritics = false;
    }
    
    //Sirurile de caractere sunt operate in functie de setarile setate 
    public String processInputs(final String sComp){
         if(eliminateDiacritics == true){
             return ParseOptions.removeDiacritics(sComp);
        }
        else{
             return sComp;
        }        
    }
    
    public void setInsertionColor(Color color){
        this.insertionColor = color;
    }
    
    public Color getInsertionColor(){
        return insertionColor;
    }
    
    public void setDeletionColor(Color color){
        this.deletionColor = color;
    }
    
    public Color getDeletionColor(){
        return deletionColor;
    }   
    
    public void setEliminateDiacritics(boolean value){
        eliminateDiacritics = value;        
    }
    
    public boolean getEliminateDiacritics(){
        return eliminateDiacritics;
    }    
    
}
