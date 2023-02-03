package gen;

import java.util.LinkedList;

public class Syllable {

	LinkedList<Unit> units;
	
	public Syllable() {
		units = new LinkedList<Unit>();
	}
	
	public void addSyllable(Unit unit) {
		units.add(unit);
	}
	
}
