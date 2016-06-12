package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ItemLastAction extends AbstractAction {

	GenericDialog dialog;

	public ItemLastAction(GenericDialog dialog){
		
		putValue(LARGE_ICON_KEY, new ImageIcon("images/last.gif"));
		putValue(NAME,"Last Item");
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
	
}
