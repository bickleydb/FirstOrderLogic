import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

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
			
			//gui.grader.grade(toAdd);
			
			File feedbackFolder = new File("Feedback");
			if(!feedbackFolder.exists())
				feedbackFolder.mkdir();
			
			String[] files = feedbackFolder.list();
			File toWrite = new File("Feedback/"+gui.fileName);
			Scanner input = null;
			PrintWriter printer = null;
			try{
			if(!toWrite.exists()) {
				printer = new PrintWriter(toWrite);
				printer.println("Your statement: " + toAdd);
				printer.println("     Grader response: False!");
			} else {
				input = new Scanner(toWrite);
				String completeData = "";
				 input.nextLine();
				while(input.hasNext()) {
				 
				  completeData = completeData+input.nextLine() + "\n";	
				}
				printer = new PrintWriter(toWrite);
				printer.println("Grader Feedback");
				printer.println(completeData);
				
				printer.println("Your statement: " + toAdd);
				printer.println("     Grader response: False!");
				
				
			}
			
			} catch (FileNotFoundException e) {
				
			}
			
			printer.close();
			//input.close();
			
			String feedback = "\n     Nice Try!\r\n";
			compOutput.setText(compOutput.getText()+"\n"+in.getText()+ feedback);
			in.setText("Enter Your Statement Here\t");
			
			
			//System.out.println(compOutput.getLineCount());
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
