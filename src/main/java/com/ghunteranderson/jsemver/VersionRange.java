package com.ghunteranderson.jsemver;

/**
 * A selector to describe a range of versions.
 */
public interface VersionRange {
	public static VersionRange from(String version) {
		return new VersionRangeParser().parse(version);
	}
	
	/**
	 * Determines if a version is included version range.
	 * 
	 * @param version Version to be considered
	 * @return true if range includes version.
	 */
	boolean contains(Version version);
}
