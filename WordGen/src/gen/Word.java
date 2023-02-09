package gen;

import java.util.LinkedList;

public class Word {

	LinkedList<Syllable> syls;
	
	public Word() {
		syls = new LinkedList<Syllable>();
	}
	
	public Syllable getLastSyllable() {
		return syls.size() > 0 ? syls.getLast() : null;
	}
	
	public void addSyllable(Syllable syl) {
		syls.add(syl);
	}
	
	public Syllable generateSyllable(byte restrict) {
		Syllable syl = new Syllable(restrict);
		
		// GENERATE CONSONANTS
		Unit lastun = getLastSyllable() != null ? getLastSyllable().getLastUnit() : null;
		boolean startConsRequired = !syl.forcedOpenStart() && syl.forcedClosedStart(); // if syllable has a forced closed start, or 50% chance, generate start consonants
		// generate first consonant
		if (startConsRequired || Math.random() < 0.5) { // if consonant is required, or random chance
			Unit nunit;
			do {
				nunit = UnitLibrary.getRandomConsonant(); // get random consonant
			} while ((lastun != null && !lastun.isValidNeighbor(nunit)) || (nunit.noStart() && (lastun == null || lastun.isConsonant())));
			// if the consonant is not a valid neighbor for the previous unit in the word, keep picking (or if the unit cannot start a syllable (unless after a vowel))
			syl.addUnit(nunit);
			lastun = nunit;
			// maybe generate more
			boolean halt = !lastun.hasOpenBlends(); // if the last unit has no blends, halt immediately
			boolean blended = false;
			while (!halt && Math.random() * 3 < 1) { // if random permits, try to generate more consonants
				nunit = lastun.getRandomOpenBlendUnit(); // get random consonant
				if (blended && lastun.isSolitaryBlend(nunit)) continue;
				halt = !nunit.hasOpenBlends() || lastun.isSolitaryBlend(nunit); // if the blend is solitary, we should stop
				syl.addUnit(nunit);
				lastun = nunit;
				blended = true;
			}
		}
		
		// GENERATE VOWELS (could be slightly more efficient)
		{
			// generate first vowel
			Unit vunit;
			do {
				vunit = UnitLibrary.getRandomVowel(); // get random vowel
			} while (lastun != null && !lastun.isValidNeighbor(vunit)); // if the vowel is not a valid neighbor for the pervious unit in the word, keep picking
			syl.addUnit(vunit);
			lastun = vunit;
			// maybe generate more
			boolean halt = !vunit.hasBlends(); // if unit has no blends, halt immediately
			while (!halt && (int) (Math.random() * 5) == 0) { // while random chance permits, generate a vowel that does not already exist, (unless it is the only existing one, in which case also halt)
				// ie, "ee" is allowed and will halt vowel production, but "eae" is not allowed because e already exists and is not the only vowel
				vunit = UnitLibrary.getRandomVowel();
				if (lastun.isValidBlend(vunit) && !(syl.contains(vunit) && syl.getNumVowels() > 1)) {
					halt = syl.contains(vunit);
					syl.addUnit(vunit);
					lastun = vunit;
					halt = halt || syl.getNumVowels() >= 3;
				}
			}
		}
		
		// GENERATE MORE CONSONANTS
		boolean endConsRequired = !syl.forcedOpenEnd() && syl.forcedClosedEnd(); // if syllable has a forced closed start, or 50% chance, generate start consonants
		// generate first consonant
		if (endConsRequired || Math.random() < 0.5) { // if consonant is required, or random chance
			Unit nunit;
			do {
				nunit = UnitLibrary.getRandomConsonant(); // get random consonant
			} while (!lastun.isValidNeighbor(nunit) || nunit.noEnd()); // if the consonant is not a valid neighbor for the previous unit in the word, keep picking
			syl.addUnit(nunit);
			lastun = nunit;
			// maybe generate more
			boolean halt = !lastun.hasClosedBlends(); // if the last unit has no blends, halt immediately
			boolean blended = false;
			while (!halt && Math.random() * 3 < 1) { // if random permits, try to generate more consonants
				nunit = lastun.getRandomClosedBlendUnit(); // get random consonant
				if (blended && lastun.isSolitaryBlend(nunit)) continue;
				halt = !nunit.hasClosedBlends() || lastun.isSolitaryBlend(nunit); // if the blend is solitary, we should stop
				syl.addUnit(nunit);
				lastun = nunit;
				blended = true;
			}
		}
		
		return syl;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Syllable syl : syls) {
			sb.append(syl.toString());
		}
		return sb.toString();
	}
	
}