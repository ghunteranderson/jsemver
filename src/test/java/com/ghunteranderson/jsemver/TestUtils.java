package com.ghunteranderson.jsemver;

import java.util.stream.Stream;

public class TestUtils {
	public static Stream<Version> getVersionHistory(){
		return Stream.of(
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
	}
	
	
	public static Version version(int major, int minor, int patch) {
		return Version.builder(major, minor, patch).build();
	}
	
	public static Version version(int major, int minor, int patch, String preRelease, String build) {
		return Version.builder(major, minor, patch)
				.preReleaseLabel(preRelease.split("\\."))
				.buildLabel(build.split("\\."))
				.build();
	}
}
