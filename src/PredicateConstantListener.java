import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class PredicateConstantListener implements ActionListener {

	ProgramGui gui;

	public PredicateConstantListener(ProgramGui gui) {
		super();
		this.gui = gui;
	}

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
		for(int i = 0; i < newFunctions.length; i++)
			functions.addItem(newFunctions[i]);
		
		String[] newConstants = changeWorld.getConstantNames();
		for(int i = 0; i < newConstants.length; i++)
			constants.addItem(newConstants[i]);
		
		functions.addActionListener(this);
		constants.addActionListener(this);

	}

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
