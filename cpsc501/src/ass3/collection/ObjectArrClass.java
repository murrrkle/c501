package ass3.collection;

public class ObjectArrClass {
	public Object[] arr = new Object[3];
	public String toString() {
		String string = "[";
		for (int i = 0; i < 2; i++) {
			string += arr[i] + ", ";
		}
		return string + arr[3] + "]";
	}
}
