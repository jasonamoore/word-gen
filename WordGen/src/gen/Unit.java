package gen;

import java.util.ArrayList;

public class Unit {
	
	private int id;
	private boolean vowel; // true if vowel
	private boolean consonant; // true if consonant
	private byte[] relationships = new byte[UnitLibrary.units.length]; // relationship bits for each unit with this unit
	private ArrayList<Integer> openblends; // ids of units which form an opening blend with this unit
	private ArrayList<Integer> closedblends; // ids of units which form an closing blend with this unit
	
	public Unit(int id, boolean vowel, boolean consonant, byte[] relationships, ArrayList<Integer> openblends, ArrayList<Integer> closeblends) {
		this.id = id;
		this.vowel = vowel;
		this.consonant = consonant;
		this.relationships = relationships;
		this.openblends = openblends;
		this.closedblends = closeblends;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isVowel() {
		return vowel;
	}
	
	public boolean isConsonant() {
		return consonant;
	}
	
	private byte getRelationship(Unit unit) {
		return relationships[unit.getId()]; // returns the corresponding relationship
	}
	
	public boolean isSolitaryBlend(Unit unit) {
		if (unit == null) return false;
		return getRelationship(unit) > 0b100; // returns true if the relationship between this unit and the provided unit is solitary (ie, must be a blend and a 1 in the 3rd bit)
	}
	
	public boolean isValidBlend(Unit unit) {
		if (unit == null) return false;
		byte rel = getRelationship(unit);
		return (rel & 0b011) > 0 || (vowel && unit.isVowel() && rel != 0b100); // returns true if either of the blend bits are 1, or if both are vowels and relationship is not non-pairing
	}
	
	public boolean isValidNeighbor(Unit unit) {
		if (unit == null) return false;
		return getRelationship(unit) != 0b100; // returns true as long as relationship is not the no-pairing bit pattern
	}
	
	public boolean hasOpenBlends() {
		return openblends.size() > 0; // true if there are any open blends
	}
	
	public boolean hasClosedBlends() {
		return closedblends.size() > 0; // true if there are any closed blends
	}

	public boolean hasBlends() {
		return hasOpenBlends() || hasClosedBlends();
	}
	
	public Unit getRandomOpenBlendUnit() {
		return hasOpenBlends() ? UnitLibrary.getById(openblends.get((int) (Math.random() * openblends.size()))) : null; // returns a random unit which forms an open blend with this unit
	}
	
	public Unit getRandomClosedBlendUnit() {
		return hasClosedBlends() ? UnitLibrary.getById(closedblends.get((int) (Math.random() * closedblends.size()))) : null; // returns a random unit which forms a closed blend with this unit
	}
	
	public String toString() {
		return UnitLibrary.getString(this);
	}
	
}
