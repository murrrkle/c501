package ass2;

import java.lang.reflect.*;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		if (obj == null)
			return;

		try {
			Class classObj = obj.getClass();
			printInfo(classObj, obj, recursive);
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	private void printInfo(Class classObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		printClassNameBanner();
		inspectClassNames(classObj);

		printInterfacesBanner();
		inspectInterfaces(classObj);

		printFieldsBanner();
		inspectFields(classObj, obj, recursive);

		printConstructorsBanner();
		inspectConstructors(classObj);

		printMethodsBanner();
		inspectMethods(classObj);

	}

	private void inspectClassNames(Class classObj) {

		System.out.println("Full Class Name: " + classObj.getName());
		System.out.println("Class Name: " + classObj.getSimpleName());
		System.out.println("Declaring Class Name: " + classObj.getDeclaringClass());
		if (!classObj.equals(Object.class))
			System.out.println("Immediate Superclass: " + classObj.getSuperclass().getName());
		System.out.println();
	}

	private void inspectInterfaces(Class classObj) {
		Class[] interfaces = classObj.getInterfaces();
		System.out.println("Implemented Interfaces: ");
		if (interfaces.length == 0) {
			System.out.println("No implemented interfaces.");
			return;
		}

		for (Class i : interfaces) {
			System.out.println("> " + i.getName() + " ");
		}

		System.out.println();
	}

	private void inspectFields(Class classObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fieldsToInspect = classObj.getDeclaredFields();

		if (fieldsToInspect.length == 0) {
			System.out.println("No declared fields.");
			return;
		}

		for (Field f : fieldsToInspect)
			printFieldInfo(obj, recursive, f);

		if (classObj.getSuperclass() != null)
			inspectFields(classObj.getSuperclass(), obj, recursive);
	}

	private void printFieldInfo(Object obj, boolean recursive, Field f) throws IllegalAccessException {
		f.setAccessible(true);
		int modifiers = f.getModifiers();
	
		System.out.println("Field Name: " + f.getName());
		System.out.println("Declaring Class Name: " + f.getDeclaringClass().getName());
		System.out.println("Field Modifiers: " + Modifier.toString(modifiers));
		System.out.println("Field Type: " + f.getType().getSimpleName());
	
		if (recursive) {
			printFieldValue(f, obj);
		} else
			printObjectReference(f, obj);
	
		printSeparator();
	
		if (!f.getType().isPrimitive() && recursive)
			inspect(f.get(obj), true);
	}

	private void printFieldValue(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (f.getType().isArray()) {
			Object fArray = f.get(obj);
			System.out.println("Array Length: " + Array.getLength(fArray));
			printArrayContents(fArray);
		} else
			System.out.println("Field Value: " + f.get(obj));
	}

	private void inspectConstructors(Class classObj) {
		Constructor[] constructors = classObj.getDeclaredConstructors();
		if (constructors.length == 0) {
			System.out.println("No Constructors.");
			return;
		}

		for (Constructor c : constructors)
			printConstructorInfo(c);
		
		if (classObj.getSuperclass() != null)
			inspectConstructors(classObj.getSuperclass());

	}

	private void printConstructorInfo(Constructor c) {
		System.out.println("Constructor Name: " + c.getName());
	
		int modifiers = c.getModifiers();
		System.out.println("Constructor Modifiers: " + Modifier.toString(modifiers));
	
		printParameters(c.getParameterTypes());
	
		System.out.println();
	
		printExceptions(c.getExceptionTypes());
		printSeparator();
	}

	private void inspectMethods(Class classObj) {
		Method[] methods = classObj.getDeclaredMethods();
		if (methods.length == 0) {
			System.out.println("No methods.");
			return;
		}

		for (Method m : methods)
			printMethodInfo(m);
		
		if (classObj.getSuperclass() != null)
			inspectMethods(classObj.getSuperclass());
	}

	private void printMethodInfo(Method m) {
		System.out.println("Method Name: " + m.getName());
		System.out.println("Declaring Class Name: " + m.getDeclaringClass().getName());
		System.out.println("Method Return Type: " + m.getReturnType().getSimpleName());

		System.out.println("Method Modifiers: " + Modifier.toString(m.getModifiers()));
		System.out.println();

		printParameters(m.getParameterTypes());
		System.out.println();
		printExceptions(m.getExceptionTypes());
		printSeparator();
	}

	private void printArrayContents(Object arr) {
		int arrLength = Array.getLength(arr);
		if (arrLength == 0)
			System.out.println("Array Contents: []");
		else {
			System.out.print("Array Contents: [");
			for (int i = 0; i < arrLength - 1; i++) {
				System.out.print(Array.get(arr, i) + ", ");
			}
			System.out.println(Array.get(arr, arrLength - 1) + "]");
		}
	}

	private void printObjectReference(Field f, Object obj) throws IllegalAccessException {
		System.out.println("Field Value: " + f.getClass() + "@" + f.get(obj).hashCode());
	}

	private void printParameters(Class[] parameterList) {
		System.out.println("Parameter Types: ");
		if (parameterList.length == 0) {
			System.out.println();
			System.out.println("No parameters.");
		} else {
			for (Class p : parameterList) {
				System.out.println("> " + p.getName() + " ");
			}
		}
	}

	private void printExceptions(Class[] exceptionList) {
		System.out.println("Thrown Exceptions: ");
		if (exceptionList.length == 0) {
			System.out.println("No thrown exceptions.");
		} else {
			for (Class e : exceptionList) {
				System.out.println("> " + e.getName() + " ");
			}
		}
	}

	private void printClassNameBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                 Class Name                 │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printInterfacesBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                 Interfaces                 │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printFieldsBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                   Fields                   │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printConstructorsBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                Constructors                │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printMethodsBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                   Methods                  │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printSeparator() {
		System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");
	}
}
