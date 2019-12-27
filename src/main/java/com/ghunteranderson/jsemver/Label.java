package com.ghunteranderson.jsemver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Label implements Iterable<String>{

	private final List<String> parts;
	
	public Label(List<String> parts) {
		this.parts = new ArrayList<>(parts.size());
		for(String part:parts) {
			if(part != null && !part.trim().isEmpty())
				this.parts.add(part);
		}
	}
	
	public Label() {
		this(new ArrayList<String>());
	}
	
	public String getPart(int i) {
		return parts.get(i);
	}
	
	public boolean isEmpty() {
		return parts.isEmpty();
	}
	
	public int size() {
		return parts.size();
	}
	
	@Override
	public Iterator<String> iterator() {
		return parts.iterator();
	}
	
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
