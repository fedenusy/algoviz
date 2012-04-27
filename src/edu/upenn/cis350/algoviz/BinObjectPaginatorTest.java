package edu.upenn.cis350.algoviz;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BinObjectPaginatorTest {

	private BinObjectPaginator _paginator;
	
	@Before
	public void setUp() throws Exception {
		_paginator = new BinObjectPaginator(0, 0, 0, "Test Paginator");
		
		ArrayList<BinObject> objs = new ArrayList<BinObject>();
		objs.add(new BinObject(5, 10, null));
		objs.add(new BinObject(10, 20, null));
		objs.add(new BinObject(17, 32, null));
		objs.add(new BinObject(3, 11, null));
		objs.add(new BinObject(8, 20, null));
		objs.add(new BinObject(10, 20, null));
		objs.add(new BinObject(17.5, 32, null));
		objs.add(new BinObject(3, 11, null));
		objs.add(new BinObject(4, 9, null));
		objs.add(new BinObject(8, 20, null));
		objs.add(new BinObject(3, 4, null));
		objs.add(new BinObject(5, 7.5, null));
		objs.add(new BinObject(2, 4, null));
		objs.add(new BinObject(5, 10, null));
		objs.add(new BinObject(10, 20, null));
		objs.add(new BinObject(17, 32, null));
		objs.add(new BinObject(3, 11, null));
		objs.add(new BinObject(8, 20, null));
		objs.add(new BinObject(10, 20, null));
		objs.add(new BinObject(17.5, 32, null));
		objs.add(new BinObject(3, 11, null));
		objs.add(new BinObject(4, 9, null));
		objs.add(new BinObject(8, 20, null));
		objs.add(new BinObject(3, 4, null));
		objs.add(new BinObject(5, 7.5, null));
		objs.add(new BinObject(2, 4, null));
		
		_paginator.addAll(objs);
	}

	@Test
	public void testPaginator() {
		assertEquals(_paginator.getTitle(), "Test Paginator");
		
		assertEquals(_paginator.getText(), "1 / 2");
		_paginator.nextPage();
		assertEquals(_paginator.getText(), "2 / 2");
		_paginator.nextPage();
		assertEquals(_paginator.getText(), "1 / 2");
		_paginator.previousPage();
		assertEquals(_paginator.getText(), "2 / 2");
		_paginator.previousPage();
		assertEquals(_paginator.getText(), "1 / 2");
		
		assertEquals(_paginator.getCurrentPageObjects().size(), 16);
		_paginator.nextPage();
		assertEquals(_paginator.getCurrentPageObjects().size(), 10);
		
		BinObject testObj = new BinObject(3, 3, null);
		_paginator.add(testObj);
		assertEquals(_paginator.getCurrentPageObjects().size(), 11);
		
		_paginator.remove(testObj);
		assertEquals(_paginator.getCurrentPageObjects().size(), 10);
	}
	
}
