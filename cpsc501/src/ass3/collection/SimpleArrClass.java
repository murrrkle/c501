package ass3.collection;

public class SimpleArrClass {
	public int[] arr = new int[3];
	public String toString() {
		String string = "[";
		for (int i = 0; i < 2; i++) {
			string += arr[i] + ", ";
		}
		return string + arr[2] + "]";
	}
}
