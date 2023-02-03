package gen;

import java.util.LinkedList;

public class Word {

	LinkedList<Syllable> sylls;
	
	public Word() {
		sylls = new LinkedList<Syllable>();
	}
	
	public void addSyllable(Syllable syl) {
		sylls.add(syl);
	}
	
}