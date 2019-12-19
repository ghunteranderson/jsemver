package com.ghunteranderson.jsemver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class VersionParser {

	
	public Version parse(CharacterScanner scanner) {
		// Major version
		int major = parseNumericIdentifier(scanner);
		if(scanner.next() != '.')
			throw new SemanticVersionSyntaxException(scanner.toString());
		
		// Minor version
		int minor = parseNumericIdentifier(scanner);
		if(scanner.next() != '.')
			throw new SemanticVersionSyntaxException(scanner.toString());
		
		// Patch Version
		int patch = parseNumericIdentifier(scanner);
		
		Version.Builder builder = Version.builder(major, minor, patch);
		
		if(scanner.peek() ==  '-') {
			scanner.next();
			builder.preReleaseLabel(parseLabel(scanner, this::parsePreReleaseIdentifier));
		}
		
		if(scanner.peek() == '+') {
			scanner.next();
			builder.buildLabel(parseLabel(scanner, this::parseBuildIdentifier));
		}
		
		return builder.build();
	}
	
	
	private List<String> parseLabel(CharacterScanner scanner, Function<CharacterScanner, String> labelParser) {
		List<String> parts = new ArrayList<>();
		parts.add(labelParser.apply(scanner));
		while(scanner.peek() == '.') {
			scanner.next();
			parts.add(labelParser.apply(scanner));
		}
		return parts;
	}
	

	// <numeric identifier>
	private int parseNumericIdentifier(CharacterScanner scanner) {
		String digits = parseDigits(scanner);
		if(digits.startsWith("0") && digits.length() > 1)
			throwLeftPaddedZerosException(scanner.toString());
		return Integer.parseInt(digits);
	}

	private String parseDigits(CharacterScanner scanner) {
		char c = scanner.next();
		
		if(isDigit(c)){
			StringBuilder bob = new StringBuilder().append(c);
			while(isDigit(scanner.peek()))
				bob.append(scanner.next());
			return bob.toString();
		}
		
		else
			throw new SemanticVersionSyntaxException(scanner.toString());
	}
	
	private void throwLeftPaddedZerosException(String inputVersion) {
		throw new SemanticVersionSyntaxException("Numeric identifiers cannot be left padded with zeros: " + inputVersion);
	}
	
	private String parsePreReleaseIdentifier(CharacterScanner cs) {
		boolean alphaReached = false;
		StringBuilder bob = new StringBuilder();
		while(isIdentifierCharacter(cs.peek())) {
			if(isLetter(cs.peek()))
				alphaReached = true;
			bob.append(cs.next());
		}
		
		String ident = bob.toString();
		if(!alphaReached && ident.startsWith("0") && ident.length() > 1)
			throwLeftPaddedZerosException(cs.toString());
		return ident;
	}
	
	private String parseBuildIdentifier(CharacterScanner cs) {
		StringBuilder bob = new StringBuilder();
		while(isIdentifierCharacter(cs.peek())) {
			bob.append(cs.next());
		}
		return bob.toString();
	}
	
	// <digit>
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	// <letter>
	private boolean isLetter(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}
	
	// <non-digit>
	private boolean isNonDigit(char c) {
		return c == '-' || isLetter(c);
	}
	
	// <identifier character>
	private boolean isIdentifierCharacter(char c) {
		return isDigit(c) || isNonDigit(c);
	}
}
