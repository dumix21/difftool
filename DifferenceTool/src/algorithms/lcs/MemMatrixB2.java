// Algoritm LCS
// Implementarea foloseste o optimizare a memoriei: 2 bytes in loc de int
//
// pentru Proiectul Individual UVT, An 2
// in colaborare cu Syonic SRL
//
// leo.mada@syonic.eu

package algorithms.lcs;

import java.util.Vector;

import algorithms.lcs.LCS.DIFF_TYPE;
import algorithms.lcs.LCS.ExtendedChar;

public class MemMatrixB2 {
	
	// Matricea de memorare
	private transient byte [][] memBMatrix = null;
	
	private final EqualINTF checkEq;
	
	private final int nRows;
	private final int nCols;
	
	// +++++++++++++++ CONSTRUCTOR ++++++++++++++++

	public MemMatrixB2(final int nRows, final int nCols, final EqualINTF checkEq) {
		this.nRows = nRows;
		this.nCols = nCols;
		this.checkEq = checkEq;
	}
	
	// +++++++++++++ MEMBER FUNCTIONS ++++++++++++++
	
	public Vector<ExtendedChar> Compile(final String sComp1, final String sComp2) {
		this.computeLCSLarge();
		return this.LCSComputeNonRecursiveLarge(sComp1, sComp2);
	}
	
	private void computeLCSLarge() {
		final int nLenRows = this.nRows;
		final int nLenCols = this.nCols;
		final Long lSize = 2L * nLenRows * nLenCols;
		System.out.println("" + nLenRows + ", "+ nLenCols + ": " + lSize.toString());
		final byte [][] memoryMatrix = new byte[nLenRows][2 * nLenCols];
		this.memBMatrix = memoryMatrix;
		
		for(int i = 0; i < nLenRows; i++) {
			memoryMatrix[i][0] = 0;
		}
		//
		for(int j = 0; j < 2 * nLenCols; j++) {
			memoryMatrix[0][j] = 0;
		}
		
		for(int nRow = 1; nRow < nLenRows; nRow++) {
			for(int nCol = 1; nCol < nLenCols; nCol++) {
				final int nColB2 = nCol + nCol;
				final int nRowPrev = nRow - 1;
				final int nColPrev = nColB2 - 2;
				if (checkEq.isEqual(nRow, nCol)) {
					// System.out.println("" + sComp1.charAt(nRowPrev) + ": " + (0xFF & memoryMatrix[nRowPrev][nColPrev + 1]));
					if((memoryMatrix[nRowPrev][nColPrev + 1]) == -1) {
						memoryMatrix[nRow][nColB2]     = (byte) ((0xFF & memoryMatrix[nRowPrev][nColPrev]) + 1);
						memoryMatrix[nRow][nColB2 + 1] = 0;
					} else {
						memoryMatrix[nRow][nColB2]     = memoryMatrix[nRowPrev][nColPrev];
						memoryMatrix[nRow][nColB2 + 1] = (byte) (memoryMatrix[nRowPrev][nColPrev + 1] + 1);
					}
				} else if(this.isLarger(nRow, nCol)) {
					memoryMatrix[nRow][nColB2]     = memoryMatrix[nRowPrev][nColB2];
					memoryMatrix[nRow][nColB2 + 1] = memoryMatrix[nRowPrev][nColB2 + 1];
				} else {
					memoryMatrix[nRow][nColB2]     = memoryMatrix[nRow][nColPrev];
					memoryMatrix[nRow][nColB2 + 1] = memoryMatrix[nRow][nColPrev + 1];
				}
			}
		}
		// test
		// 1-Pass Multiple Tables vs 2-Pass Single-Table
		// final Huffman huff = new Huffman();
		// huff.Compress(memoryMatrix[nLenRows - 10000]);
	}
	private boolean isLarger(final int nRow, final int nCol) {
		final int nColB2 = nCol + nCol;
		final int iValPrevCol = ((0xFF & memBMatrix[nRow][nColB2-2]) << 8) + (0xFF & memBMatrix[nRow][nColB2 - 1]);
		final int iValPrevRow = ((0xFF & memBMatrix[nRow-1][nColB2]) << 8) + (0xFF & memBMatrix[nRow-1][nColB2 + 1]);
		if(iValPrevCol >= iValPrevRow) {
			return false;
		}
		return true;
	}
	private Vector<ExtendedChar> LCSComputeNonRecursiveLarge(final String sComp1, final String sComp2) {
		final Vector<ExtendedChar> vReverse = new Vector<ExtendedChar>();

		int nRow = nRows - 1;
		int nCol = nCols - 1;
		while(true) {
			if (nRow > 0 && nCol > 0 && checkEq.isEqual(nRow, nCol)) {
				nRow--;
				vReverse.addElement(new ExtendedChar(sComp1.charAt(nRow), nRow, DIFF_TYPE.NONE));
				nCol--;
			}	
			else if (nCol > 0 && (nRow == 0 || ! isLarger(nRow, nCol))) {
				nCol--;
				vReverse.addElement(new ExtendedChar(sComp2.charAt(nCol), nCol, DIFF_TYPE.INSERTION));
			}
			else if (nRow > 0 && (nCol == 0 || isLarger(nRow, nCol))) {
				nRow--;
				vReverse.addElement(new ExtendedChar(sComp1.charAt(nRow), nRow, DIFF_TYPE.DELETION));
			} else {
				break;
			}
		}
		// Revert
		final Vector<ExtendedChar> vLCS = new Vector<> (vReverse.size());
		for(int id = vReverse.size() - 1; id >= 0; id--) {
			vLCS.add(vReverse.get(id));
		}
		return vLCS;
	}
}
