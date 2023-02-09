package gen;

import util.Distribution;

public class Unit {
	
	private int id;
	private boolean vowel; // true if vowel
	private boolean consonant; // true if consonant
	private boolean nostart; // true if a syllable can't start with this unit
	private boolean noend; // true if a syllable can't end with this unit
	private byte[] relationships = new byte[UnitLibrary.units.length]; // relationship bits for each unit with this unit
	private Distribution openblends; // ids of units which form an opening blend with this unit
	private Distribution closedblends; // ids of units which form an closing blend with this unit
	
	public Unit(int id, boolean vowel, boolean consonant, byte[] relationships, Distribution openblends, Distribution closeblends, boolean nostart, boolean noend) {
		this.id = id;
		this.vowel = vowel;
		this.consonant = consonant;
		this.relationships = relationships;
		this.openblends = openblends;
		this.closedblends = closeblends;
		this.nostart = nostart;
		this.noend = noend;
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
	
	public boolean noStart() {
		return nostart;
	}
	
	public boolean noEnd() {
		return noend;
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
		return !openblends.isEmpty(); // true if there are any open blends
	}
	
	public boolean hasClosedBlends() {
		return !closedblends.isEmpty(); // true if there are any closed blends
	}

	public boolean hasBlends() {
		return hasOpenBlends() || hasClosedBlends();
	}
	
	public Unit getRandomOpenBlendUnit() {
		return hasOpenBlends() ? UnitLibrary.getById((int) openblends.getRandom()) : null; // returns a random unit which forms an open blend with this unit
	}
	
	public Unit getRandomClosedBlendUnit() {
		return hasClosedBlends() ? UnitLibrary.getById((int) closedblends.getRandom()) : null; // returns a random unit which forms a closed blend with this unit
	}
	
	public String toString() {
		return UnitLibrary.getString(this);
	}
	
}
