package com.ghunteranderson.jsemver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimpleRange implements Range {

	private RangeOperator operator;
	private Version version;
	
	@Override
	public boolean contains(Version input) {
		switch(operator) {
			case CARAT:
				return input.getMajorVersion() == version.getMajorVersion()
					&& compareThisTo(input) >= 0;

			case EQUAL:
				return compareThisTo(input) == 0;
				
			case GREATER_THAN:
				return compareThisTo(input) > 0;
				
			case GREATER_THAN_EQUAL:
				return compareThisTo(input) >= 0;
				
			case LESS_THAN:
				return compareThisTo(input) < 0;
				
			case LESS_THAN_EQUAL:
				return compareThisTo(input) <= 0;
				
			case TILDE:
				return input.getMajorVersion() == version.getMajorVersion()
					&& input.getMinorVersion() == version.getMinorVersion()
					&& compareThisTo(input) >= 0;
			
			default: 
				throw new IllegalStateException("Unrecognized range operator: " + operator);
		}
	}
	
	private int compareThisTo(Version input) {
		return new VersionComparator().compare(version, input);
	}

}
