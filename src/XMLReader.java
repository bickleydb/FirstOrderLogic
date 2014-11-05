import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author bickledb
 *
 */
/**
 * @author bickledb
 *
 */
public class XMLReader {
	File toRead;
	DocumentBuilderFactory dbFact;
	DocumentBuilder dBuilder;
	Document doc;

	/**
	 * @param fileName
	 */
	public XMLReader(String fileName) {
		toRead = new File(fileName);
		dbFact = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(toRead);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */

	public NodeList[] getContents() {
		NodeList[] contents = new NodeList[2];
		NodeList predicates = doc.getElementsByTagName("predicate");
		// System.out.println(predicates.getLength());
		NodeList constants = doc.getElementsByTagName("constant");
		contents[0] = predicates;
		contents[1] = constants;
		return contents;
	}

	/**
	 * @return
	 */
	public String getFunctions(boolean forGui) {
		String rtn = "";
		NodeList functions = doc.getElementsByTagName("function");
		for (int i = 0; i < functions.getLength(); i++) {
			Element ele = (Element) functions.item(i);
			int numParams = Integer.parseInt(ele.getAttribute("args"));
			rtn = rtn + ele.getAttribute("name");
			if (forGui) {
			rtn = rtn + " ( ";
			for(int t = numParams-1; t > 0; t--) {
				rtn = rtn + (char)(('z') -t) + " , " ;
			}
			if(numParams != 0)
				rtn = rtn + "z )";
			else 
				rtn = rtn + ")";
			}
			rtn = rtn	+"052015";
			
		}
		return rtn + "052015";

	}

	/**
	 * @return
	 */
	public String getConstants() {
		String rtn = "";
		NodeList functions = doc.getElementsByTagName("constant");
		for (int i = 0; i < functions.getLength(); i++) {
			Element ele = (Element) functions.item(i);
			rtn = rtn + ele.getAttribute("domain") + ":"
					+ ele.getAttribute("name") + "052015";
		}
		return rtn;

	}

	/**
	 * @return
	 */
	public ArrayList<String> getTruth(String functionName) {
		ArrayList<String> rtn = new ArrayList<String>();
		NodeList functions = doc.getElementsByTagName("function");
		for(int i = 0; i < functions.getLength(); i++) {
			Element ele = (Element) functions.item(i);
			if(ele.getAttribute("name").equals(functionName)) {
				NodeList truth = ele.getElementsByTagName("true");
				for(int t = 0; t < truth.getLength(); t++) {
					Element ele2 = (Element) truth.item(t);
					rtn.add(ele2.getAttribute("params"));
				}
			}
		}
		return rtn;
	}

	/**
	 * @param input
	 * @param type
	 * @return
	 */
	public String decodeForName(NodeList input, String type) {
		String rtn = "";
		for (int i = 0; i < input.getLength(); i++) {
			Node curNode = input.item(i);
			Element cur = (Element) curNode;
			if (curNode.getNodeName().equals(type)) {
				rtn = rtn + cur.getAttribute("name");
				if (type.equals("predicate")) {
					int argNum = Integer.parseInt(cur.getAttribute("args"));
					rtn = rtn + "(";
					for (int a = 0; a < argNum - 1; a++) {
						rtn = rtn + (char) ('z' - a) + ",";
					}
					rtn = rtn + "z)";
				}
				rtn = rtn + "052015";
			}
		}
		// System.out.println("RTN " + rtn);
		return rtn;
	}

	/**
	 * @param in
	 * @param forTree
	 * @return
	 */
	public String[] toArr(String in, boolean forTree) {
		String copy = in;
		//System.out.println("Copy: " + copy);
		int totalSize = 0;
		while (copy.indexOf("052015") != -1) {
			copy = copy.substring(copy.indexOf("052015") + 6, copy.length());
			totalSize++;
		}

		String[] rtn = new String[totalSize];
		int rtnIndex = 0;
		copy = in;
		while (!copy.isEmpty()) {
			String add = copy.substring(0, copy.indexOf("052015"));
			if (!forTree)
				add = add.substring(add.indexOf(":") + 1);
			rtn[rtnIndex] = add;
			rtnIndex++;
			copy = copy.substring(copy.indexOf("052015") + 6, copy.length());
		}
		return rtn;

	}

}
