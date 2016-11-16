package ass3.collection;

import java.util.ArrayList;

public class ArrListClass {
	public ArrayList<Object> arr = new ArrayList<Object>();

	public String toString() {
		if (arr == null || arr.size() == 0)
			return "[]";
		if (arr.size() == 1) {
			return "[" + arr.get(0) + "]";
		} else if (arr.size() > 1) {
			String string = "[";
			for (int i = 0; i < arr.size() - 1; i++) {
				string += arr.get(i) + ", ";
			}
			return string + arr.get(arr.size() - 1) + "]";
		}
		return "[]";
	}
}
