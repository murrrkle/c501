package ass2test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TestTarget implements Iterable {
	private String message;
	private ArrayList<String> arr;
	private TestTarget2 tt2;
	private int[] iarr;

	public TestTarget() {
		message = "default";
		arr = new ArrayList<String>();
		arr.add("hello");
		arr.add("World");
		arr.add("!");
		tt2 = new TestTarget2("gell");
		iarr = new int[]{1, 2, 3, 4, 5, 6, 7};
	}

	public TestTarget(String text) {
		message = text;

	}
	
	public String getMessage() {
		return message;
	}
	
	public void printMessage(String extraMessage) {
		System.out.println(message + extraMessage);
	}
	
	public void display() throws IOException {
		System.out.println("Goodbye cruel world");
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
