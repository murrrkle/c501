package ass3.serialize;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.*;

public class Deserializer {
	public Object deserialize(Document doc) {
		IdentityHashMap<Integer, Object> map = new IdentityHashMap<Integer, Object>();

		Element root = doc.getRootElement();
		List<Element> objects = root.getChildren("object");
		for (Element e : objects) {
			try {
				map.put(Integer.parseInt(e.getAttribute("id").getValue()), toObject(e));

			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException e1) {

				System.out.println(e1.getCause());
			}
		}
		System.out.println(map);
		return null;
	}

	private Object toObject(Element e)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
					Integer id = new Integer(e.getAttributeValue("id"));
					// System.out.println("I'm an ObjectClass");
				} else {
					f.set(obj, Integer.parseInt(fieldValue.getText()));
					// System.out.println("I'm a SimpleClass");
				}
			}
		}

		return obj;
	}

	private boolean isArray(Element e) {
		return e.getAttribute("length") != null;
	}

	private boolean fieldIsReference(Element e) {
		return e.getName() == "reference";
	}
}
