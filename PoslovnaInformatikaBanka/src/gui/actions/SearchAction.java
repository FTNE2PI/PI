package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SearchAction extends AbstractAction {
	
	GenericDialog dialog;
	
	public SearchAction(GenericDialog dialog) {
		putValue(SMALL_ICON, new ImageIcon("images/search.gif"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
