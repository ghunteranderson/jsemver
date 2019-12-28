package com.ghunteranderson.jsemver;

/**
 * Exception may be thrown when a syntax error is encountered while parsing 
 * either semantic versions or Jsemver selectors.
 */
public class VersionSyntaxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construct new exception.
	 * @param message Helpful message describing exception.
	 */
	public VersionSyntaxException(String message) {
		super(message);
	}
}
