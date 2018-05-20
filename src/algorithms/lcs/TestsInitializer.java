package algorithms.lcs;

public class TestsInitializer {

	public static void main(String[] args) {
		
                String s1, s2;
            
                s1 = "AABBAA";
		s2 = "AAAA" ;
            
		LCS lcs = new LCS(s1 ,s2, 1);		
                
		lcs.printLCSString();
		System.out.println("\n");
		lcs.printDifferences();
		System.out.println("\n");
                lcs.printLCSMatrix();
		
	}

}
