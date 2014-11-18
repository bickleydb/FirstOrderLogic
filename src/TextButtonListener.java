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

	/**
	 * @param gui
	 */
	public TextButtonListener(ProgramGui gui) {
		super();
		this.gui = gui;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		JButton pressed = (JButton) arg0.getSource();
		if (pressed.getName().equals("enter")) {
			JTextArea compOutput = gui.out;
			compOutput.setCaretPosition(0);
			JTextField in = gui.texts;
			// System.out.println(gui.grader.toString());
			String toAdd = in.getText();
			compOutput.setText("");
			compOutput.setCaretPosition(0);
			if (toAdd.indexOf("Enter Your Statement Here") != -1)
				return;

			gui.grader.evaluateStatement(toAdd);
			File feedbackFolder = new File("Feedback");
			if (!feedbackFolder.exists())
				feedbackFolder.mkdir();

			String[] files = feedbackFolder.list();
			File toWrite = new File("Feedback/" + gui.fileName);
			Scanner input = null;
			PrintWriter printer = null;
			try {
				if (!toWrite.exists()) {
					printer = new PrintWriter(toWrite);
					printer.println("Your statement: " + toAdd);
					printer.println("     Grader response: False!");
				} else {
					input = new Scanner(toWrite);
					String completeData = "";
					input.nextLine();
					while (input.hasNext()) {

						completeData = completeData + input.nextLine() + "\n";
					}
					printer = new PrintWriter(toWrite);
					printer.println("Grader Feedback");
					printer.println(completeData);

					printer.println("Your statement: " + toAdd);
					printer.println("     Grader response: False!");

				}

			} catch (FileNotFoundException e) {

			}

			// printer.close();
			// input.close();

			String feedback = "\n     Nice Try!\r\n";
			compOutput.setText(compOutput.getText() + "\n" + in.getText()
					+ feedback);
			in.setText("Enter Your Statement Here\t");

			return;
		}

		JTextField userInput = gui.texts;
		String currentInput = userInput.getText();
		if (currentInput.indexOf("Enter Your Statement Here") != -1)
			currentInput = "";
		int location = userInput.getCaretPosition();
		if (location < currentInput.length()) {
			String first = currentInput.substring(0, location);
			String second = currentInput.substring(location);
			currentInput = first + pressed.getName() + second;
		} else {
			currentInput = currentInput + pressed.getName();
			
		}
		userInput.setText(currentInput);

	}

}
