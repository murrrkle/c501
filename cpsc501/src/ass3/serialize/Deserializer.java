package ass3.serialize;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.*;

public class Deserializer {
	private IdentityHashMap<Integer, Object> map;
	List<Element> objects;

	public Deserializer() {
		map = new IdentityHashMap<Integer, Object>();
		objects = null;
	}

	public Object deserialize(Document doc) {
		Element root = doc.getRootElement();
		objects = root.getChildren("object");
		for (Element e : objects) {

			if (notYetSerialized(e)) {
				try {					
					toObject(e);

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| SecurityException e1) {

					System.out.println(e1.getCause());
				}
			} else {
				System.out.println("already serialized");
			}
		}
//		System.out.println(map);
//		System.out.println(objects);
		for (Map.Entry<Integer, Object> entry : map.entrySet()) {
			System.out.println(entry.getValue());
		}
		return null;
	}

	private Object toObject(Element e) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class classObj = Class.forName("ass3.collection." + e.getAttribute("class").getValue());
		Object obj = classObj.newInstance();

		Field[] fields = classObj.getDeclaredFields();
		for (Field f : fields) {
			if (isArray(e)) {
				List<Element> arrayElements = e.getChildren();
				// System.out.println("I'm an array");

			} else {
				Element field = e.getChildren().get(0);
				Element fieldValue = field.getChildren().get(0);
				if (fieldIsReference(fieldValue)) {
					Integer refId = Integer.parseInt(fieldValue.getText());
					Element tmp = findElementByID(refId);
					if (notYetSerialized(tmp)) {
						f.set(obj, toObject(tmp));
					} else {
						f.set(obj, map.get(refId));
//						System.out.println("already added");
					}

					// System.out.println("I'm an ObjectClass");
				} else {
					f.set(obj, Integer.parseInt(fieldValue.getText()));
					// System.out.println("I'm a SimpleClass");
				}
			}
		}
		Integer objId = Integer.parseInt(e.getAttribute("id").getValue());
		map.put(objId, obj);
		return obj;
	}
	
	private Element findElementByID(int i) {
		for (Element e : objects) {
			if (Integer.parseInt(e.getAttributeValue("id")) == i) {
				return e;
			}
		}
		return null;
	}

	private boolean notYetSerialized(Element e) {
		for (Map.Entry<Integer, Object> entry : map.entrySet()) {
			if (entry.getKey() == Integer.parseInt(e.getAttribute("id").getValue()))
				return false;
		}
		return true;
	}

	private boolean isArray(Element e) {
		return e.getAttribute("length") != null;
	}

	private boolean fieldIsReference(Element e) {
		return e.getName() == "reference";
	}
}
