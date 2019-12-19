package com.ghunteranderson.jsemver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SemanticVersionTest {

	@Test
	public void toString_onlyCoreVersion() {
		SemanticVersion version = SemanticVersion.builder(3, 4, 5).build();
		assertEquals("3.4.5", version.toString());
	}
	
	@Test
	public void toString_withPreRelaseLabel() {
		SemanticVersion version = SemanticVersion
				.builder(1, 2, 3)
				.preReleaseLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3-alpha.3.485", version.toString());
	}
	
	@Test
	public void toString_withBuildLabel() {
		SemanticVersion version = SemanticVersion
				.builder(1, 2, 3)
				.buildLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3+alpha.3.485", version.toString());
	}
	
	@Test
	public void toString_withBothLabels() {
		SemanticVersion version = SemanticVersion
				.builder(1, 2, 3)
				.buildLabel("003", "4")
				.preReleaseLabel("alpha", "3", "485")
				.build();
		assertEquals("1.2.3-alpha.3.485+003.4", version.toString());
	}
	
	@Test
	public void toString_emptyLabelPartsAreIgnord() {
		SemanticVersion version = SemanticVersion
				.builder(1, 2, 3)
				.buildLabel("")
				.preReleaseLabel("alpha", "", "485")
				.build();
		assertEquals("1.2.3-alpha.485", version.toString());
	}
}
