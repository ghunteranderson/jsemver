package com.ghunteranderson.jsemver;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.ghunteranderson.jsemver.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;

public class VersionComparatorTest {
	
	public static Stream<Arguments> equalsSamples(){
		return Stream.of(
				version(0, 0, 0, "", ""),
				version(1, 0, 0, "", ""),
				version(1, 2, 3, "", ""),
				version(1, 2, 3, "alpha.1", ""),
				version(1, 2, 3, "", "build.0034"),
				version(1, 2, 3, "alpha.1", "build.0034")
				).map(v -> Arguments.of(v, v));
	}

	public static Stream<Arguments> lessThanSamples(){
		return Stream.of(
				// Core version is ordered numerically
				Arguments.of(version(9, 0, 0, "", ""), version(19, 0, 0, "", "")),
				Arguments.of(version(0, 9, 0, "", ""), version(0, 19, 0, "", "")),
				Arguments.of(version(0, 0, 9, "", ""), version(0, 0, 19, "", "")),
				
				// Core version ordering follows priority
				Arguments.of(version(0, 9, 0, "", ""), version(1, 0, 0, "", "")),
				Arguments.of(version(2, 2, 9, "", ""), version(2, 3, 0, "", "")),
				
				// No label comes after labeled
				Arguments.of(version(1, 2, 3, "alpha", "build.02"), version(1, 2, 3, "", "")),
				Arguments.of(version(1, 2, 3, "alpha", ""), version(1, 2, 3, "", "")),
				Arguments.of(version(1, 2, 3, "alpha", ""), version(1, 2, 3, "alpha.12", "")),
				
				// Pre-release identifiers are ordered numerically or lexically
				Arguments.of(version(1, 2, 3, "alpha", ""), version(1, 2, 3, "beta", "")),
				Arguments.of(version(1, 2, 3, "9", ""), version(1, 2, 3, "12", "")),
				Arguments.of(version(1, 2, 3, "9z", ""), version(1, 2, 3, "ab", "")),
				Arguments.of(version(1, 2, 3, "9123", ""), version(1, 2, 3, "a", "")),
				Arguments.of(version(1, 2, 3, "alpha.123", ""), version(1, 2, 3, "alpha.abc", "")),
				
				// Pre-release follow priority
				Arguments.of(version(1, 2, 3, "alpha.12", ""), version(1, 2, 3, "beta.11", "")),
				Arguments.of(version(1, 2, 3, "alpha.9.rc", ""), version(1, 2, 3, "alpha.12.rc", "")),
				Arguments.of(version(1, 2, 3, "10.10a.alpha", ""), version(1, 2, 3, "10.9a.alpha", "")),
				
				// Alpha labels are sorted by ASCII code
				Arguments.of(version(1, 2, 3, "Az", ""), version(1, 2, 3, "ab", "")),
				
				// A few examples from the semver spec
				Arguments.of(version(1, 0, 0, "alpha", ""), version(1, 0, 0, "alpha.1", "")),
				Arguments.of(version(1, 0, 0, "alpha.1", ""), version(1, 0, 0, "alpha.beta", "")),
				Arguments.of(version(1, 0, 0, "beta", ""), version(1, 0, 0, "beta.2", "")),
				Arguments.of(version(1, 0, 0, "beta.2", ""), version(1, 0, 0, "beta.11", "")),
				Arguments.of(version(1, 0, 0, "beta.11", ""), version(1, 0, 0, "rc.1", "")),
				Arguments.of(version(1, 0, 0, "rc.1", ""), version(1, 0, 0, "", ""))
				);
	}
	
	public static Stream<Arguments> greaterThanSamples(){
		return lessThanSamples().map(args -> Arguments.of(args.get()[1], args.get()[0]));
	}
	
	@ParameterizedTest
	@MethodSource("lessThanSamples")
	public void compare_versionIsLessThan(Version v1, Version v2) {
		assertTrue(new VersionComparator().compare(v1, v2) < 0);
	}
	
	@ParameterizedTest
	@MethodSource("greaterThanSamples")
	public void compare_versionIsGreaterThan(Version v1, Version v2) {
		assertTrue(new VersionComparator().compare(v1, v2) > 0);
	}
	
	@ParameterizedTest
	@MethodSource("equalsSamples")
	public void compare_versionsAreEqual(Version version) {
		assertEquals(0, new VersionComparator().compare(version, version));
	}
	
	public void compare_buildNumberIsIgnored() {
		VersionComparator vc = new VersionComparator();
		assertEquals(0, vc.compare(version(1, 2, 3, "alpha.123", "build.0032"), version(1, 2, 3, "alpha.123", "")));
	}
	
	
}
