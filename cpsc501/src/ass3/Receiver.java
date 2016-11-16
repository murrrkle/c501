package ass3;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ass3.serialize.Deserializer;
import ass3.sockets.Server;

public class Receiver {
	
	public static void main(String[] args) {
		Server srv = new Server();
		srv.getXMLFile();
		Document doc = readXML();
		if (doc == null) {
			System.out.println("File doesn't exist.");
			System.exit(0);
		} else {
			Deserializer dsrl = new Deserializer();
			IdentityHashMap<Integer, Object> objects = (IdentityHashMap<Integer, Object>) dsrl.deserialize(doc);
			Inspector inspector = new Inspector();
			for (Map.Entry<Integer, Object> entry : objects.entrySet()) {
				inspector.inspect(entry.getValue(), false);				
			}
		}
	}

	private static Document readXML() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("C:" + File.separator + "XMLData" + File.separator + "outfile.xml");

		Document document = null;
		try {
			document = (Document) builder.build(xmlFile);
		} catch (JDOMException | IOException e) {
			System.out.println(e.getCause());
		}
		return document;

	}
}
