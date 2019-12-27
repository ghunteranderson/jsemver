package com.ghunteranderson.jsemver;

/**
 * Collection of operators used to join two version ranges into one.
 */
public enum JoinOperator {
	
	/**
	 * All versions from each range are included in the new range.
	 */
	UNION,
	/**
	 * Only versions that included in both sets are included in the new range.
	 */
	INTERSECTION;
}
