package ass3.serialize;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Deserializer {
	public org.jdom2.Document deserialize(Object obj) {
		return null;
	}
	
	static void readXML(){
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("C:\\XMLData\\file.xml");	
		try {
	
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("staff");
	
			for (int i = 0; i < list.size(); i++) {
				
			   Element node = (Element) list.get(i);
	
			   System.out.println("First Name : " + node.getChildText("firstname"));
			   System.out.println("Last Name : " + node.getChildText("lastname"));
			   System.out.println("Nick Name : " + node.getChildText("nickname"));
			   System.out.println("Salary : " + node.getChildText("salary"));
			   
			   System.out.println("----------------------");
			}
		
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
	

	}
}
