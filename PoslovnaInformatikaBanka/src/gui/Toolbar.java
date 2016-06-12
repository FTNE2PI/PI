package gui;

import gui.actions.AddModeAction;
import gui.actions.EditAction;
import gui.actions.HelpAction;
import gui.actions.ItemDeleteAction;
import gui.actions.ItemFirstAction;
import gui.actions.ItemLastAction;
import gui.actions.ItemNextAction;
import gui.actions.ItemPreviousAction;
import gui.actions.NextAction;
import gui.actions.RefreshAction;
import gui.actions.SearchAction;
import gui.actions.ZoomAction;
import gui.dialogs.GenericDialog;

import javax.swing.Action;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar {
	
	private GenericDialog dialog;
	private Action btnAdd;
	private Action btnDelete;
	private Action btnEdit;
	private Action btnZoomPickUp;
	private Action btnNextForm;
	
	public Toolbar(GenericDialog dialog) {
		super();
		setFloatable(false);
		
		this.dialog = dialog;
		
		add(new SearchAction(dialog));
		add(new RefreshAction(dialog));
		
		btnZoomPickUp = new ZoomAction(dialog);
		btnZoomPickUp.setEnabled(false);
		add(btnZoomPickUp);
		
		add(new HelpAction(dialog));
		addSeparator();
		
		add(new ItemFirstAction(dialog));
		add(new ItemPreviousAction(dialog));
		add(new ItemNextAction(dialog));
		add(new ItemLastAction(dialog));
		addSeparator();
		
		
		btnAdd = new AddModeAction(dialog);
		add(btnAdd);
		
		btnEdit = new EditAction(dialog);
		add(btnEdit);
		
		btnDelete = new ItemDeleteAction(dialog);
		add(btnDelete);
		addSeparator();
		
		addSeparator();
		
		btnNextForm = new NextAction(dialog);
		add(btnNextForm);
		
		if(dialog.isReadOnly()) {
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnEdit.setEnabled(false);
		}
	}
	
	public Action getZoomAction() {
		return btnZoomPickUp;
	}
	
	public Action getAddAction() {
		return btnAdd;
	}
	
	public Action getEditAction() {
		return btnEdit;
	}
	
	public Action getDeleteAction() {
		return btnDelete;
	}
	
	public Action getNextAction() {
		return btnNextForm;
	}
}
