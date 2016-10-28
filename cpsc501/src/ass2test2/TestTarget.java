package ass2test2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class TestTarget implements Iterable, Comparable {
	private String message;
	private ArrayList<String> arr;
	private int[] iarr;
	public int ex;
	protected boolean isTest;

	public TestTarget() {
		message = "default";
		arr = new ArrayList<String>();
		arr.add("hello");
		arr.add("World");
		arr.add("!");
		iarr = new int[]{1, 2, 3, 4, 5, 6, 7};
		ex = 0;
		isTest = true;
	}

	public TestTarget(String text) {
		message = text;

	}
	
	public String getMessage() {
		return message;
	}
	
	protected void printMessage(String extraMessage) {
		System.out.println(message + extraMessage);
	}
	
	private void display() throws IOException {
		System.out.println("Goodbye cruel world");
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
