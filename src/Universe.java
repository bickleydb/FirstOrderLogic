import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Daniel Bickley
 * 
 */
public class Universe {

	
	protected ArrayList<Domain> domains;
	private String fileName;

	/**
	 * 
	 */
	public Universe() {
		this("xml/universe.xml");
		
	}

	/**
	 * 
	 * @param fileName
	 */
	public Universe(String fileName) {
		this.fileName = fileName;
		DomainReader loading = new DomainReader(fileName);
	
	}

}