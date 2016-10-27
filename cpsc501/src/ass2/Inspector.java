package ass2;

import java.lang.reflect.*;

public class Inspector {
	private Field[] fieldsToInspect;
	
	public void inspect(Object obj, boolean recursive) {
		if (obj == null)
			return;
		
		Class classObj = obj.getClass();
		System.out.println("**********************************************");
		
		printInfo(classObj);
		
		if (recursive) {

		} else {
			
		}
	}
	
	private void printInfo(Class classObj) {
		printClassName(classObj);
		printInterfaces(classObj);
		printFields(classObj);
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
		if (interfaces.length == 0) {
			System.out.println("No implemented interfaces.");
		}
		for (Class i : interfaces) {
			System.out.println("* " + i.getName() + " ");
		}

		System.out.println();
		System.out.println("**********************************************");
	}

	private void printFields(Class classObj) {
		fieldsToInspect = classObj.getDeclaredFields();
		
		System.out.println("Fields:");
		
		if (fieldsToInspect.length == 0) {
			System.out.println("No fields.");
		} else {
			for (Field field : fieldsToInspect) {
				System.out.println("* " + Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + " ");
			}
		}
		
		
		System.out.println();
		System.out.println("**********************************************");
	}

	private void printConstructorsInformation(Class classObj) {
		Constructor[] constructors = classObj.getConstructors();
		if (constructors.length == 0) {
			System.out.println("No constructors.");
		}

		for (Constructor constructor : constructors) {
			System.out.println("Constructor name: " + constructor.getName());

			int modifiers = constructor.getModifiers();
			System.out.println("Constructor modifier: " + Modifier.toString(modifiers));

			Class[] parameterList = constructor.getParameterTypes();
			System.out.println("Constructor parameter types: ");
			if (parameterList.length == 0) {
				System.out.println("No parameters.");
			} else {
				for (Class p : parameterList) {
					System.out.println("* " + p.getName() + " ");
				}
			}

			System.out.println();

			Class[] exceptionList = constructor.getExceptionTypes();
			System.out.println("Exceptions thrown by constructor: ");

			if (exceptionList.length == 0) {
				System.out.println("No thrown exceptions.");
			} else {
				for (Class e : exceptionList) {
					System.out.println("* " + e.getName() + " ");
				}
			}

			System.out.println();
			System.out.println("**********************************************");

		}
	}

	private void printMethodInformation(Class classObj) {

		Method[] methods = classObj.getDeclaredMethods();
		if (methods.length == 0) {
			System.out.println("No methods.");
		}
		for (Method m : methods) {
			System.out.println("method name: " + m.getName());

			System.out.println("Return type of method: " + m.getReturnType());

			int modifiers = m.getModifiers();
			System.out.println("method modifier: " + Modifier.toString(modifiers));

			Class[] parameterList = m.getParameterTypes();
			System.out.println("method parameter types: ");
			if (parameterList.length == 0) {
				System.out.println("No parameters.");
			} else {
				for (Class p : parameterList) {
					System.out.println("* " + p.getName() + " ");
				}
			}

			System.out.println();

			Class[] exceptionList = m.getExceptionTypes();
			System.out.println("Exceptions thrown by method: ");

			if (exceptionList.length == 0) {
				System.out.println("No thrown exceptions.");
			} else {
				for (Class e : exceptionList) {
					System.out.println("* " + e.getName() + " ");
				}
			}

			System.out.println();
			System.out.println("**********************************************");
		}
	}
}
