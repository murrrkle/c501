package ass3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ass3.serialize.Deserializer;

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
