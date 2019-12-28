package com.ghunteranderson.jsemver;

import static com.ghunteranderson.jsemver.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static com.ghunteranderson.jsemver.RangeOperator.*;
import static com.ghunteranderson.jsemver.JoinOperator.*;

public class VersionRangeParserTest {

	@Test
	public void parse_tilaExpression() {
		VersionRange expected = new SimpleRange(TILDE, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("~3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_allowSpaceAfterOperator() {
		VersionRange expected = new SimpleRange(TILDE, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("~ 3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_caratExpression() {
		VersionRange expected = new SimpleRange(CARAT, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("^3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_lessThanExpression() {
		VersionRange expected = new SimpleRange(LESS_THAN, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("<3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_greaterThanExpression() {
		VersionRange expected = new SimpleRange(GREATER_THAN, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse(">3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_lessThanEqualExpression() {
		VersionRange expected = new SimpleRange(LESS_THAN_EQUAL, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("<=3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_greaterThanEqualExpression() {
		VersionRange expected = new SimpleRange(GREATER_THAN_EQUAL, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse(">=3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_equalExpression() {
		VersionRange expected = new SimpleRange(EQUAL, version(3, 2, 1, "alpha.32", ""));
		VersionRange actual = new VersionRangeParser().parse("3.2.1-alpha.32");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_joinTwoVersions() {
		VersionRange expected = new JoinedRange(
				new SimpleRange(EQUAL, version(3, 2, 1, "alpha.32", "")),
				UNION,
				new SimpleRange(EQUAL,  version(3, 4, 5, "rc.2", "")));
		VersionRange actual = new VersionRangeParser().parse("3.2.1-alpha.32 || 3.4.5-rc.2");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_joinTwoRanges() {
		VersionRange expected = new JoinedRange(
				new SimpleRange(LESS_THAN, version(3, 2, 1, "alpha", "jenkins.89")),
				UNION,
				new SimpleRange(TILDE, version(1, 2, 3, "beta.21", ""))
				);
		VersionRange actual = new VersionRangeParser().parse("<3.2.1-alpha+jenkins.89||~1.2.3-beta.21");
		assertEquals(expected, actual);	
	}
	
	@Test
	public void parse_intersectTwoVersions() {
		VersionRange expected = new JoinedRange(
				new SimpleRange(EQUAL, version(3, 2, 1, "alpha.32", "")),
				INTERSECTION,
				new SimpleRange(EQUAL,  version(3, 4, 5, "rc.2", "")));
		VersionRange actual = new VersionRangeParser().parse("3.2.1-alpha.32 3.4.5-rc.2");
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void parse_intersectTakesPriorityOverUnion() {
		VersionRange expected = new JoinedRange(
				new SimpleRange(TILDE, version(1, 0, 0)),
				UNION,
				new JoinedRange(
						new SimpleRange(CARAT, version(2, 0, 0)),
						INTERSECTION, 
						new SimpleRange(LESS_THAN, version(2, 3, 4))
				));
		VersionRange actual = new VersionRangeParser().parse("~1.0.0 || ^2.0.0 <2.3.4");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_parenthesisCanReversePriority() {
		VersionRange expected = new JoinedRange(
				new JoinedRange(
						new SimpleRange(TILDE, version(1, 0, 0)), 
						UNION, 
						new SimpleRange(CARAT, version(2, 0, 0))
				),
				INTERSECTION,
				new SimpleRange(LESS_THAN, version(2, 3, 4))
			);
		VersionRange actual = new VersionRangeParser().parse("( ~1.0.0||^2.0.0 ) <2.3.4");
		assertEquals(expected, actual);
	}
	
	@Test
	public void parse_parenthesisCanBeNested() {
		VersionRange expected = new SimpleRange(EQUAL, version(1, 2, 3));
		VersionRange actual = new VersionRangeParser().parse("(((1.2.3)))");
		assertEquals(expected, actual);
	}
}
