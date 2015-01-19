import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * The main class of the program, and is responsible for the GUI of the program.
 * This class handles calling the correct methods from the correct classes while
 * handling the GUI.
 * 
 * @author Daniel Bickley
 * 
 */
public class ProgramGui extends JFrame {

	protected JComboBox<String> functions;
	protected JComboBox<String> worldSelection;
	protected JComboBox<String> constants;

	protected JTextField texts;
	protected JTextArea out;
	protected FunctionLoader reader;
	protected String fileName;

	private TextButtonListener text;
	private InputDocumentListener heyListen;
	private PredicateConstantListener placePredicate;
	protected ArrayList<World> worlds;

	/**
	 * Basic constructor that initializes everything required for the GUI to
	 * function. From here, the program is driven by the listeners to create and
	 * test the user's input.
	 */
	public ProgramGui() {
		worlds = new ArrayList<World>();
		loadWorlds();
		instantiateVariables();
		chooseFeedbackFolder();
		this.setSize(500, 500);
		this.setLayout(new BorderLayout());
		createRelationPanel();
		createCharacterPanel();
		createUserInputPanel();
		createOutputPanel();
		this.setVisible(true);
	}

	/*
	 * Loads in every world within the xml folder. If the folder does not exist,
	 * then the folder is created.
	 */
	private void loadWorlds() {
		File xmlFolder = new File("xml/");
		if (!xmlFolder.exists())
			xmlFolder.mkdir();
		String[] files = xmlFolder.list();
		for (int i = 0; i < files.length; i++) {
			worlds.add(new World("xml/" + files[i]));
		}

	}

	/*
	 * Creates a JOptonPane that is used to determine the user's desired output
	 * file. If the user doesn't care, the program will default to "output.txt"
	 */
	private void chooseFeedbackFolder() {
		fileName = JOptionPane
				.showInputDialog(this,
						"What would you like your feedback saved to? Leave it empty if you don't care.");
		if (fileName.equals(""))
			fileName = "output";
		fileName = "Feedback/" + fileName + ".txt";
		text = new TextButtonListener(this);

	}

	/*
	 * Instantiates variables in a separate method for readability.
	 */
	private void instantiateVariables() {
		heyListen = new InputDocumentListener(this);
		placePredicate = new PredicateConstantListener(this);
	}

	/*
	 * Gets the names of every world that has been read in for the user to
	 * select
	 */
	private String[] getWorldNames() {
		String[] rtn = new String[worlds.size()];
		for (int i = 0; i < rtn.length; i++) {
			String fileName = worlds.get(i).getFilename();
			rtn[i] = fileName.substring(fileName.indexOf("/") + 1);
		}
		return rtn;
	}

	/*
	 * Creates the section of the GUI that contains the JComboBoxes to select
	 * the world, constants, and functions that the user wants to utilize.
	 */
	private void createRelationPanel() {
		JPanel pane = new JPanel();

		JLabel worldLabel = new JLabel("Worlds");
		JLabel functionLabel = new JLabel("Functions");
		JLabel constantLabel = new JLabel("Constants");

		worldLabel.setAlignmentX(CENTER_ALIGNMENT);
		constantLabel.setAlignmentX(CENTER_ALIGNMENT);
		functionLabel.setAlignmentX(CENTER_ALIGNMENT);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		worldSelection = new JComboBox<String>(getWorldNames());
		worldSelection.addActionListener(placePredicate);
		int currentWorld = worldSelection.getSelectedIndex();

		functions = new JComboBox<String>(worlds.get(currentWorld)
				.getFunctionNames());
		functions.addActionListener(placePredicate);
		constants = new JComboBox<String>(worlds.get(currentWorld)
				.getConstantNames());
		constants.addActionListener(placePredicate);

		pane.add(worldLabel);
		pane.add(worldSelection);
		pane.add(functionLabel);
		pane.add(functions);
		pane.add(constantLabel);
		pane.add(constants);

		this.add(pane, BorderLayout.WEST);

	}

	/*
	 * Creates the section of the GUI where the user can select mathematical
	 * symbols and variables to add into the User input.
	 */
	private void createCharacterPanel() {
		JPanel chars = new JPanel();
		chars.setLayout(new GridLayout(0, 1));
		JLabel symbols = new JLabel("Symbols");
		chars.add(symbols);

		JButton forAll = new JButton(Character.toString(Constants.FOR_ALL));
		JButton thereExists = new JButton(
				Character.toString(Constants.THERE_EXISTS));
		JButton and = new JButton(Character.toString(Constants.AND));
		JButton or = new JButton(Character.toString(Constants.OR));
		JButton impliles = new JButton(Character.toString(Constants.IMPLIES));
		JButton iff = new JButton(Character.toString(Constants.IFF));
		JButton x = new JButton(Character.toString(Constants.X));
		JButton y = new JButton(Character.toString(Constants.Y));
		JButton xPrime = new JButton(Constants.X_PRIME);
		JButton yPrime = new JButton(Constants.Y_PRIME);

		chars.add(forAll);
		chars.add(thereExists);
		chars.add(and);
		chars.add(or);
		chars.add(impliles);
		chars.add(iff);
		chars.add(x);
		chars.add(y);
		chars.add(xPrime);
		chars.add(yPrime);

		forAll.addActionListener(text);
		thereExists.addActionListener(text);
		and.addActionListener(text);
		or.addActionListener(text);
		impliles.addActionListener(text);
		iff.addActionListener(text);
		x.addActionListener(text);
		y.addActionListener(text);
		xPrime.addActionListener(text);
		yPrime.addActionListener(text);

		forAll.setName(Character.toString(Constants.FOR_ALL));
		thereExists.setName(Character.toString(Constants.THERE_EXISTS));
		and.setName(Character.toString(Constants.AND) + " ");
		or.setName(Character.toString(Constants.OR) + " ");
		impliles.setName(Character.toString(Constants.IMPLIES) + " ");
		iff.setName(Character.toString(Constants.IFF) + " ");
		x.setName(Character.toString(Constants.X) + " ");
		y.setName(Character.toString(Constants.Y) + " ");
		xPrime.setName(Constants.X_PRIME + " ");
		yPrime.setName(Constants.Y_PRIME + " ");

		chars.setBorder(BorderFactory.createDashedBorder(Color.RED));
		this.add(chars, BorderLayout.EAST);
	}

	/*
	 * Creates the section of the GUI where the user types in the statement they would
	 * like to test against the world 
	 */
	private void createUserInputPanel() {
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
		texts = new JTextField("Enter Your Statement Here\r", 40);
		texts.setEditable(true);
		Document editing = texts.getDocument();
		editing.addDocumentListener(heyListen);
		JButton send = new JButton("ENTER");
		send.setName("enter");
		send.addActionListener(text);
		input.add(texts);
		input.add(send);
		this.add(input, BorderLayout.SOUTH);

	}

	/*
	 * Creates the section of the GUI where the output of the program is shown to the user. 
	 * The output will also be saved in a file, but the GUI also shows the output to the 
	 * user as soon as possible.
	 */
	private void createOutputPanel() {
		JPanel output = new JPanel();
		output.setLayout(new BorderLayout());
		JLabel outputName = new JLabel("Output");
		output.add(outputName, BorderLayout.NORTH);
		output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		out = new JTextArea(100, 100);
		JScrollPane toScroll = new JScrollPane(out);
		toScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		output.add(toScroll, BorderLayout.EAST);
		toScroll.setSize(20, 200);
		toScroll.setSize(100, 100);
		out.setEditable(false);
		output.add(out);
		this.add(output, BorderLayout.CENTER);
	}

	/**
	 * Makes it go.
	 * @param args
	 */
	public static void main(String[] args) {
		ProgramGui gui = new ProgramGui();
	}

	/*
	 * Deletes everything in the user input panel.
	 */
	protected void removeInput() {
		// System.out.println("REMOVE");
		Document delete = texts.getDocument();
		try {
			delete.remove(0, delete.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
