package gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UnitLibrary {

	public static Unit[] units = new Unit[31];
	public static ArrayList<Unit> vowels = new ArrayList<Unit>(6);
	public static ArrayList<Unit> consonants = new ArrayList<Unit>(25);
	
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int CH = 3;
	public static final int D = 4;
	public static final int E = 5;
	public static final int F = 6;
	public static final int G = 7;
	public static final int H = 8;
	public static final int I = 9;
	public static final int J = 10;
	public static final int K = 11;
	public static final int L = 12;
	public static final int M = 13;
	public static final int N = 14;
	public static final int O = 15;
	public static final int P = 16;
	public static final int PH = 17;
	public static final int QU = 18;
	public static final int R = 19;
	public static final int S = 20;
	public static final int SH = 21;
	public static final int T = 22;
	public static final int TH = 23;
	public static final int U = 24;
	public static final int V = 25;
	public static final int W = 26;
	public static final int X = 27;
	public static final int Y = 28;
	public static final int Y_ = 29;
	public static final int Z = 30;
	
	//public enum UnitType {A, B, C, CH, D, E, F, G, H, I, J, K, L, M, N, O, P, QU, R, S, SH, T, TH, U, V, W, X, Y, Z}
	//public static final UnitType[] unitenums = UnitType.values();
	public static final String[] strs = {"a", "b", "c", "ch", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "ph", "qu", "r", "s", "sh", "t", "th", "u", "v", "w", "x", "y", "y", "z"};

	public static void loadUnitData(String file) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		StringBuilder sb = new StringBuilder();
		// preprocess, load all data into one String
		while (scan.hasNextLine()) {
			String line = scan.nextLine().replaceAll("[ ,\t]", "");
			if (line.charAt(0) == '#') continue;
			sb.append(line);
		}
		String data = sb.toString();
		// while loop to read data
		int i = 0;
		char c;
		while (i < data.length()) {
			c = data.charAt(i);
			// variables for the new unit being created
			int unid;
			byte[] relations = new byte[units.length];
			ArrayList<Integer> open = new ArrayList<Integer>();
			ArrayList<Integer> closed = new ArrayList<Integer>();
			boolean cons = false;
			boolean vowel = false;
			// find the unit currently being read
			String token = "";
			while (c != ';') {
				token += c;
				i++; c = data.charAt(i);
			}
			unid = getIdByString(token);
			i++; c = data.charAt(i); // ; skip semicolon
			// find whether this unit is a vowel, consonant or both
			while (c != ':') {
				vowel = vowel | c == 'v';
				cons = cons | c == 'c';
				i++; c = data.charAt(i);
			}
			i++; // ; skip colon
			i++; // { skip opening curly bracket
			c = data.charAt(i);
			// start reading the relationships
			while (c != '}') {
				String rtok = "";
				i++; // - skip hypen
				i++; // > skip arrow
				c = data.charAt(i);
				while (c != '=') {
					rtok += c;
					i++; c = data.charAt(i);
				}
				int ruid = getIdByString(rtok);
				i++; // = skip equals
				int bit2 = data.charAt(i) - '0';
				int bit1 = data.charAt(i + 1) - '0';
				int bit0 = data.charAt(i + 2) - '0';
				i += 3; c = data.charAt(i); // skip bits
				relations[ruid] = (byte) ((bit2 << 2) + (bit1 << 1) + bit0);
				if (bit1 == 1) open.add(ruid);
				if (bit0 == 1) closed.add(ruid);
				if (c == ',') i++; // , skip comma
				c = data.charAt(i);
			}
			i++;
			// create Unit
			Unit nunit = new Unit(unid, vowel, cons, relations, open, closed);
			units[unid] = nunit;
			if (nunit.isVowel()) vowels.add(nunit);
			if (nunit.isConsonant()) consonants.add(nunit);
		}
		/*for (int y = 0; y < units.length; y++) {
			System.out.println(getString(units[y]) + getString(units[y].getRandomClosedBlendUnit()));
		}*/
	}
	
	public static Unit getRandomConsonant() {
		return consonants.size() > 0 ? consonants.get((int) (Math.random() * consonants.size())) : null;
	}
	
	public static Unit getRandomVowel() {
		return vowels.size() > 0 ? vowels.get((int) (Math.random() * vowels.size())) : null;
	}
	
	public static Unit getById(int id) {
		if (id < 0 || id >= units.length) return null;
		return units[id];
	}
	
	public static String getString(Unit unit) {
		if (unit == null) return "";
		return strs[unit.getId()];
	}
	
	public static int getIdByString(String ustr) {
		switch (ustr.toUpperCase()) {
			case "A": return 0;
			case "B": return 1;
			case "C": return 2;
			case "CH": return 3;
			case "D": return 4;
			case "E": return 5;
			case "F": return 6;
			case "G": return 7;
			case "H": return 8;
			case "I": return 9;
			case "J": return 10;
			case "K": return 11;
			case "L": return 12;
			case "M": return 13;
			case "N": return 14;
			case "O": return 15;
			case "P": return 16;
			case "PH": return 17;
			case "QU": return 18;
			case "R": return 19;
			case "S": return 20;
			case "SH": return 21;
			case "T": return 22;
			case "TH": return 23;
			case "U": return 24;
			case "V": return 25;
			case "W": return 26;
			case "X": return 27;
			case "Y": return 28;
			case "Y*": return 29;
			case "Z": return 30;
		}
		return -1;
	}
	
}
