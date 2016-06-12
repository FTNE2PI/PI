package gui.dialogs;

import javax.swing.JDialog;

public class GenericDialog extends JDialog {
	protected boolean isReadOnly = false;
	
	public boolean isReadOnly() {
		return isReadOnly;
	}
}
