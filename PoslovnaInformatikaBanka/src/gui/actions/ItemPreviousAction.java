package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ItemPreviousAction extends AbstractAction {

	GenericDialog dialog;

	public ItemPreviousAction(GenericDialog dialog){
		
		putValue(LARGE_ICON_KEY, new ImageIcon("images/prev.gif"));
		putValue(NAME,"Previous Item");
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
