package gui.state;

import gui.dialogs.GenericDialog;


public class EditState extends State {

	public EditState(GenericDialog dialog) {
		super(dialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setMode() {

	}

	@Override
	public void commit() {
		
	}

	@Override
	public void rollback() {
	}

	@Override
	public String toString() {
		return "EDIT";
	}

}
