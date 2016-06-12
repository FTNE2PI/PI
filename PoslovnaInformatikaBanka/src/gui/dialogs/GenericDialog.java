package gui.dialogs;

import gui.StatusBar;
import gui.Toolbar;
import gui.state.State;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;

public class GenericDialog extends JDialog {
	private static final long serialVersionUID = 9212332865510405498L;
	private static final String ERROR_DATA_TRUNCTUATION = "Jedne ili više vrednosti koje ste uneli su prevelike.";

	protected Toolbar toolbar;
	protected JTable tableGrid = new JTable();
	protected JPanel formPanel = new JPanel();
	protected JPanel formButtonsPanel = new JPanel();
	protected JPanel formInputPanel = new JPanel();
	protected JButton btnDelete = new JButton();
	protected GenericDialog thisDialog = this;

	protected String titleName;
	protected StatusBar statusBar;
	protected State state;
	protected Vector<Component> order;
	protected Vector<Component> orderActiveTextFields;

	protected Map<String, String> zoomMap = new HashMap<String, String>();
	protected Vector<String> columnsForZoom;
	protected String tableName;
	protected boolean isReadOnly = false;
	protected int brojKolona = 0;
	protected boolean kontrolniBrojGenerisan = false;
	protected JButton btnGenerisiKontrolniBroj;
	
	public boolean isReadOnly() {
		return isReadOnly;
	}
	
	public int getBrojKolona() {
		return brojKolona;
	}
}
