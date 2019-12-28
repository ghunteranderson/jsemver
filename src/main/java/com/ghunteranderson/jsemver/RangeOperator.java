package com.ghunteranderson.jsemver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Collection of operators that, when applied to a single version, create a 
 * range.
 */
@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public enum RangeOperator {
	/**
	 * Includes all versions greater than or equal to the supplied version
	 * with the same major version number
	 */
	CARAT("^"),
	/**
	 * Includes all versions greater than or equal to the supplied version
	 * with the same major and minor version number
	 */
	TILDE("~"),
	/**
	 * Includes all versions less than and not including the supplied version
	 */
	LESS_THAN("<"),
	/**
	 * Includes all versions less than or equal to the supplied version
	 */
	LESS_THAN_EQUAL("<="),
	/**
	 * Includes all versions greater than the supplied version
	 */
	GREATER_THAN(">"),
	/**
	 * Includes all versions greater than or equal to the supplied version
	 */
	GREATER_THAN_EQUAL(">="),
	/**
	 * Includes only the supplied version
	 */
	EQUAL("");
	
	private final String symbol;
}
