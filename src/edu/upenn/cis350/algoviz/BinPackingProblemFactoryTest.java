package edu.upenn.cis350.algoviz;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import android.test.ProviderTestCase2;

public class BinPackingProblemFactoryTest extends ProviderTestCase2 {

	public BinPackingProblemFactoryTest(Class providerClass, String providerAuthority) {
		super(providerClass, providerAuthority);
		_factory = new BinPackingProblemFactory(getMockContext());
	}

	private BinPackingProblemFactory _factory;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(_factory.getProblemNames().toString());
	}

}
