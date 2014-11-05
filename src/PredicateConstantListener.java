import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;


public class PredicateConstantListener implements ActionListener {

	ProgramGui gui;
	
	public PredicateConstantListener (ProgramGui gui) {
		super();
		this.gui = gui;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		@SuppressWarnings("unchecked")
		JComboBox<String> pressed= (JComboBox<String>) arg0.getSource();
		if(pressed.equals(gui.functions)) {
			String current = (String) gui.functions.getSelectedItem();
			JTextField in = gui.texts;
			String userIn = in.getText();
			if (userIn.indexOf("Enter Your Statement Here") != -1)
				userIn = "";
			int location =  in.getCaretPosition();
			String first = userIn.substring(0,location);
			String second = userIn.substring(location);
		    userIn = first + current + second;
			//userIn = userIn + current;
			in.setText(userIn);
		}
		if (pressed.equals(gui.constants)) {
			String current = (String) gui.constants.getSelectedItem() + " ";
			JTextField in = gui.texts;
			String userIn = in.getText();
			if (userIn.indexOf("Enter Your Statement Here") != -1)
				userIn = "";
			int location =  in.getCaretPosition();
			String first = userIn.substring(0,location);
			String second = userIn.substring(location);
		    userIn = first + current + second;
			//userIn = userIn + current;
			in.setText(userIn);
			
			
		}
	}

}
