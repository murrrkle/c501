package ass2test2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TestTarget2 extends TestTarget implements TestInterface{
	private String message2;
	private ArrayList<String> arr2;
	protected boolean isTest2 = true;
	private TestTarget tt;

	public TestTarget2(String text) {
		setMessage2(text);
		setArr2(new ArrayList<String>());
		tt = new TestTarget();
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public ArrayList<String> getArr2() {
		return arr2;
	}

	public void setArr2(ArrayList<String> arr2) {
		this.arr2 = arr2;
	}

	@Override
	public String print() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
