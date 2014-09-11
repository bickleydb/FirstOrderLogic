import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TextButtonListener implements ActionListener {
	ProgramGui gui;
	
	public TextButtonListener(ProgramGui gui) {
		super();
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		JButton pressed = (JButton)arg0.getSource();
		if (pressed.getName().equals("enter")){
			JTextArea compOutput = gui.out;
			JTextField in = gui.texts;
			String toAdd = in.getText();
			if(toAdd.indexOf("Enter Your Statement Here") != -1)
				return;
			
			
			String feedback = "\n     Nice Try!\r\n";
			compOutput.setText(compOutput.getText()+"\n"+in.getText()+ feedback);
			in.setText("Enter Your Statement Here\t");
			
			
			System.out.println(compOutput.getLineCount());
			return;
		}
		
		
		JTextField userInput = gui.texts;
		String currentInput = userInput.getText();
		if (currentInput.indexOf("Enter Your Statement Here") != -1)
			currentInput = "";
		currentInput = currentInput + pressed.getName();
		userInput.setText(currentInput);
		
	}

}
