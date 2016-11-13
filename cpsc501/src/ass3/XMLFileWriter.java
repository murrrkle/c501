package ass3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFileWriter {

	public static void main(String[] args) {
		// writeXML();
		// readXML();
		 modifyXML();
	}
	
	static void writeXML(){
		try {

			Element company = new Element("company");
			Document doc = new Document(company);
			//doc.setRootElement(company);
			
			Element staff = new Element("staff");
			staff.setAttribute(new Attribute("id", "1"));
			staff.addContent(new Element("firstname").setText("Allen"));
			staff.addContent(new Element("lastname").setText("Kim"));
			staff.addContent(new Element("nickname").setText("John"));
			staff.addContent(new Element("salary").setText("200000"));
			
			doc.getRootElement().addContent(staff);
			
			Element staff2 = new Element("staff");
			staff2.setAttribute(new Attribute("id", "2"));
			staff2.addContent(new Element("firstname").setText("Steve"));
			staff2.addContent(new Element("lastname").setText("Smith"));
			staff2.addContent(new Element("nickname").setText("Kevin"));
			staff2.addContent(new Element("salary").setText("180000"));

			doc.getRootElement().addContent(staff2);
			
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("C:\\XMLData\\file.xml"));

			System.out.println("File Saved!");
			
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  }
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

	static void modifyXML(){
		 try {

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File("C:\\XMLData\\updateFile.xml");

			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();

			// update staff id attribute
			Element staff = rootNode.getChild("staff");
			staff.getAttribute("id").setValue("200");
			
			// add new age element to staff
			Element age = new Element("age").setText("28");
			staff.addContent(age);
			
			// update salary value
			staff.getChild("salary").setText("7000");
			
			// Remove firstname element
			staff.removeChild("firstname");

			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("C:\\XMLData\\updateFile.xml"));

			System.out.println("File updated!");
			
		  } catch (IOException io) {
			io.printStackTrace();
		  } catch (JDOMException e) {
			e.printStackTrace();
		  }
	}
}
