import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Contains functions that aid in reading the contents of worlds defined in XML
 * files. Each instance reads in the contents of the file denoted by the
 * "XMLFile" instance variable.
 * 
 * The class uses extensive use of w3c classes as well as javax classes to read
 * in the correct information.
 * 
 * @author Daniel Bickley
 * 
 */
public class FunctionLoader {

	private File XMLFile;
	private DocumentBuilderFactory fact;
	private DocumentBuilder docBuild;
	private Document doc;

	/**
	 * Constructor that reads in a file name and initializes all of the
	 * necessary variables. Note that the information is read into the "doc"
	 * instance variable and the class contains methods that parses "doc" to
	 * find the Constants, Functions, and other important information.
	 * 
	 * @param fileName
	 */
	public FunctionLoader(String fileName) {
		XMLFile = new File(fileName);
		fact = DocumentBuilderFactory.newInstance();
		try {
			docBuild = fact.newDocumentBuilder();
			doc = docBuild.parse(XMLFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates an ArrayList of every Function that is contained in the XML file.
	 * 
	 * @return ArrayList of all of the functions that is defined in the XML file
	 *         being read.
	 */
	public ArrayList<Function> readFunctions() {
		ArrayList<Function> functions = new ArrayList<Function>();
		NodeList xmlData = getFunctionDetails();
		for (int i = 0; i < xmlData.getLength(); i++) {
			functions.add(parseFunctionElements((Element) xmlData.item(i)));
		}
		return functions;
	}

	/**
	 * Creates an ArrayList of every Constant that is contained in the XML file.
	 * 
	 * @return ArrayList of all of the Constant that is defined in the XML file
	 *         being read.
	 */
	public ArrayList<Const> readConstants() {
		ArrayList<Const> constants = new ArrayList<Const>();
		NodeList xmlData = getConstantDetails();
		for (int i = 0; i < xmlData.getLength(); i++) {
			constants.add(parseConstantElements((Element) xmlData.item(i)));
		}
		return constants;
	}

	/*
	 * Returns a NodeList that contains every element that has a tag name of constant
	 */
	private NodeList getConstantDetails() {
		NodeList consts = doc.getElementsByTagName("constant");
		return consts;
	}

	/*
	 * Creates Const objects defined in each Element and returns the object
	 */
	private Const parseConstantElements(Element cur) {
		Const newConst = new Const();
		newConst.setDomain(cur.getAttribute("domain"));
		newConst.setName(cur.getAttribute("name"));
		return newConst;

	}

	/*
	 * Creates Function objects defined in each Element and returns the object
	 */
	private Function parseFunctionElements(Element cur) {
		Function newFunction = new Function();
		newFunction.setFunctionName(cur.getAttribute("name"));
		newFunction.setDomain(cur.getAttribute("domain"));
		newFunction.setGoodConfigurations(getTruths(cur
				.getElementsByTagName("true")));
		return newFunction;

	}

	/*
	 * Returns a string representation of every combination of parameters that
	 * would be true for a given Function.
	 */
	private String[][] getTruths(NodeList ele) {
		String[][] answers = new String[ele.getLength()][];
		for (int i = 0; i < ele.getLength(); i++) {
			answers[i] = seperate((Element) ele.item(i));
		}
		return answers;
	}

	/*
	 * Separates the information of a specific element and inserts the parameters
	 * into a String[] to help create Function objects.
	 */
	private String[] seperate(Element element) {
		String param = element.getAttribute("params");
		int numSections = 1;
		for (int i = 0; i < param.length(); i++)
			if (param.charAt(i) == ':')
				numSections++;
		String[] seperatedParams = new String[numSections];
		int paramIndex = 0;
		while (param.indexOf(":") != -1) {
			seperatedParams[paramIndex++] = param.substring(0,
					param.indexOf(":"));
			param = param.substring(param.indexOf(":") + 1);
		}
		seperatedParams[paramIndex] = param;
		return seperatedParams;

	}

	/*
	 * Returns a NodeList that contains all of the entries in the XMLFile that have a tag
	 * named "function"
	 */
	private NodeList getFunctionDetails() {
		NodeList functions = doc.getElementsByTagName("function");
		return functions;
	}

	/**
	 * For debugging of the class.
	 * @param args
	 */
	public static void main(String[] args) {
		FunctionLoader funct = new FunctionLoader("src/world1.xml");
		ArrayList<Function> fun = funct.readFunctions();
		ArrayList<Const> consts = funct.readConstants();
		for (int i = 0; i < fun.size(); i++)
			System.out.println(fun.get(i));
		for (int i = 0; i < consts.size(); i++) {
			System.out.println(consts.get(i));
		}
	}

}
