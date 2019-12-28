package com.ghunteranderson.jsemver;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * A special type of VersionRange where two ranges are joined by union or by
 * by intersection.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class JoinedRange implements VersionRange{

	private final VersionRange range1;
	private final JoinOperator operator;
	private final VersionRange range2;
	
	@Override
	public boolean contains(Version version) {
		switch(operator) {
		case INTERSECTION:
			return range1.contains(version) && range2.contains(version);
		case UNION:
			return range1.contains(version) || range2.contains(version);
		default:
			throw new IllegalStateException("Unrecognized join operator " + operator);
		}
	}
	
	/**
	 * @return First version range in joining
	 */
	public VersionRange getRange1() {
		return range1;
	}

	/**
	 * @return Operator used to join two ranges
	 */
	public JoinOperator getOperator() {
		return operator;
	}

	/**
	 * @return Second version range in joining
	 */
	public VersionRange getRange2() {
		return range2;
	}
	
	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		
		if(operator == JoinOperator.INTERSECTION
				&& range1 instanceof JoinedRange 
				&& ((JoinedRange)range1).getOperator() == JoinOperator.UNION) {
			bob.append('(').append(range1).append(')');
		}
		else {
			bob.append(range1);
		}
		
		bob.append(getText(operator));
		
		if(operator == JoinOperator.INTERSECTION
				&& range2 instanceof JoinedRange 
				&& ((JoinedRange)range2).getOperator() == JoinOperator.UNION) {
			bob.append('(').append(range2).append(')');
		}
		else {
			bob.append(range2);
		}
		return bob.toString();
	}
	
	private String getText(JoinOperator op) {
		switch (op) {
		case INTERSECTION:
			return " ";
		case UNION:
			return " || ";
		default:
			throw new IllegalStateException("Unrecognized join operator " + operator);
		
		}
	}
}
