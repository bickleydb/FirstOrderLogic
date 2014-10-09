import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class InputDocumentListener implements DocumentListener {

	ProgramGui gui;
	
	public InputDocumentListener(ProgramGui gui) {
	this.gui = gui;	
	
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		Document doc = arg0.getDocument();
		String text = "";
		try {
			text = doc.getText(0, doc.getLength());
				gui.removeInput();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("Caught");
			//e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

}
