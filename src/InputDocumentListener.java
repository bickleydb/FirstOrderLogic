import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class InputDocumentListener implements DocumentListener {

	ProgramGui gui;
	
	/**
	 * @param gui
	 */
	public InputDocumentListener(ProgramGui gui) {
	this.gui = gui;	
	
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		try {
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
