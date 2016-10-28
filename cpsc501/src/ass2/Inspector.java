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

		printClassName(classObj);
		printInterfaces(classObj);
		printFields(classObj, obj, recursive);
		printConstructorsInformation(classObj);
		printMethodInformation(classObj);

	}

	private void printClassName(Class classObj) {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                 Class Name                 │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
		System.out.println("Full Class Name: " + classObj.getName());
		System.out.println("Class Name: " + classObj.getSimpleName());
		System.out.println("Declaring Class Name: " + classObj.getDeclaringClass());
		if (!classObj.equals(Object.class))
			System.out.println("Immediate Superclass: " + classObj.getSuperclass().getName());
		System.out.println();
	}

	private void printInterfaces(Class classObj) {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                 Interfaces                 │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");

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

	private void printFields(Class classObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                   Fields                   │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");

		Field[] fieldsToInspect = classObj.getDeclaredFields();

		if (fieldsToInspect.length == 0) {
			System.out.println("No declared fields.");
			return;
		}

		for (Field field : fieldsToInspect) {

			field.setAccessible(true);
			int modifiers = field.getModifiers();

			System.out.println("Field Name: " + field.getName());
			System.out.println("Field Modifiers: " + Modifier.toString(modifiers));

			System.out.println("Field Type: " + field.getType().getSimpleName());

			if (field.getType().isArray()) {
				Object fArray = field.get(obj);
				System.out.println("Array Length: " + Array.getLength(fArray));
				if (Array.getLength(fArray) == 0)
					System.out.println("Array Contents: []");
				else {

					System.out.print("Array Contents: [");
					for (int i = 0; i < Array.getLength(fArray) - 1; i++) {
						System.out.print(Array.get(fArray, i) + ", ");
					}
					System.out.println(Array.get(fArray, Array.getLength(fArray) - 1) + "]");
				}
			} else
				System.out.println("Field Value: " + field.get(obj));

			System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");

			if (!field.getType().isPrimitive() && recursive)
				inspect(field.get(obj), true);
		}

		printSuperclassFields(classObj, obj, recursive);
	}

	private void printSuperclassFields(Class cObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException {
		Class classObj = cObj.getSuperclass();
		if (classObj != null) {

			Field[] fieldsToInspect = classObj.getDeclaredFields();

			if (fieldsToInspect.length == 0)
				return;

			for (Field field : fieldsToInspect) {

				field.setAccessible(true);
				int modifiers = field.getModifiers();

				System.out.println("Field Name: " + field.getName());
				System.out.println("Declaring Class Name: " + field.getDeclaringClass().getName());
				System.out.println("Field Modifiers: " + Modifier.toString(modifiers));

				System.out.println("Field Type: " + field.getType().getSimpleName());

				if (field.getType().isArray()) {
					Object fArray = field.get(obj);
					System.out.println("Array Length: " + Array.getLength(fArray));
					if (Array.getLength(fArray) == 0)
						System.out.println("Array Contents: []");
					else {

						System.out.print("Array Contents: [");
						for (int i = 0; i < Array.getLength(fArray) - 1; i++) {
							System.out.print(Array.get(fArray, i) + ", ");
						}
						System.out.println(Array.get(fArray, Array.getLength(fArray) - 1) + "]");
					}
				} else
					System.out.println("Field Value: " + field.get(obj));

				System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");

				if (!field.getType().isPrimitive() && recursive)
					inspect(field.get(obj), true);
			}
			printSuperclassFields(classObj, obj, recursive);
		}
	}

	private void printConstructorsInformation(Class classObj) {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                Constructors                │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
		Constructor[] constructors = classObj.getDeclaredConstructors();
		if (constructors.length == 0) {
			System.out.println("No Constructors.");
			return;
		}

		for (Constructor constructor : constructors) {
			System.out.println("Constructor Name: " + constructor.getName());

			int modifiers = constructor.getModifiers();
			System.out.println("Constructor Modifiers: " + Modifier.toString(modifiers));

			Class[] parameterList = constructor.getParameterTypes();
			System.out.println("Constructor Parameter Types: ");
			if (parameterList.length == 0) {
				System.out.println();
				System.out.println("No parameters.");
			} else {
				for (Class p : parameterList) {
					System.out.println("> " + p.getName() + " ");
				}
			}

			System.out.println();

			Class[] exceptionList = constructor.getExceptionTypes();
			System.out.println("Thrown Exceptions: ");

			if (exceptionList.length == 0) {
				System.out.println("No thrown exceptions.");
			} else {
				for (Class e : exceptionList) {
					System.out.println("> " + e.getName() + " ");
				}
			}
			System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");
			printSuperclassConstructorsInformation(classObj);
		}
	}

	private void printSuperclassConstructorsInformation(Class cObj) {
		Class classObj = cObj.getSuperclass();
		if (classObj != null) {

			Constructor[] constructors = classObj.getDeclaredConstructors();
			if (constructors.length == 0)
				return;

			for (Constructor constructor : constructors) {
				System.out.println("Constructor Name: " + constructor.getName());

				int modifiers = constructor.getModifiers();
				System.out.println("Constructor Modifiers: " + Modifier.toString(modifiers));

				Class[] parameterList = constructor.getParameterTypes();
				System.out.println("Constructor Parameter Types: ");
				if (parameterList.length == 0) {
					System.out.println();
					System.out.println("No parameters.");
				} else {
					for (Class p : parameterList) {
						System.out.println("> " + p.getName() + " ");
					}
				}

				System.out.println();

				Class[] exceptionList = constructor.getExceptionTypes();
				System.out.println("Thrown Exceptions: ");

				if (exceptionList.length == 0) {
					System.out.println("No thrown exceptions.");
				} else {
					for (Class e : exceptionList) {
						System.out.println("> " + e.getName() + " ");
					}
				}
				System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");
			}
			printSuperclassConstructorsInformation(classObj);
		}
	}

	private void printMethodInformation(Class classObj) {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                   Methods                  │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");

		Method[] methods = classObj.getDeclaredMethods();
		if (methods.length == 0) {
			System.out.println("No methods.");
			return;
		}

		for (Method m : methods) {
			System.out.println("Method Name: " + m.getName());

			System.out.println("Method Return Type: " + m.getReturnType());

			int modifiers = m.getModifiers();
			System.out.println("Method Modifiers: " + Modifier.toString(modifiers));
			System.out.println();

			Class[] parameterList = m.getParameterTypes();
			System.out.println("Method Parameter Types: ");
			if (parameterList.length == 0) {
				System.out.println("No parameters.");
			} else {
				for (Class p : parameterList) {
					System.out.println("> " + p.getSimpleName() + " ");
				}
			}

			System.out.println();

			Class[] exceptionList = m.getExceptionTypes();
			System.out.println("Thrown Exceptions: ");

			if (exceptionList.length == 0) {
				System.out.println("No thrown exceptions.");
			} else {
				for (Class e : exceptionList) {
					System.out.println("> " + e.getName() + " ");
				}
			}

			System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");
		}
	}
}
