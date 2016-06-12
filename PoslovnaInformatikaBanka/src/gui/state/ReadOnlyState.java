package gui.state;

import gui.dialogs.GenericDialog;


public class ReadOnlyState extends State {

	public ReadOnlyState(GenericDialog dialog) {
		super(dialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setMode() {
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Read Only";
	}

}
