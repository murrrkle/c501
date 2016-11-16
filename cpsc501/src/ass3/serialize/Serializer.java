package ass3.serialize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import ass3.collection.*;

public class Serializer {
	private IdentityHashMap<Integer, Object> objects;
	private Document doc;
	private FileWriter fw;

	public Serializer(IdentityHashMap<Integer, Object> map) {
		objects = map;
		Element serialized = new Element("serialized");
		doc = new Document(serialized);
		File f = new File("C:" + File.separator + "XMLData" + File.separator + "file.xml");
		if (!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getKey(Object obj) {
		for (Map.Entry<Integer, Object> o : objects.entrySet()) {
			if (obj.equals(o.getValue()))
				return o.getKey();
		}
		return -1;
	}

	public org.jdom2.Document serialize(Object obj) {
		if (obj instanceof Map.Entry<?, ?>) {
			Integer objId = (Integer) ((Map.Entry) obj).getKey();
			Object objValue = ((Map.Entry) obj).getValue();

			Element entry = new Element("object");
			entry.setAttribute(new Attribute("id", objId.toString()));
			entry.setAttribute(new Attribute("class", objValue.getClass().getSimpleName()));
			doc.getRootElement().addContent(entry);

			try {
				Class classObj = Class.forName(objValue.getClass().getName());

				switch (objValue.getClass().getSimpleName()) {
				case "SimpleClass":
					SimpleClass scTmp = (SimpleClass) objValue;

					Element scField = new Element("field");
					scField.setAttribute(new Attribute("name", "value"));
					scField.setAttribute(new Attribute("declaringclass",
							classObj.getField("value").getDeclaringClass().getSimpleName()));

					Element scValue = new Element("value");
					scValue.setText(scTmp.toString());
					scField.addContent(scValue);
					entry.addContent(scField);
					break;

				case "SimpleArrClass":
					SimpleArrClass sacTmp = (SimpleArrClass) objValue;
					entry.setAttribute(new Attribute("length", Integer.toString(sacTmp.arr.length)));

					for (int i = 0; i < 3; i++) {
						Element sacValue = new Element("value");
						sacValue.setText(Integer.toString(sacTmp.arr[i]));

						entry.addContent(sacValue);
					}

					break;

				case "ObjectClass":
					ObjectClass ocTmp = (ObjectClass) objValue;

					Element ocField = new Element("field");
					ocField.setAttribute(new Attribute("name", "object"));
					ocField.setAttribute(new Attribute("declaringclass",
							classObj.getField("object").getDeclaringClass().getSimpleName()));

					Element ocValue = new Element("reference");
					ocValue.setText(Integer.toString(getKey(ocTmp.object)));
					ocField.addContent(ocValue);

					entry.addContent(ocField);
					break;

				case "ObjectArrClass":
					ObjectArrClass oacTmp = (ObjectArrClass) objValue;

					entry.setAttribute(new Attribute("length", Integer.toString(oacTmp.arr.length)));

					for (int i = 0; i < 3; i++) {
						Element oacValue = new Element("reference");
						oacValue.setText(Integer.toString(getKey(oacTmp.arr[i])));
						entry.addContent(oacValue);
					}

					break;

				case "ArrListClass":
					ArrListClass alcTmp = (ArrListClass) objValue;
					Element alField = new Element("field");
					alField.setAttribute(new Attribute("name", "arr"));
					alField.setAttribute(new Attribute("declaringclass",
							classObj.getField("arr").getDeclaringClass().getSimpleName()));

						Element alValue = new Element("reference");
						alValue.setText(Integer.toString(getKey(alcTmp.arr)));
						alField.addContent(alValue);

					entry.addContent(alField);
					break;
					
				case "ArrayList":
					ArrayList<Object> alTmp = (ArrayList<Object>) objValue;

					for (int i = 0; i < alTmp.size(); i++) {
						Element oacValue = new Element("value");
						oacValue.setAttribute("index", "" + i);
						oacValue.setText(Integer.toString(getKey(alTmp.get(i))));
						entry.addContent(oacValue);
					}

					break;

				default:
					break;
				}
			} catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
				System.out.println(e.getCause());
			}

		}
		return doc;
	}

	public void doSerialize() {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());

		for (Map.Entry<Integer, Object> o : objects.entrySet()) {
			serialize(o);
		}
		try {
			xmlOutput.output(doc, fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
