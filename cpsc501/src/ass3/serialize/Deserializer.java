package ass3.serialize;

import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import ass3.collection.*;

public class Deserializer {
	public Object deserialize(Document doc) {
		IdentityHashMap<Integer, Object> objects = new IdentityHashMap<Integer, Object>();
		
		
		Element root = doc.getRootElement();
		List<Element> list = root.getChildren("object");
		for (Element e : list) {
			try {
				Class classObj = Class.forName(e.getAttribute("class").getValue());
			} catch (ClassNotFoundException e1) {
				System.out.println(e1.getCause());
			}
			
			
			
			System.out.println(e.getAttribute("id"));
		}
		
		
		return null;
	}
}
