package com.ghunteranderson.jsemver;

import java.util.Comparator;

public class VersionComparator implements Comparator<Version>{

	@Override
	public int compare(Version v1, Version v2) {
		/*int result;
		if((result = Integer.compare(v1.getMajorVersion(), v2.getMajorVersion())) != 0)
			return result;
		
		if((result = Integer.compare(v1.getMinorVersion(), v2.getMinorVersion())) != 0)
			return result;
		
		if((result = Integer.compare(v1.getPatchVersion(), v2.getPatchVersion())) != 0)
			return result;
		
		
		Label pr1 = v1.getPreReleaseLabel();
		Label pr2 = v2.getPreReleaseLabel();
		*/ return 0;
		
	}

}
