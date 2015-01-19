import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Daniel Bickley
 *
 */
public class TextButtonListener implements ActionListener {
	private ProgramGui gui;
	File feedbackFolder;

	/**
	 * @param gui
	 */
	public TextButtonListener(ProgramGui gui) {
		super();
		this.gui = gui;

		this.feedbackFolder = new File(gui.fileName);
		if (!feedbackFolder.exists())
			feedbackFolder.mkdir();
	}

	/**
	 * 
	 * @param toAdd
	 */
	private void writeToFile(String toAdd) {
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

	}

	/**
	 * 
	 */
	private void pressEnter() {
		JTextArea compOutput = gui.out;
		compOutput.setCaretPosition(0);
		JTextField in = gui.texts;
		String toAdd = in.getText();
		compOutput.setText("");
		compOutput.setCaretPosition(0);
		if (toAdd.indexOf("Enter Your Statement Here") != -1)
			return;

		StatementTree userInput = new StatementTree();
		
		String feedback = "\n     Nice Try!\r\n";
		compOutput.setText(compOutput.getText() + "\n" + in.getText()
				+ feedback);
		in.setText("Enter Your Statement Here\t");

		return;

	}

	/**
	 * 
	 * @param pressed
	 */
	private void insertButtonName(JButton pressed) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		JButton pressed = (JButton) arg0.getSource();
		if (pressed.getName().equals("enter")) {
			pressEnter();
			return;
		}
		insertButtonName(pressed);

	}

}
