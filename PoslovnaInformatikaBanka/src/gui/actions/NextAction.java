package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class NextAction extends AbstractAction {

	GenericDialog dialog;

	public NextAction(GenericDialog dialog) {
		putValue(SMALL_ICON, new ImageIcon("images/nextform.gif"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
