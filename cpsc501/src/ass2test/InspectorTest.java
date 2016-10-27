package ass2test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ass2.Inspector;

public class InspectorTest {
	Inspector i;
	TestTarget tt;

	@Before
	public void setup() {
		i = new Inspector();
		tt = new TestTarget();
	}
	@After
	public void teardown() {
		i = null;
		tt = null;
	}
	
	@Test
	public void constructorsSimpleTest1() {
		
		i.inspect(tt, true);
	}

}
