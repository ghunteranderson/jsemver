package com.ghunteranderson.jsemver;

/**
 * Character Scanner assists with version parsing by traversing the string one 
 * character at a time. A pre-processing step is performed to make white space
 * handling a little easier.
 */
class CharacterScanner {
	
	/**
	 * The character returned after the end of the string has been reached.
	 */
	public static final char END = '\0';
	
	private String value;
	private int index;

	/**
	 * Constructs a new CharacterScanner. This will also trim the string and
	 * consolidate and consecutive whitespace into a single whitespace since 
	 * they are semantically the same for version parsing.
	 * @param input Text to be scanned.
	 */
	public CharacterScanner(String input) {
		value = input
				// We do not care about spaces before/after version string
				.trim()
				// We can treat consecutive whitespace as a single whitespace
				.replaceAll("\\ +", " ");
		index = 0;
	}
	
	
	/**
	 * @return true if the scanner has more characters to serve.
	 */
	public boolean hasNext() {
		return index < value.length();
	}
	
	/**
	 * Preview the next character in the sequence without removing it from the
	 * queue. If sequence end has been reached, then {@link END} is returned.
	 * @return The next character or {@link END}
	 */
	public char peek() {
		return hasNext() ? value.charAt(index) : END;
	}
	
	/**
	 * Removes the next character from the queue and returns it to the caller.
	 * If sequence end has been reached, then {@link END} is returned.
	 * @return The next character or {@link END}
	 */
	public char next() {
		return hasNext() ? value.charAt(index++) : END;
	}
	
	/**
	 * Removes all white space from the current position in the sequence. If
	 * the next character is not white space, no action is performed.
	 * <br/><br/>
	 * Example: <code>"&nbsp;&nbsp;&nbsp;abc 123" -> "abc 123"</code>
	 */
	public void skipNextWhiteSpaces() {
		while(peek() == ' ')
			next();
	}
	
	@Override
	public String toString() {
		return value;
	}
}
