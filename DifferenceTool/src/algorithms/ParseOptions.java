package algorithms;


/* Momentan, aceasta clasa va contine metode menite sa modifice textul inainte de fii parsat */
public final class ParseOptions {
	
	public static String removeDiacritics(final String text) {
		final StringBuilder workingText = new StringBuilder(text);
		int lenght = workingText.length();
		
		for(int i = 0; i < lenght; i++) {
			final char ch = GetCharASCII(workingText.charAt(i));
			if(ch > 0) {
				workingText.setCharAt(i, ch);	
			}			
		}		
		
		return new String(workingText);
	}
	
	// Transforma diacriticele in caractere ASCII;
	// returneaza 0, daca caracterul NU este diacritic;
	public static char GetCharASCII(final char ch) {
		switch(ch) {
		case '\u0103' : return 'a';
		case '\u0102' : return 'A';
		// caractere Multi-Byte Length NU sunt procesate
		// case 'a' + '\u0302' : return 'a'; // TODO: special: doar "circumflex"
		case '\u00e2' : return 'a';
		case '\u00c2' : return 'A';
		case '\u015f' : return 's';
		case '\u015e' : return 'S';
		case '\u0219' : return 's';
		case '\u0218' : return 'S';
		case '\u0163' : return 't';
		case '\u0162' : return 'T';
		case '\u021b' : return 't';
		case '\u021a' : return 'T';
		case '\u00ee' : return 'i';
		case '\u00ce' : return 'I';
		}
		return 0;
	}
}




























