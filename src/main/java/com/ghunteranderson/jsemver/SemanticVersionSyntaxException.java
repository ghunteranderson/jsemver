package com.ghunteranderson.jsemver;

public class SemanticVersionSyntaxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SemanticVersionSyntaxException(String message) {
		super(message);
	}
}
