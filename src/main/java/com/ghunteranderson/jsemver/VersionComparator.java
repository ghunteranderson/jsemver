package com.ghunteranderson.jsemver;

import java.util.Comparator;

/**
 * Comparator for {@link Version} objects following the semanitc version
 * specification for ordering versions.
 */
public class VersionComparator implements Comparator<Version>{

	@Override
	public int compare(Version v1, Version v2) {
		int result;
		if((result = Integer.compare(v1.getMajorVersion(), v2.getMajorVersion())) != 0)
			return result;
		
		if((result = Integer.compare(v1.getMinorVersion(), v2.getMinorVersion())) != 0)
			return result;
		
		if((result = Integer.compare(v1.getPatchVersion(), v2.getPatchVersion())) != 0)
			return result;
		
		Label pr1 = v1.getPreReleaseLabel();
		Label pr2 = v2.getPreReleaseLabel();
		
		// Check for versions without pre-release label
		boolean labelOneEmpty = pr1.isEmpty();
		boolean labelTwoEmpty = pr2.isEmpty();
		if(labelOneEmpty && labelTwoEmpty)
			return 0;
		else if(labelOneEmpty && !labelTwoEmpty)
			return 1;
		else if(!labelOneEmpty && labelTwoEmpty)
			return -1;
	
		// If neither label is empty, then we must compare parts
		int i;
		for(i=0; i<pr1.size() && i<pr2.size(); i++) {
			result = compareIdentifier(pr1.getPart(i), pr2.getPart(i));
			if(result !=0)
				return result;
		}
		
		// If v1 had more label parts, v1 is greater
		if(i < pr1.size())
			return 1;
		// If v2 had more label parts, v2 is greater
		else if( i < pr2.size())
			return -1;
	
		return 0;
	}
	
	
	
	private int compareIdentifier(String a, String b) {
		boolean aIsNumber = a.chars().allMatch(c -> c >='0' && c <= '9');
		boolean bIsNumber = b.chars().allMatch(c -> c >='0' && c <= '9');
		if(aIsNumber && bIsNumber)
			return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
		else{
			int i=0;
			char[] ca = a.toCharArray();
			char[] cb = b.toCharArray();
			for(i=0; i < a.length() && i < b.length(); i++) {
				if(ca[i] < cb[i])
					return -1;
				else if(ca[i] > cb[i])
					return 1;
			}
			if(ca.length < cb.length)
				return -1;
			else if(ca.length > cb.length)
				return 1;
			else
				return 0;
			
		}
	}
	
}
