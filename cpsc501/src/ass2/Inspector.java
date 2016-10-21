package ass2;

import java.lang.reflect.*;

public class Inspector {
	public void inspect(Object obj, boolean recursive) {
		Class classObj = obj.getClass();
		printClassName(classObj);
		printInterfaces(classObj);
		printFields(classObj, recursive);
		printConstructorsInformation(classObj);
		printMethodInformation(classObj);
	}



	private void printClassName(Class classObject) {
		System.out.println("Full Class Name: " + classObject.getName());
		System.out.println("Class Name: " + classObject.getSimpleName());
		System.out.println("Immediate Superclass: " + classObject.getSuperclass().getName());
		
		System.out.println();
		System.out.println("**********************************************");
	}
	
	private void printFields(Class classObj, boolean recursive) {
		
	}

	private void printInterfaces(Class classObj) {
		
	}

	private void printConstructorsInformation(Class classObject) {
		
	}

	private void printMethodInformation(Class classObject) {

	}
}
