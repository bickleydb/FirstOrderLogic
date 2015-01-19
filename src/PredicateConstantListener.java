import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Class used to listen for the JComboBoxes with the Gui so the user of the
 * program can select the world they are working with, whatever functions they
 * would like to use, as well as any constants they would like to use.
 * 
 * Also listens for the user to press buttons to select mathematical symbols to
 * be inserted into their statement as well as certain variables.
 * 
 * @author Daniel Bickley
 * 
 */
public class PredicateConstantListener implements ActionListener {

	private ProgramGui gui;

	/**
	 * Constructor that initializes everything correctly
	 * 
	 * @param gui
	 *            The gui that the lister is applied to
	 */
	public PredicateConstantListener(ProgramGui gui) {
		super();
		this.gui = gui;
	}

	/*
	 * Changes the current world that the user is using. This will automatically
	 * changes the available functions and constants, as well as the world the
	 * user input will be tested against.
	 */
	private void changeWorld() {
		JComboBox<String> worlds = gui.worldSelection;
		int newSelection = worlds.getSelectedIndex();
		World changeWorld = gui.worlds.get(newSelection);

		JComboBox<String> functions = gui.functions;
		JComboBox<String> constants = gui.constants;

		functions.removeActionListener(this);
		constants.removeActionListener(this);

		functions.removeAllItems();
		constants.removeAllItems();

		String[] newFunctions = changeWorld.getFunctionNames();
		for (int i = 0; i < newFunctions.length; i++)
			functions.addItem(newFunctions[i]);

		String[] newConstants = changeWorld.getConstantNames();
		for (int i = 0; i < newConstants.length; i++)
			constants.addItem(newConstants[i]);

		functions.addActionListener(this);
		constants.addActionListener(this);

	}

	/*
	 * Upon a click of the JComboBox, the name of the function that was selected
	 * is inserted in the input box at the location at the Caret.
	 */
	private void insertFunction() {
		String current = (String) gui.functions.getSelectedItem();
		JTextField in = gui.texts;
		String userIn = in.getText();
		if (userIn.indexOf("Enter Your Statement Here") != -1)
			userIn = "";
		int location = in.getCaretPosition();
		if (location < userIn.length()) {
			String first = userIn.substring(0, location);
			String second = userIn.substring(location);
			userIn = first + current + second;
		} else {
			userIn = userIn + current;
		}
		// userIn = userIn + current;
		in.setText(userIn);
	}

	/*
	 * Upon a click of the JComboBox, the name of the constant that was selected
	 * is inserted in the input box at the location at the Caret.
	 */
	private void insertConstant() {
		String current = (String) gui.constants.getSelectedItem() + " ";
		JTextField in = gui.texts;
		String userIn = in.getText();
		if (userIn.indexOf("Enter Your Statement Here") != -1)
			userIn = "";
		int location = in.getCaretPosition();
		String first = userIn.substring(0, location);
		String second = userIn.substring(location);
		userIn = first + current + second;
		// userIn = userIn + current;
		in.setText(userIn);
	}

	@Override
	/**
	 * Determines what should be done whenever a button is clicked, and calls the 
	 * appropriate method.
	 */
	public void actionPerformed(ActionEvent arg0) {
		@SuppressWarnings("unchecked")
		JComboBox<String> pressed = (JComboBox<String>) arg0.getSource();
		if (pressed.equals(gui.worldSelection)) {
			changeWorld();
			return;
		}

		if (pressed.equals(gui.functions)) {
			insertFunction();
		}
		if (pressed.equals(gui.constants)) {
			insertConstant();

		}
	}

}
