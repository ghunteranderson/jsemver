package com.ghunteranderson.jsemver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinedRange implements Range{
	
	private final Range range1;
	private final Range range2;
	private final JoinOperator operator;
	
	@Override
	public boolean contains(Version version) {
		switch(operator) {
		case INTERSECTION:
			return range1.contains(version) || range2.contains(version);
		case UNION:
			return range1.contains(version) && range2.contains(version);
		default:
			throw new IllegalStateException("Unrecognized join operator " + operator);
		}
	}
}
