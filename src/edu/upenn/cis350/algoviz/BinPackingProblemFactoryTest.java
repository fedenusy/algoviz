package edu.upenn.cis350.algoviz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

public class BinPackingProblemFactoryTest {

	@Test
	public void testFactory() {
		ArrayList<Bin> bins1 = new ArrayList<Bin>();
		ArrayList<BinObject> objs1 = new ArrayList<BinObject>();
		bins1.add(new Bin(25));
		objs1.add(new BinObject(5, 10, null));
		objs1.add(new BinObject(10, 20, null));
		objs1.add(new BinObject(17, 32, null));
		objs1.add(new BinObject(3, 11, null));
		objs1.add(new BinObject(8, 20, null));
		double sol1 = BinPackingProblemFactory.calculateSolution(bins1, objs1);
		assertTrue(sol1 == 53);
		
		ArrayList<Bin> bins2 = new ArrayList<Bin>();
		ArrayList<BinObject> objs2 = new ArrayList<BinObject>();
		bins2.add(new Bin(25));
		objs2.add(new BinObject(5, 10, null));
		objs2.add(new BinObject(10, 20, null));
		objs2.add(new BinObject(17, 32, null));
		objs2.add(new BinObject(3, 11, null));
		objs2.add(new BinObject(8, 20, null));
		objs2.add(new BinObject(10, 20, null));
		objs2.add(new BinObject(17.5, 32, null));
		objs2.add(new BinObject(3, 11, null));
		objs2.add(new BinObject(4, 9, null));
		objs2.add(new BinObject(8, 20, null));
		objs2.add(new BinObject(3, 4, null));
		objs2.add(new BinObject(5, 7.5, null));
		objs2.add(new BinObject(2, 4, null));
		double sol2 = BinPackingProblemFactory.calculateSolution(bins2, objs2);
		assertTrue(sol2 >= 53);
		
	}

}