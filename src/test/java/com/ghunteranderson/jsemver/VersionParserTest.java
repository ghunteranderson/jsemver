package com.ghunteranderson.jsemver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VersionParserTest {

	
	@Test
	public void parse_coreVersion() {
		CharacterScanner cs = new CharacterScanner("1.2.3");
		Version v = new VersionParser().parse(cs);
		assertEquals(1, v.getMajorVersion());
		assertEquals(2, v.getMinorVersion());
		assertEquals(3, v.getPatchVersion());
		assertTrue(v.getBuildLabel().isEmpty());
		assertTrue(v.getPreReleaseLabel().isEmpty());
	}
	
	@Test
	public void parse_coreVersionWithMultipleDigits() {
		CharacterScanner cs = new CharacterScanner("123.234.345");
		Version v = new VersionParser().parse(cs);
		assertEquals(123, v.getMajorVersion());
		assertEquals(234, v.getMinorVersion());
		assertEquals(345, v.getPatchVersion());
		assertTrue(v.getBuildLabel().isEmpty());
		assertTrue(v.getPreReleaseLabel().isEmpty());
	}
	
	@Test
	public void parse_coreVersionWithZeros() {
		CharacterScanner cs = new CharacterScanner("0.0.0");
		Version v = new VersionParser().parse(cs);
		assertEquals(0, v.getMajorVersion());
		assertEquals(0, v.getMinorVersion());
		assertEquals(0, v.getPatchVersion());
		assertTrue(v.getBuildLabel().isEmpty());
		assertTrue(v.getPreReleaseLabel().isEmpty());
	}
	
	@Test
	public void parse_leftPaddedMajorThrowsException() {
		CharacterScanner cs = new CharacterScanner("01.2.3");
		assertThrows(VersionSyntaxException.class, () -> new VersionParser().parse(cs));
	}
	
	@Test
	public void parse_leftPaddedMinorThrowsException() {
		CharacterScanner cs = new CharacterScanner("1.02.3");
		assertThrows(VersionSyntaxException.class, () -> new VersionParser().parse(cs));
	}
	
	@Test
	public void parse_leftPaddedPatchThrowsException() {
		CharacterScanner cs = new CharacterScanner("1.2.03");
		assertThrows(VersionSyntaxException.class, () -> new VersionParser().parse(cs));
	}
	
	@Test
	public void parse_withPreReleaseLabel() {
		CharacterScanner cs = new CharacterScanner("1.2.3-alpha.1");
		Version v = new VersionParser().parse(cs);
		assertEquals(1, v.getMajorVersion());
		assertEquals(2,  v.getMinorVersion());
		assertEquals(3, v.getPatchVersion());
		assertEquals("alpha", v.getPreReleaseLabel().getPart(0));
		assertEquals("1", v.getPreReleaseLabel().getPart(1));
	}
	
	@Test
	public void parse_releaseLabelWithLeftPaddedIdentThrowsException() {
		CharacterScanner cs = new CharacterScanner("1.2.3-alpha.01");
		assertThrows(VersionSyntaxException.class, () -> new VersionParser().parse(cs));
	}
	
	@Test
	public void parse_withBuildLabel() {
		// Note that this allows left padded identifiers
		CharacterScanner cs = new CharacterScanner("1.2.3+alpha.01");
		Version v = new VersionParser().parse(cs);
		assertEquals(1, v.getMajorVersion());
		assertEquals(2,  v.getMinorVersion());
		assertEquals(3, v.getPatchVersion());
		assertEquals("alpha", v.getBuildLabel().getPart(0));
		assertEquals("01", v.getBuildLabel().getPart(1));
	}
	
	
	@Test
	public void parse_withBothLabels() {
		CharacterScanner cs = new CharacterScanner("1.2.3-alpha.1+02.3");
		Version v = new VersionParser().parse(cs);
		assertEquals(1, v.getMajorVersion());
		assertEquals(2,  v.getMinorVersion());
		assertEquals(3, v.getPatchVersion());
		assertEquals("alpha", v.getPreReleaseLabel().getPart(0));
		assertEquals("1", v.getPreReleaseLabel().getPart(1));
		assertEquals("02", v.getBuildLabel().getPart(0));
		assertEquals("3", v.getBuildLabel().getPart(1));
	}
	
	@Test
	public void parse_coreVersionCannotStartWithLetter() {
		CharacterScanner cs = new CharacterScanner("v1.2.3-alpha.01");
		assertThrows(VersionSyntaxException.class, () -> new VersionParser().parse(cs));
	}

}
