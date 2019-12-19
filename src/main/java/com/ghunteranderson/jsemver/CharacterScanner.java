package com.ghunteranderson.jsemver;

class CharacterScanner {
	
	public static final char END = '\0';
	
	private String value;
	private int index;

	public CharacterScanner(String s) {
		value = s
				// We do not care about spaces before/after version string
				.trim()
				// We can treat consecutive whitespace as a single whitespace
				.replaceAll("\\ +", " ");
		index = 0;
	}
	
	
	public boolean hasNext() {
		return index < value.length();
	}
	
	public char peek() {
		return hasNext() ? value.charAt(index) : END;
	}
	
	public char next() {
		return hasNext() ? value.charAt(index++) : END;
	}
	
	public String toString() {
		return value;
	}
}
