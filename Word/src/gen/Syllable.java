package gen;

import java.util.LinkedList;

public class Syllable {

	public static final byte NO_RESTRICTIONS = 0b0000;
	public static final byte FORCED_CLOSED_START = 0b1000;
	public static final byte FORCED_OPEN_START = 0b0100;
	public static final byte FORCED_OPEN_END = 0b0010;
	public static final byte FORCED_CLOSED_END = 0b0001;
	
	private byte restricts;
	private LinkedList<Unit> units;
	private int numConsonants = 0;
	private int numVowels = 0;
	
	public Syllable(byte restricts) {
		this.restricts = restricts;
		units = new LinkedList<Unit>();
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
		if (unit.isVowel()) numVowels++;
		else numConsonants++;
	}
	
	public boolean closedStart() {
		return units.getFirst().isConsonant();
	}

	public boolean closedEnd() {
		return units.getLast().isConsonant();
	}
	
	public int getNumConsonants() {
		return numConsonants;
	}
	
	public int getNumVowels() {
		return numVowels;
	}
	
	public Unit getLastUnit() {
		return units.size() > 0 ? units.getLast() : null;
	}
	
	public boolean contains(Unit unit) {
		return units.contains(unit);
	}
	
	public boolean hasClosedStart() {
		return units.size() > 0 ? !units.getFirst().isVowel() : false;
	}
	
	public boolean hasClosedEnd() {
		return units.size() > 0 ? !units.getLast().isVowel() : false;
	}
	
	// the following methods return true or false depending on whether the given restriction applies for this syllable
	public boolean forcedClosedStart() {
		return (restricts & 0b1000) > 0;
	}
	
	public boolean forcedOpenStart() {
		return (restricts & 0b0100) > 0;
	}
	
	public boolean forcedOpenEnd() {
		return (restricts & 0b0010) > 0;
	}
	
	public boolean forcedClosedEnd() {
		return (restricts & 0b0001) > 0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Unit u : units) {
			sb.append(u.toString());
		}
		return sb.toString();
	}
	
}
