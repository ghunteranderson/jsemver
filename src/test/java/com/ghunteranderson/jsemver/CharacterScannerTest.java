package com.ghunteranderson.jsemver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CharacterScannerTest {

	
	@Test
	public void next_simpleStringIsRead() {
		CharacterScanner cs = new CharacterScanner("^2.2.1");
		assertEquals('^', cs.next());
		assertEquals('2', cs.next());
		assertEquals('.', cs.next());
		assertEquals('2', cs.next());
		assertEquals('.', cs.next());
		assertEquals('1', cs.next());
		assertEquals(CharacterScanner.END, cs.next());
	}
	
	@Test
	public void next_whiteSpaceBeforeAndAfterAreIgnored() {
		CharacterScanner cs = new CharacterScanner(" ^2.2.1 ");
		assertEquals('^', cs.next());
		assertEquals('2', cs.next());
		assertEquals('.', cs.next());
		assertEquals('2', cs.next());
		assertEquals('.', cs.next());
		assertEquals('1', cs.next());
		assertEquals(CharacterScanner.END, cs.next());
	}
	
	
	@Test
	public void next_consecutiveWhiteSpaceReturnsOneWhiteSpace() {
		CharacterScanner cs = new CharacterScanner("1.0.0  -    1.2.0");
		assertEquals('1', cs.next());
		assertEquals('.', cs.next());
		assertEquals('0', cs.next());
		assertEquals('.', cs.next());
		assertEquals('0', cs.next());
		assertEquals(' ', cs.next());
		assertEquals('-', cs.next());
		assertEquals(' ', cs.next());
		assertEquals('1', cs.next());
		assertEquals('.', cs.next());
		assertEquals('2', cs.next());
		assertEquals('.', cs.next());
		assertEquals('0', cs.next());
		assertEquals(CharacterScanner.END, cs.next());
	}
	
	@Test
	public void hasNext_emptyString() {
		CharacterScanner cs = new CharacterScanner("");
		assertFalse(cs.hasNext());
	}
	
	@Test
	public void hasNext_nonEmptyString() {
		CharacterScanner cs = new CharacterScanner("Hi");
		assertTrue(cs.hasNext());
		cs.next(); // H
		assertTrue(cs.hasNext());
		cs.next(); // i
		assertFalse(cs.hasNext());
	}
	
	@Test
	public void peek_doesNotProgressString() {
		CharacterScanner cs = new CharacterScanner("abcd");
		assertEquals('a', cs.peek());
		assertEquals('a', cs.peek());
		assertEquals('a', cs.peek());
		assertEquals('a', cs.peek());
		cs.next();
		assertEquals('b', cs.peek());
		assertEquals('b', cs.peek());
		assertEquals('b', cs.peek());
		assertEquals('b', cs.peek());
		assertTrue(cs.hasNext());
	}
}
