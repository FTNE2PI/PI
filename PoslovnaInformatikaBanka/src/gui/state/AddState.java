package gui.state;

import gui.dialogs.GenericDialog;


public class AddState extends State {
	
	public AddState(GenericDialog dialog) {
		super(dialog);
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
		return "ADD";
	}

}
