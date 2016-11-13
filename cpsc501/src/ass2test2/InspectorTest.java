package ass2test2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ass2.Inspector;

public class InspectorTest {
	Inspector i;
	TestTarget tt;
	TestTarget2 tt2;

	@Before
	public void setup() {
		i = new Inspector();
		tt = new TestTarget();
		tt2 = new TestTarget2("hello");
	}
	@After
	public void teardown() {
		i = null;
		tt = null;
		tt2 = null;
	}
	
	@Test
	public void nonRecursiveTest() {
		i.inspect(tt, false);
	}
	
	@Test
	public void recursiveTest() {
		i.inspect(tt, true);
	}
	
	@Test
	public void childNonRecursiveTest() {
		i.inspect(tt2, false);
	}
	
	@Test
	public void childRecursiveTest() {
		i.inspect(tt2, true);
	}

}
