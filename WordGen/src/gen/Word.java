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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Syllable syl : syls) {
			sb.append(syl.toString());
		}
		return sb.toString();
	}
	
}