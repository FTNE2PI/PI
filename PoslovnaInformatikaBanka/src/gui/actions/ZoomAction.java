package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ZoomAction extends AbstractAction {
	
	GenericDialog dialog;
	
	public ZoomAction(GenericDialog dialog) {
		putValue(SMALL_ICON, new ImageIcon("images/zoom-pickup.gif"));
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
