package gui.state;

public abstract class State {
	
	public abstract void setMode();
	
	public abstract void commit();
	public abstract void rollback();
	public abstract String toString();
	
}
