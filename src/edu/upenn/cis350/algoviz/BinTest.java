package edu.upenn.cis350.algoviz;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinTest {
	
	BinObject obj1;
	BinObject obj2;
	BinObject obj3;

	@Before
	public void setUp() throws Exception {
		obj1 = new BinObject(10, 20, "default type");
		obj2 = new BinObject(20, 25, "default type");
		obj3 = new BinObject(10.6, 20.8, "default type");
	}

	@After
	public void tearDown() throws Exception {
		obj1 = null;
		obj2 = null;
		obj3 = null;
	}

	@Test
	public void testInsert() {
		Bin b1 = new Bin(20);
		assertTrue(b1.insert(obj1));
		BinObject actual = obj1;
		BinObject expected = b1.getContents().get(0);
		assertSame(expected, actual);
	}
	
	@Test
	public void testInsertDoubleCap() {
		Bin b1 = new Bin(20);
		assertTrue(b1.insert(obj3));
		BinObject actual = obj3;
		BinObject expected = b1.getContents().get(0);
		assertSame(expected, actual);
	}
	
	@Test
	public void testInsertTooMany() {
		Bin b1 = new Bin(20);
		b1.insert(obj1);
		assertFalse(b1.insert(obj2));
	}
	
	@Test
	public void testRemove() {
		Bin b1 = new Bin(30);
		b1.insert(obj1);
		b1.insert(obj2);
		assertTrue(b1.remove(obj1));
		assertTrue(b1.remove(obj2));
		assertFalse(b1.getContents().contains(obj1));
		assertFalse(b1.getContents().contains(obj2));
	}
	
	@Test
	public void testRemoveObjectNotInside() {
		Bin b1 = new Bin(20);
		ArrayList<BinObject> expected = b1.getContents();
		b1.insert(obj1);
		b1.insert(obj2);
		assertFalse(b1.remove(obj3));
		ArrayList<BinObject> actual = b1.getContents();
		assertEquals(expected, actual);
	}
	
}
