package com.ghunteranderson.jsemver;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Version models the data parsed from a semantic version string.
 */
@EqualsAndHashCode
@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
public class Version {
	
	/**
	 * Parse the semantic version string with strict syntax enforced.
	 * Seen {@link #from(String, boolean)}
	 * @param version Semantic version string
	 * @return Parsed version
	 * @throws VersionSyntaxException if version string cannot be parsed
	 */
	public static Version from(String version) {
		return new VersionParser().parse(version);
	}
	
	/**
	 * Parse the semantic version string.
	 * @param version Semantic version string
	 * @param strict When true, parser fails on any invalid syntax. 
	 * When false, parser will allow some common version syntax discrepancies.
	 * @return
	 */
	public static Version from(String version, boolean strict) {
		return new VersionParser().parse(version);
	}
	
	/**
	 * Instantiate a new version builder.
	 * 
	 * @param major Major version number
	 * @param minor Minor version number
	 * @param patch Patch version number
	 * @return Builder to complete the new Version instance.
	 */
	public static Builder builder(int major, int minor, int patch) {
		return new Builder(major, minor, patch);
	}
	
	
	private final int majorVersion;
	private final int minorVersion;
	private final int patchVersion;
	private final Label preReleaseLabel;
	private final Label buildLabel;
	
	/**
	 * Returns true if the version has a non-empty pre-release label.
	 * @return true if the version has a non-empty pre-release label.
	 */
	public boolean isPreRelease() {
		return !preReleaseLabel.isEmpty();
	}
	
	/**
	 * Returns major version number.
	 * @return major version number.
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	/**
	 * Returns minor version number.
	 * @return minor version number.
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	/**
	 * Returns patch version number.
	 * @return patch version number.
	 */
	public int getPatchVersion() {
		return patchVersion;
	}

	/**
	 * Returns pre-release label. If the version is not a pre-release, then an
	 * empty {@link Label} will be returned.
	 * @return pre-release label.
	 */
	public Label getPreReleaseLabel() {
		return preReleaseLabel;
	}

	/**
	 * Returns build metadata label. If the version does not have build
	 * metadata, then an empty {@link Label} label will be returned.
	 * @return build metadata label.
	 */
	public Label getBuildLabel() {
		return buildLabel;
	}

	/**
	 * Builds a Version string following semantic version syntax.
	 * @return reconstructed version string following semantic version syntax.
	 */
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
	
	/**
	 * <p>Builder for constructing new Version objects. Do not
	 * attempt to instantiate this class directly.</p>
	 * Also see {@link Version#builder(int, int, int)}
	 */
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
		
		/**
		 * <p>Add a pre-release label. If multiple invocations are made, only the
		 * last invocation will be used.</p>
		 * 
		 * Also see {@link Builder#preReleaseLabel(List)}
		 * @param parts Parts of the pre-release label
		 * @return Builder for more building.
		 */
		public Builder preReleaseLabel(String...parts) {
			return preReleaseLabel(Arrays.asList(parts));
		}
		
		/**
		 * <p>Add a pre-release label. If multiple invocations are made, only the
		 * last invocation will be used.</p>
		 * 
		 * Also see {@link Builder#preReleaseLabel(String...)}
		 * @param parts Parts of the pre-release label
		 * @return Builder for more building.
		 */
		public Builder preReleaseLabel(List<String> parts) {
			this.preReleaseLabel = new Label(parts);
			return this;
		}
		
		/**
		 * <p>Add a build metadata label. If multiple invocations are made, only the
		 * last invocation will be used.</p>
		 * 
		 * Also see {@link Builder#buildLabel(List)}
		 * @param parts Parts of the build metadata label
		 * @return Builder for more building.
		 */
		public Builder buildLabel(String...parts) {
			return buildLabel(Arrays.asList(parts));
		}
		
		/**
		 * <p>Add a build metadata label. If multiple invocations are made, only the
		 * last invocation will be used.</p>
		 * Also see {@link Builder#buildLabel(String...)}
		 * @param parts Parts of the build metadata label
		 * @return Builder for more building.
		 */
		public Builder buildLabel(List<String> parts) {
			this.buildLabel = new Label(parts);
			return this;
		}
		
		/**
		 * Construct the {@link Version} object.
		 * @return Constructed @{link Version} object.
		 */
		public Version build() {
			if(preReleaseLabel == null)
				preReleaseLabel = new Label();
			
			if(buildLabel == null)
				buildLabel = new Label();
			
			return new Version(majorVersion, minorVersion, patchVersion, preReleaseLabel, buildLabel);
		}
		
	}
}
