package com.ghunteranderson.jsemver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Parse version numbers based on semantic version syntax.
 */
class VersionParser {
	
	private boolean strict = true;
	
	/**
	 * Access strict parsing flag.
	 * By default strict parsing is enabled.
	 * When enabled, parser fails on any invalid syntax. 
	 * When disabled, parser will allow some common version syntax discrepancies.
	 * @return true if strict parsing is enabled.
	 */
	public boolean strict() {
		return strict;
	}
	
	/**
	 * Set if strict parsing is enabled. 
	 * By default strict parsing is disabled.
	 * When enabled, parser fails on any invalid syntax. 
	 * When disabled, parser will allow some common version syntax discrepancies.
	 * @param strict true if strict parsing is enabled.
	 * @return the same VersionParser for chained method calls
	 */
	public VersionParser strict(boolean strict) {
		this.strict = strict;
		return this;
	}

	/**
	 * Parse version number based on semantic version syntax.
	 * @param version Version string
	 * @return Parsed version
	 * @throws VersionSyntaxException if version cannot be parsed
	 */
	public Version parse(String version) {
		return parse(new CharacterScanner(version));
	}
	
	protected Version parse(CharacterScanner scanner) {
		// Major version
		int major = parseNumericIdentifier(scanner);
		if(scanner.peek() == '.') {
			scanner.next();
		}
		else {
			// Lenient parsing allows just a major version
			if(!strict && (scanner.peek() == ' ' || scanner.peek() == '\0'))
				return Version.builder(major, 0, 0).build();
			else
				throw new VersionSyntaxException(scanner.toString());
		}
		
		// Minor version
		int minor = parseNumericIdentifier(scanner);
		if(scanner.peek() == '.') {
			scanner.next();
		}
		else {
			// Lenient parsing allows major.minor as a version
			if(!strict && (scanner.peek() == ' '  || scanner.peek() == '\0'))
				return Version.builder(major, minor, 0).build();
			else
				throw new VersionSyntaxException(scanner.toString());
		}
		
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
		if(strict && digits.startsWith("0") && digits.length() > 1)
			throw leftPaddedZerosException(scanner.toString());
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
			throw new VersionSyntaxException("Unexpected character " + c + " in version " + scanner.toString());
	}
	
	private VersionSyntaxException leftPaddedZerosException(String inputVersion) {
		return new VersionSyntaxException("Numeric identifiers cannot be left padded with zeros: " + inputVersion);
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
		if(!alphaReached && ident.length() > 1) {
			// Strict parsing doesn't allow left padding zeros
			// If strict is disabled, correct the label part to be correct
			if(strict && ident.startsWith("0"))
				throw leftPaddedZerosException(cs.toString());
			else
				ident = ident.replaceAll("^0+", "");
			
		}
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
