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

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException
						| NoSuchMethodException | IllegalArgumentException | InvocationTargetException e1) {

					System.out.println(e1.getCause());
				}
			}
		}
		// System.out.println(map);
		// System.out.println(objects);
		for (Map.Entry<Integer, Object> entry : map.entrySet()) {
			System.out.println(entry.getValue().getClass().getName() + " " + entry.getValue());
		}
		return null;
	}

	private Object toObject(Element e) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		if (isArrayList(e)) {
			Class<?> classObj = Class.forName("java.util.ArrayList");
			Object obj = classObj.newInstance();

			Method m = classObj.getMethod("add", new Class[] { Object.class });

			List<Element> arrayElements = e.getChildren();
			int length = arrayElements.size();

			for (int i = 0; i < length; i++) {
				for (Element el : arrayElements) {
					if (el.getAttributeValue("index").equals("" + i)) {
						Integer refId = Integer.parseInt(el.getText());
						Element tmp = findElementByID(refId);
						if (notYetSerialized(tmp)) {
							m.invoke(obj, toObject(tmp));
						} else {
							m.invoke(obj, map.get(refId));
						}
					}
				}
			}

			Integer objId = Integer.parseInt(e.getAttribute("id").getValue());
			map.put(objId, obj);
			return obj;

		} else {
			Class classObj = Class.forName("ass3.collection." + e.getAttribute("class").getValue());
			Object obj = classObj.newInstance();

			Field[] fields = classObj.getDeclaredFields();
			for (Field f : fields) {
				if (isArrListClass(e)) {
					Element field = e.getChildren().get(0);
					Element fieldValue = field.getChildren().get(0);
					Integer refId = Integer.parseInt(fieldValue.getText());
					Element tmp = findElementByID(refId);
					if (notYetSerialized(tmp)) {
						f.set(obj, toObject(tmp));
					} else {
						f.set(obj, map.get(refId));
					}

				} else if (isArray(e)) {
					List<Element> arrayElements = e.getChildren();
					int length = arrayElements.size();
					Object a = null;
					Element firstEl = arrayElements.get(0);

					if (fieldIsReference(firstEl)) {
						a = Array.newInstance(Object.class, length);
					} else {
						a = Array.newInstance(Integer.TYPE, length);
					}

					for (int i = 0; i < length; i++) {
						Element fieldValue = arrayElements.get(i);
						if (fieldIsReference(fieldValue)) {
							Integer refId = Integer.parseInt(fieldValue.getText());
							Element tmp = findElementByID(refId);
							if (notYetSerialized(tmp)) {
								Array.set(a, i, toObject(tmp));
							} else {
								Array.set(a, i, map.get(refId));
							}
						} else {
							Array.set(a, i, Integer.parseInt(fieldValue.getText()));
						}

						f.set(obj, a);
					}

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
						}
					} else {
						f.set(obj, Integer.parseInt(fieldValue.getText()));
					}
				}
			}
			Integer objId = Integer.parseInt(e.getAttribute("id").getValue());
			map.put(objId, obj);
			return obj;
		}
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

	private boolean isArrayList(Element e) {
		return e.getAttributeValue("class").equals("ArrayList");
	}

	private boolean isArrListClass(Element e) {
		return e.getAttributeValue("class").equals("ArrListClass");
	}

	private boolean fieldIsReference(Element e) {
		return e.getName().equals("reference");
	}
}
