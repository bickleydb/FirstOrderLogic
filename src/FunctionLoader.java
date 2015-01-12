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

public class FunctionLoader {

	private File XMLFile;
	private DocumentBuilderFactory fact;
	private DocumentBuilder docBuild;
	private Document doc;

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

	public ArrayList<Function> readFunctions() {
		ArrayList<Function> functions = new ArrayList<Function>();
		NodeList xmlData = getFunctionDetails();
		for (int i = 0; i < xmlData.getLength(); i++) {
			functions.add(parseFunctionElements((Element) xmlData.item(i)));
		}
		return functions;
	}
	
	public ArrayList<Const> readConstants() {
		ArrayList<Const> constants = new ArrayList<Const>();
		NodeList xmlData = getConstantDetails();
		for(int i = 0; i < xmlData.getLength(); i++) {
			constants.add(parseConstantElements((Element) xmlData.item(i)));
		}
		return constants;
	}
	
	private NodeList getConstantDetails() {
		NodeList consts = doc.getElementsByTagName("constant");
		return consts;
	}
	
	private Const parseConstantElements(Element cur) {
		Const newConst = new Const();
		newConst.setDomain(cur.getAttribute("domain"));
		newConst.setName(cur.getAttribute("name"));
		return newConst;
		
	}

	private Function parseFunctionElements(Element cur) {
		Function newFunction = new Function();
		newFunction.setFunctionName(cur.getAttribute("name"));
		newFunction.setDomain(cur.getAttribute("domain"));
		newFunction.setGoodConfigurations(getTruths(cur
				.getElementsByTagName("true")));
		return newFunction;

	}

	private String[][] getTruths(NodeList ele) {
		String[][] answers = new String[ele.getLength()][];
		for(int i = 0; i < ele.getLength(); i++) {
         answers[i] = seperate((Element) ele.item(i));
		}
		return answers;
	}
	
	private String[] seperate( Element element) {
		String param = element.getAttribute("params");
		int numSections = 1;
		for(int i = 0; i < param.length(); i++) 
			if(param.charAt(i) == ':') numSections++;
		String[] seperatedParams = new String[numSections];
		int paramIndex = 0;
		while(param.indexOf(":") != -1 ) {
			seperatedParams[paramIndex++] = param.substring(0, param.indexOf(":"));
			param = param.substring(param.indexOf(":")+1);
		}
		seperatedParams[paramIndex] = param;
		return seperatedParams;
		
	}

	private NodeList getFunctionDetails() {
		NodeList functions = doc.getElementsByTagName("function");
		return functions;
	}
	
	public static String[] getFunctionNames(ArrayList<Function> arr) {
		String[] rtn = new String[arr.size()];
		for(int i = 0; i < arr.size(); i++) 
			rtn[i] = arr.get(i).getNameParams();
		return rtn;
	}
	
	public static String[] getConstNames(ArrayList<Const> arr) {
		String[] rtn = new String[arr.size()];
		for(int i = 0; i < arr.size(); i++) 
			rtn[i] = arr.get(i).getName();
		return rtn;
	}
	

	public static void main(String[] args) {
		FunctionLoader funct = new FunctionLoader("src/world1.xml");
		ArrayList<Function> fun = funct.readFunctions();
		ArrayList<Const> consts = funct.readConstants();
		for(int i = 0; i < fun.size(); i++)
			System.out.println(fun.get(i));
		for(int i =0; i< consts.size(); i++) {
			System.out.println(consts.get(i));
		}
	}

}
