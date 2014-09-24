import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLReader {
	File toRead;
	FileInputStream inputStream;
	XMLInputFactory factory;
	XMLStreamReader reader;

	public XMLReader() {
		toRead = new File("src/domain.xml");
		try {
			inputStream = new FileInputStream(toRead);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		factory = XMLInputFactory.newInstance();
		try {
			reader = factory.createXMLStreamReader(inputStream);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public String getContents() throws XMLStreamException {

		int currentEvent;
		String rtn = "";
		
		while(reader.hasNext()) {
			currentEvent = reader.getEventType();
			if(currentEvent == XMLStreamConstants.START_ELEMENT) {
				String adding = reader.getAttributeValue(0)+ " " + reader.getAttributeName(1)+" ";
				rtn = rtn + adding;
				System.out.println(rtn);
			}
			reader.nextTag();
		}
		return rtn;
		
		
	}
	
	public String getConstants() throws XMLStreamException {
		int currentEvent;
		String rtn = "";
		boolean readInConstants = false;
		
		while (reader.hasNext()) {
			currentEvent = reader.getEventType();
			if (currentEvent == XMLStreamConstants.START_ELEMENT) {
				String buildInformation = reader.getName().toString();
				if  (buildInformation.equals("Constants")) {
					readInConstants = !readInConstants;
				}
				if  (buildInformation.equals("endoffile"))
					return rtn;
				if (readInConstants == true) {
					if (reader.getAttributeCount() != 0) {
						buildInformation = reader.getAttributeValue(0);
						if(buildInformation.equals("constants")) {
							buildInformation = "";
						}else{
							buildInformation = buildInformation + "052015";
						}
						rtn = rtn + buildInformation;
						System.out.println(rtn);
					}
				}
			}
			
			if (currentEvent == XMLStreamConstants.START_DOCUMENT) {
				reader.next();
			}
			if (currentEvent == XMLStreamConstants.END_DOCUMENT) {
				reader.close();
				return "";
			}
				reader.nextTag();
			
		}
		return "";
		
	}

	public String getPredicates() throws XMLStreamException {
		int currentEvent = reader.getEventType();
		int index = 0;
		String rtn = "";
		int findingPredicates = 0;
		while (reader.hasNext()) {
			currentEvent = reader.getEventType();
			if (currentEvent == XMLStreamConstants.START_ELEMENT) {
				String buildInformation = reader.getName().toString();
				if (buildInformation.equals("Predicates")
						|| buildInformation.equals("Constants")) {
					findingPredicates++;
					if (findingPredicates == 2) {
						return rtn;
					}
				} else {
					if (reader.getAttributeCount() != 0) {
						buildInformation = reader.getAttributeValue(0);
						buildInformation = buildInformation + "(";
						//System.out.println(reader.getAttributeValue(0));
						int numOfParams = Integer.parseInt(reader
								.getAttributeValue(1));
						char startingParam = (char) ((int) 'z' - numOfParams);
						for (int i = 0; i < numOfParams - 1; i++) {
							char actualParam = (char) (startingParam + i);
							buildInformation = buildInformation + actualParam
									+ ",";
						}
						buildInformation = buildInformation + "z)052015";
						rtn = rtn + buildInformation;
					}
				}
			}
			if (currentEvent == XMLStreamConstants.START_DOCUMENT) {
				index = reader.next();
			}
			if (currentEvent == XMLStreamConstants.END_DOCUMENT) {
				reader.close();
				return "";
			}
			index = reader.nextTag();

		}
		return "";
	}

	public static String[] parseArray(String predicates) {
		String copy = predicates;
		int totalSize = 0;
		while(copy.indexOf("052015")!= -1) {
			copy = copy.substring(copy.indexOf("052015")+6, copy.length());
			//System.out.println("Copy:" + copy);
			totalSize++;
		}
		//System.out.println(totalSize);
		String[] rtn = new String[totalSize];
		int rtnIndex = 0;
		copy = predicates;
		while(!copy.isEmpty()) {
			String add = copy.substring(0, copy.indexOf("052015"));
			rtn[rtnIndex] = add;
			rtnIndex++;
			copy = copy.substring(copy.indexOf("052015")+6, copy.length());
			//System.out.println(copy);
		}
		return rtn;
	}

}
