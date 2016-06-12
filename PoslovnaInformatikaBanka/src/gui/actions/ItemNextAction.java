package gui.actions;

import gui.dialogs.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ItemNextAction extends AbstractAction{

	GenericDialog dialog;

	public ItemNextAction(GenericDialog dialog){
		
		putValue(LARGE_ICON_KEY, new ImageIcon("images/next.gif"));
		putValue(NAME,"Next Item");
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
