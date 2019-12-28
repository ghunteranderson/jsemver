package com.ghunteranderson.jsemver;

import static com.ghunteranderson.jsemver.JoinOperator.*;
import static com.ghunteranderson.jsemver.RangeOperator.*;

/**
 * Parse version range based on Jsemver selector syntax
 */
class VersionRangeParser {
	
	public VersionRange parse(String s) {
		return parse(new CharacterScanner(s));
	}
	
	protected VersionRange parse(CharacterScanner cs) {
		return parseSelector(cs);
	}
	
	private VersionRange parseSelector(CharacterScanner cs) {
		VersionRange first = parseIntersection(cs);
		
		// Consume optional whitespace
		cs.skipNextWhiteSpaces();
		
		if(cs.peek() != '|')
			return first;

		cs.next();
		if(cs.next() != '|')
			throw new VersionSyntaxException(cs.toString());
		
		// Consume optional whitespace
		cs.skipNextWhiteSpaces();
		
		return new JoinedRange(first, UNION, parseSelector(cs));
	}
	
	private VersionRange parseIntersection(CharacterScanner cs) {
		VersionRange first = parseGroup(cs);
		
		// Consume optional whitespace
		cs.skipNextWhiteSpaces();
		
		if(!cs.hasNext() || cs.peek() == '|' || cs.peek() == ')')
			return first;
				
		return new JoinedRange(first, INTERSECTION, parseIntersection(cs));
	}
	
	private VersionRange parseGroup(CharacterScanner cs) {
		cs.skipNextWhiteSpaces();
		
		if(cs.peek() == '(') {
			cs.next();
			VersionRange grouped = parseSelector(cs);
			if(cs.next() != ')')
				throw new VersionSyntaxException("Missing closing parenthesis: " + cs.toString());
			return grouped;
		} else {
			return parseRange(cs);
		}
	}
	
	
	private VersionRange parseRange(CharacterScanner cs) {
		RangeOperator operator = EQUAL;
		
		if(cs.peek() == '~') {
			cs.next();
			operator = TILDE;
		}
		else if(cs.peek() == '^') {
			cs.next();
			operator = CARAT;
		}
		else if(cs.peek() == '<') {
			cs.next();
			operator = LESS_THAN;
			if(cs.peek() == '=') {
				cs.next();
				operator = LESS_THAN_EQUAL;
			}
		}
		else if(cs.peek() == '>') {
			cs.next();
			operator = GREATER_THAN;
			if(cs.peek() == '=') {
				cs.next();
				operator = GREATER_THAN_EQUAL;
			}
		}
		
		cs.skipNextWhiteSpaces();
		Version version = new VersionParser().parse(cs);
		return new SimpleRange(operator, version);
	}
}
