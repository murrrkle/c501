package ass3;

import java.lang.reflect.*;

public class Visualizer {
	public void inspect(Object obj, boolean recursive) {
		if (obj == null)
			return;

		try {
			Class classObj = obj.getClass();
			printInfo(classObj, obj, recursive);
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			System.out.println(e.getCause());
		}
	}

	private void printInfo(Class classObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		printClassNameBanner();
		inspectClassNames(obj, classObj);

		printFieldsBanner();
		inspectFields(classObj, obj, recursive);

	}

	private void inspectClassNames(Object obj, Class classObj) {
		System.out.println("Class Name: " + classObj.getSimpleName());
		System.out.println("ID: " + obj.hashCode());
		System.out.println();
	}

	private void inspectFields(Class classObj, Object obj, boolean recursive)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fieldsToInspect = classObj.getDeclaredFields();

		if (fieldsToInspect.length == 0) {
			return;
		}

		for (Field f : fieldsToInspect)
			printFieldInfo(obj, recursive, f);

		if (classObj.getSuperclass() != null)
			inspectFields(classObj.getSuperclass(), obj, recursive);
	}

	private void printFieldInfo(Object obj, boolean recursive, Field f) throws IllegalAccessException {
		f.setAccessible(true);

		System.out.println("Field Name: " + f.getName());
		System.out.println("Field Type: " + f.getType().getSimpleName());
		System.out.println("Field value: " + f.get(obj));
		System.out.println("Field ID: " + f.get(obj).hashCode());

		printSeparator();
	}

	private void printClassNameBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                 Class Name                 │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printFieldsBanner() {
		System.out.println("┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┑");
		System.out.println("│                   Fields                   │");
		System.out.println("┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┙");
	}

	private void printSeparator() {
		System.out.println("\n┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n");
	}
}
