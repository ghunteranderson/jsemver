package com.ghunteranderson.jsemver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;

/**
 * A Label may correspond to a pre-release label or build matadata. The dot separated
 * values are split into a list of Strings. Labels are immutable.
 * 
 */
@EqualsAndHashCode
public class Label implements Iterable<String>{

	private final List<String> parts;
	
	/**
	 * Create a new label from the list of values. Any modifications made 
	 * against the supplied list will not impact the Label's value. Any null or
	 * empty Strings will be ignored.
	 * @param parts Values that make up label ordered by precedence
	 */
	public Label(List<String> parts) {
		this.parts = new ArrayList<>(parts.size());
		for(String part:parts) {
			if(part != null && !part.trim().isEmpty())
				this.parts.add(part);
		}
	}
	
	/**
	 * Create a new empty Label.
	 */
	public Label() {
		this(new ArrayList<String>());
	}
	
	/**
	 * Access a part of the label's data.
	 * @param index Zero based index of the part to access.
	 * @return Part of label requested
	 * @throws IndexOutOfBoundsException if index is out of range (index &lt; 0 || index &gt;= size())
	 */
	public String getPart(int index) {
		return parts.get(index);
	}
	
	/**
	 * Returns true if the Label has no parts.
	 * @return true if the Label has no parts.
	 */
	public boolean isEmpty() {
		return parts.isEmpty();
	}
	
	/**
	 * Returns the number of parts in the label.
	 * @return the number of parts in the label.
	 */
	public int size() {
		return parts.size();
	}
	
	@Override
	public Iterator<String> iterator() {
		return parts.iterator();
	}
	
	/**
	 * Builds a label string following semantic version syntax from Label.
	 * @return reconstructed label following semantic version syntax.
	 */
	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		
		int size = parts.size();
		for(int i=0; i<size-1; i++) {
			bob.append(parts.get(i));
			bob.append('.');
		}
		if(size>0)
			bob.append(parts.get(size-1));
		
		return bob.toString();
	}

	
}
