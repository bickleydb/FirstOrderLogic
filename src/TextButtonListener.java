import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;


public class TextButtonListener implements ActionListener {
	ProgramGui gui;
	
	public TextButtonListener(ProgramGui gui) {
		super();
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		JButton pressed = (JButton)arg0.getSource();
		JTextField userInput = gui.texts;
		String currentInput = userInput.getText();
		if (currentInput.indexOf("Enter Your Statement Here") != -1)
			currentInput = "";
		currentInput = currentInput + pressed.getName();
		userInput.setText(currentInput);
		
	}

}