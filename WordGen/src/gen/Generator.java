package gen;

import java.io.FileNotFoundException;

public class Generator {

	public static void main(String[] args) {
		try {
			UnitLibrary.loadUnitData("res/data.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int wc = 1;
		try {
			wc = Integer.parseInt(args[0]);	
		} catch (Exception e) {
			wc = 1;
		}
		System.out.printf("Printing %d %s:\n", wc, wc == 1 ? "word" : "words");
		System.out.print(genWord());
		while (--wc > 0) {
			System.out.print(", ");
			System.out.print(genWord());
		}
		System.out.println();
	}

	private static String genWord() {
		// start new Word
		Word word = new Word();
		// pick number of syllables
		int numSyls = (int) (Math.random() * 3) + 1;
		
		// save last restriction
		byte restrictions = Syllable.NO_RESTRICTIONS;
		for (int i = 0; i < numSyls; i++) {
			// decide restrictions for the syllable
			Syllable last = word.getLastSyllable();
			if (last == null) restrictions = Syllable.NO_RESTRICTIONS;
			else {
				if (!last.closedEnd()) restrictions = Syllable.FORCED_CLOSED_START;
			}
			
			Syllable syl = word.generateSyllable(restrictions); // create syllable
			word.addSyllable(syl);
		}
		return word.toString();
	}
	
}