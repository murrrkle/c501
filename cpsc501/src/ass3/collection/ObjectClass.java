package ass3.collection;

public class ObjectClass {
	public Object object = null;
	
	public String toString() {
		if (object != null) {
			return object.toString();
		}
		return "[]";
	}
}
