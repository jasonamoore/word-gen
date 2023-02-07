package gen_old;

import java.io.FileNotFoundException;

import gen.Syllable;
import gen.Unit;
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
			byte restrictions = Syllable.FORCED_CLOSED_END;
			if (numSyls == 1) { // if first and only syllable, must be closed on start, end, or both
				switch ((int) (Math.random() * 2)) { // pick a random choice out of two choices
					case 0: restrictions = Syllable.FORCED_CLOSED_START; break;
					case 1: restrictions = Syllable.FORCED_CLOSED_END; break;
				}
			}
			else if (i == numSyls - 1) { // if last syllable, allow open end
				restrictions = Syllable.NO_RESTRICTIONS;
			}
			Syllable syl = new Syllable(restrictions); // create syllable
			
			// GENERATE CONSONANTS
			if (!syl.forcedOpenStart() && (syl.forcedClosedStart() || (int) (Math.random() * 2) == 1)) { // if syllable has a forced closed start, or 50% chance, generate start consonants
				Unit nunit = UnitLibrary.getRandomConsonant(); // get random consonant
				syl.addUnit(nunit);
				while (nunit.hasOpenBlends() && (int) (Math.random() * 2) == 1) { // while there are blends to make, and 50% chance occurs, continue blending
					nunit = nunit.getRandomOpenBlendUnit();
					syl.addUnit(nunit);
				}
			}
			
			// GENERATE VOWELS (TODO - could be slightly more efficient
			Unit vunit = UnitLibrary.getRandomVowel();
			syl.addUnit(vunit);
			while (/*vunit.hasBlends() &&*/ (int) (Math.random() * 3) == 2) { // while random chance permits, generate a vowel that does not already exist, (unless it is the only existing one, in which case also halt)
				// ie, "ee" is allowed and will halt vowel production, but "eae" is not allowed because e already exists and is not the only vowel
				vunit = UnitLibrary.getRandomVowel();
				if (syl.getLastUnit().isValidBlend(vunit) && !(syl.contains(vunit) && syl.getNumVowels() > 1)) {
					if (syl.contains(vunit)) break;
					syl.addUnit(vunit);
				}
			}
			
			// GENERATE MORE CONSONANTS
			if (!syl.forcedOpenEnd() && (syl.forcedClosedEnd() || (int) (Math.random() * 2) == 1)) { // if syllable has a forced closed end, or 50% chance, generate end consonants
				Unit nunit = UnitLibrary.getRandomConsonant(); // get random consonant
				syl.addUnit(nunit);
				while (nunit.hasClosedBlends() && (int) (Math.random() * 2) == 1) { // while there are blends to make, and 50% chance occurs, continue blending
					nunit = nunit.getRandomClosedBlendUnit();
					syl.addUnit(nunit);
				}
			}
			word.addSyllable(syl);
		}
		return word.toString();
	}
	
}
