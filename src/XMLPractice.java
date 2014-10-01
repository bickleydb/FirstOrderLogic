import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLPractice {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File("src/domain.xml");
		DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFact.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		//System.out.println(doc.getDocumentElement().getNodeName());
		/*
		for (int i = 0; i < test.getLength(); i++) {
			Node node = test.item(i);
			Element ele = (Element) node;
			System.out.println(ele.getAttribute("name"));
		
		}*/
	}

}
