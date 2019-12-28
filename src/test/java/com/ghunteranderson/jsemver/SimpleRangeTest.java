package com.ghunteranderson.jsemver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.ghunteranderson.jsemver.TestUtils.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleRangeTest {

	@Test
	public void contains_caratOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(1, 1, 0),
				version(1, 2, 0),
				version(1, 3, 0),
				version(1, 3, 1),
				version(1, 3, 2),
				version(1, 3, 3),
				version(1, 3, 4),
				version(1, 3, 5),
				version(1, 3, 6)
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.CARAT, version(1, 1, 0));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	
	@Test
	public void contains_equalsOperator() {
		List<Version> expectedRange = Arrays.asList(version(2, 0, 0, "rc.1", ""));
		
		VersionRange selector = new SimpleRange(RangeOperator.EQUAL, version(2, 0, 0, "rc.1", ""));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	
	@Test
	public void contains_lessThanOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(0, 0, 0),
				version(0, 1, 0),
				version(0, 1, 1),
				version(0, 1, 2),
				version(0, 2, 0),
				version(0, 2, 1),
				version(1, 0, 0),
				version(1, 0, 1),
				version(1, 0, 2),
				version(1, 1, 0),
				version(1, 2, 0),
				version(1, 3, 0),
				version(1, 3, 1),
				version(1, 3, 2),
				version(1, 3, 3),
				version(1, 3, 4),
				version(1, 3, 5),
				version(1, 3, 6)
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.LESS_THAN, version(2, 0, 0, "rc.1", ""));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	@Test
	public void contains_lessThanEqualOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(0, 0, 0),
				version(0, 1, 0),
				version(0, 1, 1),
				version(0, 1, 2),
				version(0, 2, 0),
				version(0, 2, 1),
				version(1, 0, 0),
				version(1, 0, 1),
				version(1, 0, 2),
				version(1, 1, 0),
				version(1, 2, 0),
				version(1, 3, 0),
				version(1, 3, 1),
				version(1, 3, 2),
				version(1, 3, 3),
				version(1, 3, 4),
				version(1, 3, 5),
				version(1, 3, 6),
				version(2, 0, 0, "rc.1", "")
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.LESS_THAN_EQUAL, version(2, 0, 0, "rc.1", ""));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	@Test
	public void contains_greaterThanOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(2, 0, 0, "rc.2", ""),
				version(2, 0, 0, "rc.3", ""),
				version(2, 0, 0, "", ""),
				version(2, 1, 0),
				version(2, 1, 1),
				version(3, 0, 0, "alpha.1", "build.001"),
				version(3, 0, 0, "alpha.1", "build.002"),
				version(3, 0, 0, "alpha.1", "build.003"),
				version(3, 0, 0, "alpha.2", ""),
				version(3, 0, 0, "rc.1", ""),
				version(3, 0, 0, "rc.2", ""),
				version(3, 0, 0)
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.GREATER_THAN, version(2, 0, 0, "rc.1", ""));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	@Test
	public void contains_greaterThanEqualOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(2, 0, 0, "rc.1", ""),
				version(2, 0, 0, "rc.2", ""),
				version(2, 0, 0, "rc.3", ""),
				version(2, 0, 0, "", ""),
				version(2, 1, 0),
				version(2, 1, 1),
				version(3, 0, 0, "alpha.1", "build.001"),
				version(3, 0, 0, "alpha.1", "build.002"),
				version(3, 0, 0, "alpha.1", "build.003"),
				version(3, 0, 0, "alpha.2", ""),
				version(3, 0, 0, "rc.1", ""),
				version(3, 0, 0, "rc.2", ""),
				version(3, 0, 0)
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.GREATER_THAN_EQUAL, version(2, 0, 0, "rc.1", ""));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		
		assertEquals(expectedRange, actualRange);
	}
	
	@Test
	public void contains_tileOperator() {
		List<Version> expectedRange = Arrays.asList(
				version(1, 3, 2),
				version(1, 3, 3),
				version(1, 3, 4),
				version(1, 3, 5),
				version(1, 3, 6)
				);
		
		VersionRange selector = new SimpleRange(RangeOperator.TILDE, version(1, 3, 2));
		List<Version> actualRange = getVersionHistory().filter(selector::contains).collect(Collectors.toList());
		assertEquals(expectedRange, actualRange);
	}
	
}
