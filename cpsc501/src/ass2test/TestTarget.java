package ass2test;

import java.io.IOException;

public class TestTarget {
	private String message;

	public TestTarget() {
		message = "default";
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
}
