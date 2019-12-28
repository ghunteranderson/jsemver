package com.ghunteranderson.jsemver;

import static com.ghunteranderson.jsemver.TestUtils.getVersionHistory;
import static com.ghunteranderson.jsemver.TestUtils.version;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JoinedRangeTest {

	@Test
	public void contains_intersectionOperator() {
		List<Version> expectedRange = Arrays.asList(
				//version(2, 1, 0),
				version(2, 1, 1),
				version(3, 0, 0, "alpha.1", "build.001"),
				version(3, 0, 0, "alpha.1", "build.002"),
				version(3, 0, 0, "alpha.1", "build.003"),
				version(3, 0, 0, "alpha.2", "")
				//version(3, 0, 0, "rc.1", ""),
				//version(3, 0, 0, "rc.2", ""),
				//version(3, 0, 0, "RELEASE", "")
				);
		
		VersionRange r1 = new SimpleRange(RangeOperator.GREATER_THAN, version(2, 1, 0));
		VersionRange r2 = new SimpleRange(RangeOperator.LESS_THAN, version(3, 0, 0, "rc", ""));
		VersionRange selector = new JoinedRange(r1, JoinOperator.INTERSECTION, r2);
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	
	@Test
	public void contains_UnionOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(2, 0, 0, "rc.1", ""),
				version(2, 0, 0, "rc.2", ""),
				version(2, 0, 0, "rc.3", ""),
				version(2, 0, 0, "", ""),
				//version(2, 1, 0),
				//version(2, 1, 1),
				//version(3, 0, 0, "alpha.1", "build.001"),
				//version(3, 0, 0, "alpha.1", "build.002"),
				//version(3, 0, 0, "alpha.1", "build.003"),
				version(3, 0, 0, "alpha.2", ""),
				version(3, 0, 0, "rc.1", ""),
				version(3, 0, 0, "rc.2", ""),
				version(3, 0, 0)
				);
		
		VersionRange r1 = new SimpleRange(RangeOperator.TILDE, version(2, 0, 0, "rc.1", ""));
		VersionRange r2 = new SimpleRange(RangeOperator.CARAT, version(3, 0, 0, "alpha.2", ""));
		VersionRange selector = new JoinedRange(r1, JoinOperator.UNION, r2);
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1.2.3",
		"~1.2.3",
		"<1.2.3",
		"<=1.2.3",
		">=1.2.3",
		">1.2.3-rc.1 <1.2.4",
		"~1.2.3 || 2.3.4",
		"~1.2.3-alpha <1.3.0",
		"(1.2.3 || 3.2.1) (3.2.1 || 1.2.3)",
		"1.2.3 1.3.4 || 2.3.4",
		"1.2.3 || 2.3.4 3.4.5",
		"(1.2.3 || 2.3.4) 3.4.5",
	})
	public void toString_parsedVersionCanCreateSameString(String input) {
		assertEquals(input, VersionRange.from(input).toString());
		
	}
}
