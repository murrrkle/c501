package serialize;

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
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private ArrayList<Object> objects;
	
	public Serializer() {
		objects = new ArrayList<Object>();
	}
	
	
	public org.jdom2.Document serialize(Object obj) {
		return null;
	}
	
	public void addNewObject(String name) {

	}

	public void editObject(String name) {

	}

	public void removeObject(String name) {

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
//			// DataInputStream incomingData = new
//			// DataInputStream(socket.getInputStream());
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
