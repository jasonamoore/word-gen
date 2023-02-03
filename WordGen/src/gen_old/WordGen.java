package gen_old;

public class WordGen {

	public static String[] consonants = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", /*"q",*/ "r", "s", "t", "v", "w", "x", "y", "z"};

	public static String[] vowels = {"a", "e", "i", "o", "u", "y"};
	public static String[] v_blends = {"ai", "au", "ea", "ee", "ei", "eo", "eou", "eu", "ie", "io", "iou", "oa", "oo", "oi", "ou"};
	public static String[] p_blends = {"sc", "sk", "bl", "cl", "fl", "gl", "kl", "pl", "sl", "br", "cr", "dr", "fr", "gr", "kr", "pr", "tr"};
	public static String[] i_blends = {"bb", "cc", "dd", "ff", "gg", "ll", "mm", "nn", "pp", "rr", "ss", "tt"};
	public static String[] s_blends = {"ld", "nd", "rd", "ck", "lk", "nk", "rk", "sk", "wk", "lm", "rm", "sm", "ct", "ght", "lt", "mt", "nt", "pt", "rt", "st", "wt"};
	public static String[] arc_blends = {"dj", "ch", "gh", "ph", "sh", "th", "gn", "kn", "pn", "wr", "ts", "ps", "qu"};

	public static void main(String[] args) {
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

	private static final int START = 0;
	private static final int VOWEL = 1;
	private static final int CONSONANT = 2;
	private static final int END = 3;
	public static String genWord() {
		boolean gen = true;
		int gens = 0;
		String word = "";
		int state = START;
		int prestate = state;
		String[] chooser = vowels;
		while (gen) {
			double rand = Math.random();
			int rint;
			if (gens > 1 && Math.random() > -(1/12.0) * (gens - 3) + 0.55) {
				prestate = state;
				state = END;
				gen = false;
			}
			if (state == START) {
				rint = (int) (rand * 4);
				switch (rint) {
					case 0: chooser = consonants; state = VOWEL; break;
					case 1: chooser = vowels; state = CONSONANT; break;
					case 2: chooser = p_blends; state = VOWEL; break;
					case 3: chooser = arc_blends; state = VOWEL; break;
				}
			}
			else if (state == VOWEL) {
				rint = (int) (rand * 2);
				switch (rint) {
					case 0: chooser = vowels; break;
					case 1: chooser = v_blends; break;
				}
				state = CONSONANT;
			}
			else if (state == CONSONANT) {
				rint = (int) (rand * 4);
				switch (rint) {
					case 0: chooser = consonants; break;
					case 1: chooser = i_blends; break;
					case 2: chooser = s_blends; break;
					case 3: chooser = arc_blends; break;
				}
				state = VOWEL;
			}
			else if (state == END) {
				if (prestate == VOWEL) chooser = vowels;
				else if (gens > 1) {
					rint = (int) (rand * 3);
					switch (rint) {
						case 0: chooser = consonants; break;
						case 1: chooser = s_blends; break;
						case 2: chooser = arc_blends; break;
					}
				}
				else chooser = consonants;
			}
			word += chooser[(int) (Math.random() * chooser.length)];
			gens++;
		}
		return word;
	}

}
