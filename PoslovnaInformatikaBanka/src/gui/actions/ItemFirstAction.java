package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ItemFirstAction extends AbstractAction {

	GenericDialog dialog;

	public ItemFirstAction(GenericDialog dialog){
		
		putValue(LARGE_ICON_KEY, new ImageIcon("images/first.gif"));
		putValue(NAME,"First Item");
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
