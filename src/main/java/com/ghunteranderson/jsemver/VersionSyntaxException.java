package com.ghunteranderson.jsemver;

public class VersionSyntaxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VersionSyntaxException(String message) {
		super(message);
	}
}
