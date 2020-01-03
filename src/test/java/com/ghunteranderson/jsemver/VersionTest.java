package com.ghunteranderson.jsemver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class VersionTest {

	@Test
	public void toString_onlyCoreVersion() {
		Version version = Version.builder(3, 4, 5).build();
		assertEquals("3.4.5", version.toString());
	}
	
	@Test
	public void toString_withPreRelaseLabel() {
		Version version = Version
				.builder(1, 2, 3)
				.preReleaseLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3-alpha.3.485", version.toString());
	}
	
	@Test
	public void toString_withBuildLabel() {
		Version version = Version
				.builder(1, 2, 3)
				.buildLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3+alpha.3.485", version.toString());
	}
	
	@Test
	public void toString_withBothLabels() {
		Version version = Version
				.builder(1, 2, 3)
				.buildLabel("003", "4")
				.preReleaseLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3-alpha.3.485+003.4", version.toString());
	}
	
	@Test
	public void toString_emptyLabelPartsAreIgnord() {
		Version version = Version
				.builder(1, 2, 3)
				.buildLabel("")
				.preReleaseLabel("alpha", "", "485")
				.build();
		assertEquals("1.2.3-alpha.485", version.toString());
	}
	
	@Test
	public void toString_strictIsEnabledByDefault() {
		assertThrows(VersionSyntaxException.class, () -> Version.from("1.2"));
	}
	
	@Test
	public void toString_strictCanBeDisabled() {
		assertNotNull(Version.from("1.2", false));
	}
	
	
	
}
