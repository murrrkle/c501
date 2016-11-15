package ass3.serialize;

import java.awt.im.InputContext;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.zip.InflaterInputStream;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import ass3.collection.*;

public class Serializer {
	private IdentityHashMap<Integer, Object> objects;
	Document doc;
	FileWriter fw;
	
	public Serializer(IdentityHashMap<Integer, Object> map) {
		objects = map;
		Element serialized = new Element("serialized");
		doc = new Document(serialized);
		File f = new File("C:" + File.separator + "XMLData" + File.separator + "file.xml");
		if (!f.exists()) {
			try {
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
	
	public org.jdom2.Document serialize(Object obj) {
		if (obj instanceof Map.Entry<?, ?>) {
			Integer objId = (Integer) ((Map.Entry) obj).getKey();
			Object objValue = ((Map.Entry) obj).getValue();
			
			Element entry = new Element("object");
			entry.setAttribute(new Attribute("id", objId.toString()));
			entry.setAttribute(new Attribute("class", objValue.getClass().getSimpleName()));
			doc.getRootElement().addContent(entry);
			
			switch (objValue.getClass().getSimpleName()) {
				case "SimpleClass":
					SimpleClass tmp = (SimpleClass) objValue;
					Element field = new Element("object");
					field.setAttribute(new Attribute("name", tmp.toString()));
					break;
				default:
					break;
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
	
//	static void writeXML(){
//		try {
//
//			Element company = new Element("company");
//			Document doc = new Document(company);
//			//doc.setRootElement(company);
//			
//			Element staff = new Element("staff");
//			staff.setAttribute(new Attribute("id", "1"));
//			staff.addContent(new Element("firstname").setText("Allen"));
//			staff.addContent(new Element("lastname").setText("Kim"));
//			staff.addContent(new Element("nickname").setText("John"));
//			staff.addContent(new Element("salary").setText("200000"));
//			
//			doc.getRootElement().addContent(staff);
//			
//			Element staff2 = new Element("staff");
//			staff2.setAttribute(new Attribute("id", "2"));
//			staff2.addContent(new Element("firstname").setText("Steve"));
//			staff2.addContent(new Element("lastname").setText("Smith"));
//			staff2.addContent(new Element("nickname").setText("Kevin"));
//			staff2.addContent(new Element("salary").setText("180000"));
//
//			doc.getRootElement().addContent(staff2);
//			
//			// new XMLOutputter().output(doc, System.out);
//			XMLOutputter xmlOutput = new XMLOutputter();
//
//			// display nice nice
//			xmlOutput.setFormat(Format.getPrettyFormat());
//			xmlOutput.output(doc, new FileWriter("C:\\XMLData\\file.xml"));
//
//			System.out.println("File Saved!");
//			
//		  } catch (IOException io) {
//			System.out.println(io.getMessage());
//		  }
//	}
	
//	public static void main(String[] args) {
//		try {
//			int portNumber = 6666;
//			ServerSocket serverSocket = new ServerSocket(portNumber);
//			Socket socket = serverSocket.accept();
//			// DataInputStream incomingData = new DataInputStream(socket.getInputStream());
//			// String receivedMessage = (String) incomingData.readUTF();
//			// System.out.println("message: " + receivedMessage);
//
//			String outputFile = "C:\\XMLData\\outfile.xml";
//			File myFile = new File(outputFile);
//			FileOutputStream fos = new FileOutputStream(myFile);
//			BufferedOutputStream out = new BufferedOutputStream(fos);
//			byte[] buffer = new byte[1024];
//			int count;
//			InputStream in = socket.getInputStream();
//			while ((count = in.read(buffer)) > 0) {
//				byte revisedBuffer[] = new String(buffer).replaceAll("\0", "").getBytes();
//				fos.write(revisedBuffer);
//			}
//			fos.close();
//
//			serverSocket.close();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
}
