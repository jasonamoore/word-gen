package gen;

public class Generator {

	public static void main(String[] args) {
		// get command-line args for number of words to generate
		
		for (int i = 0; /*num*/i < 0; i++) {
			// print(generateWord());
		}
	}
	
	public static Word generateWord() {
		// choose length in syllables
		//int syls = random number;
		Word word = new Word();
		for (int i = 0; /*syls*/i < 0; i++) {
			//link new syllable to word: generateSyllable(/*i < last syllable?*/true);
		}
		return word;
	}
	
	public static Syllable generateSyllable(boolean closed) {
		// structure of a syllable:
		// n-length consonant blend (optional), n-length vowel blend, n-length consonant blend (optional if not closed)
		// steps:
		//	search for any letter
		//	search for any compatible next letter (continue until vowel)
		//	search for compatible next letter (continue until consonant or vowel length 3; eg: iou, eau)
		//	if closed, search for consonant
		//	search for compatible consonant or quit
		return null;
	}
	
}
