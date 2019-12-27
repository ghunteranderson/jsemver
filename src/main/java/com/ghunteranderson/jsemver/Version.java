package com.ghunteranderson.jsemver;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
public class Version {
	
	public static Version from(String version) {
		return new VersionParser().parse(version);
	}
	
	public static Builder builder(int major, int minor, int patch) {
		return new Builder(major, minor, patch);
	}
	
	
	private final int majorVersion;
	private final int minorVersion;
	private final int patchVersion;
	private final Label preReleaseLabel;
	private final Label buildLabel;
	
	public boolean isPreRelease() {
		return !preReleaseLabel.isEmpty();
	}
	
	public String toString() {
		StringBuilder bob = new StringBuilder();
		bob.append(majorVersion)
			.append('.')
			.append(minorVersion)
			.append('.')
			.append(patchVersion);
		
		if(!preReleaseLabel.isEmpty()) {
			bob.append('-').append(preReleaseLabel);
		}
		
		if(!buildLabel.isEmpty()) {
			bob.append('+').append(buildLabel);
		}
		
		return bob.toString();
	}
	
	public static class Builder{
		private final int majorVersion;
		private final int minorVersion;
		private final int patchVersion;
		private Label preReleaseLabel;
		private Label buildLabel;
		
		private Builder(int major, int minor, int patch) {
			this.majorVersion = major;
			this.minorVersion = minor;
			this.patchVersion = patch;
		}
		
		public Builder preReleaseLabel(String...parts) {
			return preReleaseLabel(Arrays.asList(parts));
		}
		
		public Builder preReleaseLabel(List<String> parts) {
			this.preReleaseLabel = new Label(parts);
			return this;
		}
		
		public Builder buildLabel(String...parts) {
			return buildLabel(Arrays.asList(parts));
		}
		
		public Builder buildLabel(List<String> parts) {
			this.buildLabel = new Label(parts);
			return this;
		}
		
		
		public Version build() {
			if(preReleaseLabel == null)
				preReleaseLabel = new Label();
			
			if(buildLabel == null)
				buildLabel = new Label();
			
			return new Version(majorVersion, minorVersion, patchVersion, preReleaseLabel, buildLabel);
		}
		
	}
}