package com.ghunteranderson.jsemver;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * A SimpleRange is a range of version numbers created by taking a single
 * version and applying a {@link RangeOperator} to it. For more complex
 * version ranges, see {@link JoinedRange}.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class SimpleRange implements VersionRange {

	private final RangeOperator operator;
	private final Version version;
	
	/**
	 * @return Operator used to convert version into a range
	 */
	public RangeOperator getOperator() {
		return operator;
	}

	/**
	 * @return Version used to create the range
	 */
	public Version getVersion() {
		return version;
	}
	
	@Override
	public boolean contains(Version input) {
		switch(operator) {
			case CARAT:
				return input.getMajorVersion() == version.getMajorVersion()
					&& compareToThis(input) >= 0;

			case EQUAL:
				return compareToThis(input) == 0;
				
			case GREATER_THAN:
				return compareToThis(input) > 0;
				
			case GREATER_THAN_EQUAL:
				return compareToThis(input) >= 0;
				
			case LESS_THAN:
				return compareToThis(input) < 0;
				
			case LESS_THAN_EQUAL:
				return compareToThis(input) <= 0;
				
			case TILDE:
				return input.getMajorVersion() == version.getMajorVersion()
					&& input.getMinorVersion() == version.getMinorVersion()
					&& compareToThis(input) >= 0;
			
			default: 
				throw new IllegalStateException("Unrecognized range operator: " + operator);
		}
	}
	
	private int compareToThis(Version input) {
		return new VersionComparator().compare(input, version);
	}

	public String toString() {
		return operator.getSymbol() + version.toString();
	}
}
