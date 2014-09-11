import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ProgramGui extends JFrame{
	
	private TextButtonListener text;
	public JTextField texts;
	
	public ProgramGui() {
		text = new TextButtonListener(this);
		this.setSize(500, 500);
		this.setLayout(new BorderLayout());
		createRelationPanel();
		createCharacterPanel();
		createUserInputPanel();
		createOutputPanel();
		this.setVisible(true);
		}
		
		private void createRelationPanel() {
			JPanel pane = new JPanel();
			pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
			JLabel relations = new JLabel ("Relations");
			pane.add(relations);
			String[] stuff = {Character.toString('\u2200'),"b","c","d"};
			JComboBox<String> tester = new JComboBox<String>(stuff);
			tester.setEditable(false);
			tester.setSize(100,100);
			tester.setName("Students");
			JLabel stu = new JLabel("Students");
			pane.add(stu);
			pane.add(tester);
			JLabel teachers = new JLabel("Students");
			pane.add(teachers);
			JComboBox<String> what = new JComboBox<String>(new String[5]);
			pane.add(what);
			this.add(pane,BorderLayout.WEST);
		}
		
		private void createCharacterPanel() {
			JPanel chars = new JPanel();
			chars.setLayout(new BoxLayout(chars,BoxLayout.Y_AXIS));
			JLabel symbols = new JLabel("Symbols");
			chars.add(symbols);
			
			
			JButton forAll = new JButton(Character.toString(Constants.FOR_ALL));
			JButton thereExists = new JButton(Character.toString(Constants.THERE_EXISTS));
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
			and.setName(Character.toString(Constants.AND));
			or.setName(Character.toString(Constants.OR));
			impliles.setName(Character.toString(Constants.IMPLIES));
			iff.setName(Character.toString(Constants.IFF));
			x.setName(Character.toString(Constants.X));
			y.setName(Character.toString(Constants.Y));
			xPrime.setName(Constants.X_PRIME);
			yPrime.setName(Constants.Y_PRIME);
		
			chars.setBorder(BorderFactory.createDashedBorder(Color.RED));
			this.add(chars,BorderLayout.EAST);
		}
		
		
		
		private void createUserInputPanel() {
			JPanel input = new JPanel();
			input.setLayout(new BoxLayout(input,BoxLayout.X_AXIS));
			texts = new JTextField("Enter Your Statement Here",40);
			String test = texts.getText();
			JButton send = new JButton("ENTER");
			input.add(texts);
			input.add(send);
			//texts.set
			this.add(input,BorderLayout.SOUTH);
			
			
			
		}
		
		private void createOutputPanel() {
			JPanel output = new JPanel();
			JTextArea out = new JTextArea(5,20);
			out.setSize(1000,1000);
			out.setText("asdf");
			out.setEditable(false);
			output.add(out);
			
			this.add(output,BorderLayout.CENTER);
			
			
		
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		ProgramGui gui = new ProgramGui();
	}
}
