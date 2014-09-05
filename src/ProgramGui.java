import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ProgramGui extends JFrame{
	
	
	public ProgramGui() {
	
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
			tester.setSize(100,100);
			tester.setName("Students");
			pane.add(tester);
			this.add(pane,BorderLayout.WEST);
		}
		
		private void createCharacterPanel() {
			JPanel chars = new JPanel();
			chars.setLayout(new BoxLayout(chars,BoxLayout.Y_AXIS));
			JLabel symbols = new JLabel("Symbols");
			chars.add(symbols);
			JButton forAll = new JButton(Character.toString('\u2200'));
			chars.add(forAll);
			JButton thereExists = new JButton(Character.toString('\u2203'));
			chars.add(thereExists);
			JButton and = new JButton(Character.toString('\u2227'));
			chars.add(and);
			JButton or = new JButton(Character.toString('\u2228'));
			chars.add(or);
			this.add(chars,BorderLayout.EAST);
		}
		
		private void createUserInputPanel() {
			JPanel input = new JPanel();
			input.setLayout(new BoxLayout(input,BoxLayout.X_AXIS));
			JTextField texts = new JTextField("WHEEOOO",40);
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
