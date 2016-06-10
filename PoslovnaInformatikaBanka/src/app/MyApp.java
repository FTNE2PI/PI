package app;

import gui.MainFrame;
import gui.dialogs.LoginDialog;
import table.property.PropertiesContainer;

public class MyApp {

	public static void main(String[] args) {
		PropertiesContainer.getInstance();
		MainFrame mf = MainFrame.getInstance();
		mf.setVisible(true);
		LoginDialog log = new LoginDialog();
		log.setVisible(true);

	}

}
