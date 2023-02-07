package gen_old;

import java.io.FileNotFoundException;

import gen.Syllable;
import gen.UnitLibrary;
import gen.Word;

public class WordGen {

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
		for (int i = 0; i < numSyls; i++) {
			// start new Syllable
			byte restrictions = Syllable.FORCED_OPEN_START | Syllable.FORCED_CLOSED_END;
			if (numSyls == 1) { // if first and only syllable, must be closed on start, end, or both
				switch ((int) (Math.random() * 2)) { // pick a random choice out of two choices
					case 0: restrictions = Syllable.FORCED_CLOSED_START; break;
					case 1: restrictions = Syllable.FORCED_CLOSED_END; break;
				}
			}
			else if (i == numSyls - 1) { // if last syllable, allow open end
				restrictions = Syllable.FORCED_OPEN_START | Syllable.NO_RESTRICTIONS;
			}
			Syllable syl = word.generateSyllable(restrictions); // create syllable
			word.addSyllable(syl);
		}
		return word.toString();
	}
	
}
