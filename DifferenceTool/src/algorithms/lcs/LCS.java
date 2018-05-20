package algorithms.lcs;

import java.util.Vector;

import algorithms.diff.*;


//Clasa contine metodele necesare pentru a utiliza algoritmul de
//calcul a Longest Common Subsequence
public class LCS implements IDifferenceAlgorithm, EqualINTF {
	
	private Vector<ExtendedChar> vLCS;
	
	// Matricea de memorare
	private int [][] memoryMatrix = null;
	
	private final int nRows;
	private final int nCols;
	private final String sComp1;
	private final String sComp2;
	
	// lungimea minima de caractere egale
	private final int nMinLen;
	
	private final boolean isLarge;
	
	// ++++++++++++++++++++ CONSTRUCTOR +++++++++++++++++++++    
        
	public LCS(final String s1, final String s2, final int nMinLength) {
		this.nMinLen = nMinLength;
		this.sComp1 = s1; // by Row
		this.sComp2 = s2; // by Col
		//
		final int iLenMax;
		if(s1.length() >= s2.length()) {
			iLenMax = s1.length();
		} else {
			iLenMax = s2.length();
		}
		if(iLenMax > 0xFF && iLenMax <= 0xFFFF) {
			isLarge = true;
		} else {
			isLarge = false;
		}
		this.nRows = sComp1.length() + 1;
		this.nCols = sComp2.length() + 1;
		//
		if(isLarge) {
			final MemMatrixB2 largeMatix = new MemMatrixB2(nRows, nCols, this);
			vLCS = largeMatix.Compile(s1, s2);
		} else {
			this.computeLCS();
			this.computeLCSString();
		}
	}
	
	// ++++++++++++++++++ MEMBER FUNCTIONS ++++++++++++++++++
	
	public Vector<ExtendedChar> returnLCSString(){
		return vLCS;
	}
	
	public void populateDifferenceVector(final Vector<DiffObj> differences) 
	{		
		for(int i = 0; i < vLCS.size(); i++) {			
			
			if(vLCS.get(i).dt == DIFF_TYPE.INSERTION) {
				int j = i;
				while(true) {
					j++;
					if(j >= vLCS.size() || vLCS.get(j).dt != DIFF_TYPE.INSERTION) {
						j--;
						break;
					}
				}
				differences.add(new DiffObj(vLCS.get(i).getPosition(), vLCS.get(j).getPosition(), DiffObj_Type.INSERTION));
				i = j;
			} else if(vLCS.get(i).dt == DIFF_TYPE.DELETION) {
                                int j = i;
                                while(true){
                                    j++;
                                    if(j >= vLCS.size() || vLCS.get(j).dt != DIFF_TYPE.DELETION){
                                        j--;
                                        break;
                                    }
                                }
                                differences.add(new DiffObj(vLCS.get(i).getPosition(), vLCS.get(j).getPosition(), DiffObj_Type.DELETION));
				i = j;
			}
		}
	}
	
	// Calculul matricii de memorizare a LCS
	private void computeLCS() {
		final int nLenRows = this.nRows;
		final int nLenCols = this.nCols;
		final int [][] memoryMatrix = new int[nLenRows][nLenCols];
		this.memoryMatrix = memoryMatrix;
		
		for(int i = 0; i < nLenRows; i++) {
			memoryMatrix[i][0] = 0;
		}
		//
		for(int j = 0; j < nLenCols; j++) {
			memoryMatrix[0][j] = 0;
		}
		
		for(int nRow = 1; nRow < nLenRows; nRow++) {
			for(int nCol = 1; nCol < nLenCols; nCol++) {
				if (this.isEqual(nRow, nCol)) {
					memoryMatrix[nRow][nCol] = memoryMatrix[nRow-1][nCol-1] + 1;
				} else {
					memoryMatrix[nRow][nCol] = Math.max(memoryMatrix[nRow][nCol-1], memoryMatrix[nRow-1][nCol]);
				}
			}
		}
	}
	
	//Printarea LCS
	//!!--Functia nu are utilitate pentru tema abordata, poate fi eliminata--!!
	public String getLCS() {
		final int nRow = nRows - 1;
		final int nCol = nCols - 1;
		final String lcs = LCSRecurssion(memoryMatrix, sComp1, sComp2, nRow, nCol);
		
		return lcs;		
	}
	
	// +++++++++++++++++++++++++++++
	
	// Helper functions
	@Override
	public boolean isEqual(final int nRow, final int nCol) {
		if(sComp1.charAt(nRow-1) != sComp2.charAt(nCol-1)) {
			return false;
		}
		if(nMinLen <= 1) {
			return true;
		}
		//
		for(int nposT1 = nRow - 2, nposT2 = nCol - 2; nposT1 >= nRow - nMinLen; nposT1--, nposT2--) {
			if(nposT1 < 0 || nposT2 < 0 || sComp1.charAt(nposT1) != sComp2.charAt(nposT2)) {
				return this.isEqual(nRow, nCol, nMinLen - nRow + nposT1 + 1);
			}
		}
		return true;
	}
	private boolean isEqual(final int i, final int j, final int after) {
		for(int nposT1 = i, nposT2 = j; nposT1 < i + after; nposT1++, nposT2++) {
			if(nposT1 >= sComp1.length() || nposT2 >= sComp2.length()) {
				return false;
			}
			if(sComp1.charAt(nposT1) != sComp2.charAt(nposT2)) {
				return false;
			}
		}
		return true;
	}
	
	//Se parcurge recursiv matricea de memomare pentru a deduce LCS
	private String LCSRecurssion(int[][] memoryMatrix, String s1, String s2, final int i, final int j ) {
		if(i == 0 || j == 0) {
			return "";
		}
		else if(this.isEqual(i, j)) {
			return LCSRecurssion(memoryMatrix, s1, s2, i-1, j-1) + s1.charAt(i-1);
		}
		else if(memoryMatrix[i][j-1] > memoryMatrix[i-1][j]) {		
			return LCSRecurssion(memoryMatrix, s1, s2, i, j-1);
		}
		else {
			return LCSRecurssion(memoryMatrix, s1, s2, i-1, j);
		}		
	}
	
	
	//Se afiseaza diferentele in formatul printr-o simpla parcurgere "standard"
	public void printDifferences() {
		final int i = nRows - 1;
		final int j = nCols - 1;
		
		diffPrintRecursion(memoryMatrix, sComp1, sComp2, i, j);		
	}
	//Functie de suport
	//Se parcurge recursiv matricea de memomare pentru identifica diferentele
	private void diffPrintRecursion(int[][] memoryMatrix, String s1, String s2, int i, int j) {
		if (i > 0 && j > 0 && this.isEqual(i, j)) {
			diffPrintRecursion(memoryMatrix, s1, s2, i-1, j-1);
			System.out.print(" " + s1.charAt(i - 1));
		}	
		else if (j > 0 && (i == 0 || memoryMatrix[i][j-1] >= memoryMatrix[i-1][j])) {
			diffPrintRecursion(memoryMatrix, s1, s2, i, j-1);
			System.out.print(" +" + s2.charAt(j-1));
		}
		else if (i > 0 && (j == 0 || memoryMatrix[i][j-1] < memoryMatrix[i-1][j])) {
			diffPrintRecursion(memoryMatrix, s1, s2, i-1, j);
			System.out.print(" --" + s1.charAt(i-1));
		}
		else {
			//System.out.print("");
		}
	}	
	
	
	// Stochez sirul creat de algoritmul LCS alaturi de:
	// pozitia in care a avut loc diferenta si tipul diferentei
	private void computeLCSString() {
                
            
		// non-Recursive version
		this.LCSComputeNonRecursive();
	}
	// Functie de suport
	// Se parcurge recursiv matricea de memorare pentru a se identifica diferentele
	// care sunt ulterior stocate
	@SuppressWarnings("unused")
	private void LCSComputeRecursion(final int nRow, final int nCol) {
		if (nRow > 0 && nCol > 0 && this.isEqual(nRow, nCol)) {
			LCSComputeRecursion(nRow-1, nCol-1);
			//System.out.print(" " + sComp1.charAt(nRow - 1));
			vLCS.addElement(new ExtendedChar(sComp1.charAt(nRow-1), nRow-1, DIFF_TYPE.NONE));
		}	
		else if (nCol > 0 && (nRow == 0 || memoryMatrix[nRow][nCol-1] >= memoryMatrix[nRow-1][nCol])) {
			LCSComputeRecursion(nRow, nCol-1);
			//System.out.print(" +" + sComp2.charAt(j-1));
			vLCS.addElement(new ExtendedChar(sComp2.charAt(nCol-1), nCol-1, DIFF_TYPE.INSERTION));
		}
		else if (nRow > 0 && (nCol == 0 || memoryMatrix[nRow][nCol-1] < memoryMatrix[nRow-1][nCol])) {
			LCSComputeRecursion(nRow-1, nCol);
			//System.out.print(" --" + sComp1.charAt(i-1));
			vLCS.addElement(new ExtendedChar(sComp1.charAt(nRow-1), nRow-1, DIFF_TYPE.DELETION));
		}
	}
	private void LCSComputeNonRecursive() {
		final Vector<ExtendedChar> vReverse = new Vector<ExtendedChar>();

		int nRow = nRows - 1;
		int nCol = nCols - 1;
		while(true) {
			if (nRow > 0 && nCol > 0 && this.isEqual(nRow, nCol)) {
				nRow--;
				vReverse.addElement(new ExtendedChar(sComp1.charAt(nRow), nRow, DIFF_TYPE.NONE));
				nCol--;
			}	
			else if (nCol > 0 && (nRow == 0 || memoryMatrix[nRow][nCol-1] >= memoryMatrix[nRow-1][nCol])) {
				nCol--;
				vReverse.addElement(new ExtendedChar(sComp2.charAt(nCol), nCol, DIFF_TYPE.INSERTION));
			}
			else if (nRow > 0 && (nCol == 0 || memoryMatrix[nRow][nCol-1] < memoryMatrix[nRow-1][nCol])) {
				nRow--;
				vReverse.addElement(new ExtendedChar(sComp1.charAt(nRow), nRow, DIFF_TYPE.DELETION));
			} else {
				break;
			}
		}
		// Revert
		vLCS = new Vector<> (vReverse.size());
		for(int id = vReverse.size() - 1; id >= 0; id--) {
			vLCS.add(vReverse.get(id));
		}
	}	
	
	//In aceasta clasa se va stoca fiecare caracter din meta-sirul creat de algoritmul de 
	//identificare a diferentelor baza pe LCS alaturi de informatii depsre diferenta
	
	public static class ExtendedChar {
		
		private final char character;
		private final int pos;
		private final DIFF_TYPE dt;
		
		public ExtendedChar(final char character, final int pos, final DIFF_TYPE dt) {
			this.character = character;
			this.pos = pos;
			this.dt = dt;
		}
		
		public char getCharacter() {
			return character;
		}
		
		public int getPosition() {
			return pos;
		}
		
		public DIFF_TYPE getDifferenceType() {
			return dt;
		}		
	}
	
	
	public enum DIFF_TYPE {		
		NONE,
		INSERTION,
		DELETION,
		SUBSTITUION
	}
	
	
	// -------------- FUNCTII UTILIZATE IN DEZVOLTARE -----------------------
	
	//Printarea matricii de memomare a LCS
	public void printLCSMatrix() {
		for(int i = 1; i < nRows; i++) {
			for(int j = 1; j < nCols; j++) {
				System.out.print(memoryMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//Printarea vectorului de caractere "extinse"
	//---- Utilizata pentru test----
	public void printLCSString() {
		for(ExtendedChar xc : vLCS) {
			if(xc.getDifferenceType() == DIFF_TYPE.NONE) {
				System.out.print(" " + xc.getCharacter());
			}
			else if (xc.getDifferenceType() == DIFF_TYPE.INSERTION) {
				System.out.print(" +" + xc.getCharacter());
			}
			else if(xc.getDifferenceType() == DIFF_TYPE.DELETION) {
				System.out.print(" -" + xc.getCharacter());
			}
			else if(xc.getDifferenceType() == DIFF_TYPE.SUBSTITUION) {
				System.out.print(" =" + xc.getCharacter());
			}
		}
	}
}
