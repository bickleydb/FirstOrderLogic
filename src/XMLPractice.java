import javax.xml.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
public class XMLPractice {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		File xmlDoc = new File("src/domain.xml");
		InputStream is = new FileInputStream(xmlDoc);
		XMLInputFactory factor = XMLInputFactory.newInstance();
		XMLStreamReader reader = factor.createXMLStreamReader(is);
		int index = reader.next();
		while(reader.hasNext()) {
			int currentEvent = reader.getEventType();
			if (currentEvent == XMLStreamConstants.START_ELEMENT) {
				String information = "";
				if(reader.getAttributeCount()!= 0) {
				information = information + reader.getAttributeValue(0);
				int numberArgs = Integer.parseInt(reader.getAttributeValue(1));
				information = information + "(";
				char variable = (char)( (int) ('z') - numberArgs);
				for (int i = 0; i < numberArgs; i++) {
					Character variableName = (char)(variable+i);
					information = information+ variableName.toString()+",";
				}
				information = information + ")";
				System.out.println(information);

				}
			
			}
			if (currentEvent == XMLStreamConstants.NAMESPACE) {
				System.out.println(reader.getNamespaceCount());
			}
			if (currentEvent == XMLStreamConstants.ATTRIBUTE) {
				System.out.println(reader.getAttributeCount());
			}
			if(currentEvent == XMLStreamConstants.END_DOCUMENT) {
				reader.close();
				return;
			}
		    index = reader.next();
			
		}
	  }
	}


