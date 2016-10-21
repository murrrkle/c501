package ass2;

import java.lang.reflect.*;

public class Inspector {
	public void inspect(Object obj, boolean recursive) {
		Class classObj = obj.getClass();
		System.out.println("**********************************************");
		printClassName(classObj);
		printInterfaces(classObj);
		printFields(classObj, recursive);
		printConstructorsInformation(classObj);
		printMethodInformation(classObj);
	}



	private void printClassName(Class classObj) {
		System.out.println("Full Class Name: " + classObj.getName());
		System.out.println("Class Name: " + classObj.getSimpleName());
		System.out.println("Immediate Superclass: " + classObj.getSuperclass().getName());
		
		System.out.println();
		System.out.println("**********************************************");
	}
	
	private void printInterfaces(Class classObj) {
		
		Class[] interfaces = classObj.getInterfaces();
		System.out.println("Implemented Interfaces: ");
		for (Class i : interfaces) {
			System.out.println("* " + i.getName() + " ");
		}

		System.out.println();
		System.out.println("**********************************************");
	}
	
	private void printFields(Class classObj, boolean recursive) {
		
	}



	private void printConstructorsInformation(Class classObj) {
		Constructor[] constructors = classObj.getConstructors();

		for (Constructor constructor : constructors) {
			System.out.println("Constructor name: " + constructor.getName());

			int modifiers = constructor.getModifiers();
			System.out.println("Constructor modifier: " + Modifier.toString(modifiers));

			Class[] parameterList = constructor.getParameterTypes();
			System.out.println("Constructor parameter types: ");
			for (Class p : parameterList) {
				System.out.println("* " + p.getName() + " ");
			}

			System.out.println();

			Class[] exceptionList = constructor.getExceptionTypes();
			System.out.println("Exceptions thrown by constructor: ");

			for (Class e : exceptionList) {
				System.out.println("* " + e.getName() + " ");

			}

			System.out.println();
			System.out.println("**********************************************");

		}
	}

	private void printMethodInformation(Class classObj) {

		Method[] methods = classObj.getDeclaredMethods();
		for (Method m : methods) {
			System.out.println("method name: " + m.getName());

			System.out.println("Return type of method: " + m.getReturnType());

			int modifiers = m.getModifiers();
			System.out.println("method modifier: " + Modifier.toString(modifiers));

			Class[] parameterList = m.getParameterTypes();
			System.out.println("method parameter types: ");
			for (Class p : parameterList) {
				System.out.println("* " + p.getName() + " ");
			}

			System.out.println();

			Class[] exceptionList = m.getExceptionTypes();
			System.out.println("Exceptions thrown by method: ");

			for (Class e : exceptionList) {
				System.out.println("* " + e.getName() + " ");

			}

			System.out.println();
			System.out.println("**********************************************");
		}
	}
}
