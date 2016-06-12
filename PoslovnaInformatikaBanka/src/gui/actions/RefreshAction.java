package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class RefreshAction extends AbstractAction {
	
	GenericDialog dialog;
	
	public RefreshAction(GenericDialog dialog) {
		putValue(SMALL_ICON, new ImageIcon("images/refresh.gif"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
