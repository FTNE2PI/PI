package gui.dialogs;

import gui.StatusBar;
import gui.Toolbar;
import gui.state.State;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.eclipse.jdt.core.compiler.InvalidInputException;

public abstract class GenericDialog extends JDialog {
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
	
	protected void removeTextFieldsFromOrder() {
		if (!order.isEmpty())
			order.clear();
	}

	protected void initializeTableModel() {
		
	}

	public JPanel getFormInputPanel() {
		return formInputPanel;
	}
	
	protected abstract void initializeFormInputPanel();
	
	private boolean getRecursiveInput(String columnName,
			Vector<String> resultHolder, JPanel currentPanel) {
		boolean foundInput = false;
		for (int i = 0; i < currentPanel.getComponentCount(); i++) {
			Component c = currentPanel.getComponent(i);
			if (c instanceof JPanel) {
				foundInput = getRecursiveInput(columnName, resultHolder,
						(JPanel) c);
				if (foundInput) {
					break;
				}
			} else if (c instanceof JTextField) {
				JTextField tf = (JTextField) c;
				if (tf.getName().equals(columnName)) {
					String result = tf.getText().trim();
					if (result.equals(""))
						result = null;
					resultHolder.add(result);
					foundInput = true;
					break;
				}
			}
		}
		return foundInput;
	}
	
	/**
	 * Metoda koja vraca vrednost komponente na osnovu imena kolone.
	 * 
	 * @param columnName
	 * @return
	 */
	protected String getInput(String columnName) {
		Vector<String> resultHolder = new Vector<String>();
		getRecursiveInput(columnName, resultHolder, formInputPanel);
		if (resultHolder.isEmpty())
			return null;
		else
			return resultHolder.get(0);
	}
	
	public void fillZoomMap() {
		for (int i = 0; i < columnsForZoom.size(); i++) {
			zoomMap.put(columnsForZoom.get(i), getInput(columnsForZoom.get(i)));
		}
	}
	
	private int emptyTextFieldRecursive(JPanel currentPanel, int currentIndex) {
		for (int i = 0; i < currentPanel.getComponentCount(); i++) {
			Component c1 = currentPanel.getComponent(i);
			if (c1 instanceof JPanel) {
				currentIndex = emptyTextFieldRecursive((JPanel) c1,
						currentIndex);
			} else if (c1 instanceof JTextField) {
				currentIndex++;
				JTextField tf = (JTextField) c1;
				tf.setText("");
				if (currentIndex == 1)
					tf.requestFocus();

			} else if (c1 instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) c1;
				if (cb.getName().equals("BAR_VAZI"))
					cb.setSelected(true);
				else
					cb.setSelected(false);

			} else if (c1 instanceof JComboBox) {
				JComboBox cb = (JComboBox) c1;
				cb.setSelectedIndex(0);
			}
		}
		return currentIndex;
	}

	public void fillNext(Vector<Object> rowData, String tName) {
		
	}
	
	/**
	 * Metoda koja prazni komponente u dijalogu.
	 */
	public void emptyTextField() {
		int index = 0;
		index = emptyTextFieldRecursive(formInputPanel, index);

	}

	/**
	 * Metoda koja vraca aktivne textFieldove u dijalogu
	 */
	public void getActiveTextFields() {
		for (Component comp : order) {
			if (comp instanceof JTextField)
				if (((JTextField) comp).isEditable())
					orderActiveTextFields.add(comp);

		}

	}
	
	private int setFieldValuesRecursive(Vector<Object> values,
			JPanel currentPanel, int currentIndex) {
		for (int i = 0; i < currentPanel.getComponentCount(); i++) {
			Component c = currentPanel.getComponent(i);
			if (c instanceof JPanel)
				currentIndex = setFieldValuesRecursive(values, (JPanel) c,
						currentIndex);
			else if (c instanceof JTextField) {
				JTextField tf = (JTextField) c;
				tf.setText(values.get(currentIndex).toString());
				currentIndex++;
			} else if (c instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) c;
				if (values.get(currentIndex).toString().equals("true"))
					cb.setSelected(true);
				else
					cb.setSelected(false);
				currentIndex++;
			}
		}

		return currentIndex;
	}
	
	private void setFieldValues(Vector<Object> values) {
		int index = 0;
		index = setFieldValuesRecursive(values, formInputPanel, index);
	}
	
	public void sync() {
		int index = tableGrid.getSelectedRow();
		if (index < 0) {
			emptyTextField();
			return;
		}
		Vector<Object> values = new Vector<Object>();
		for (int i = 0; i < tableGrid.getModel().getColumnCount(); i++) {
			values.add(tableGrid.getModel().getValueAt(index, i));
		}

		setFieldValues(values);
		if (tableName.equals("RACUN_POSLOVNIH_LICA")) {
			if ((boolean) tableGrid.getValueAt(tableGrid.getSelectedRow(), 5) == true)
				btnDelete.setVisible(true);
			else
				btnDelete.setVisible(false);
		}
	}
	
	/**
	 * Metoda za pretragu reda u tabeli.
	 */
	public void searchRow() {
	}

	/**
	 * Metoda za dodavanje novog reda u tabelu.
	 */
	public void addRow() {

	}

	/**
	 * Metoda za brisanje selektovanog reda u tabeli.
	 */
	public void deleteSelectedRow() {

	}

	/**
	 * Metoda za izmenu selektovanog reda.
	 */
	public void updateRow() {

	}
	
	public Toolbar getToolbar() {
		return toolbar;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public JTable getTableGrid() {
		return tableGrid;
	}

	public void clearTextFields() {

	}

	public void setState(State state) {
		this.state = state;
	}

	public State getCurrentState() {
		return state;
	}

	public Map<String, String> getZoomMap() {
		return zoomMap;
	}

	public void setColumnsForZoom(Vector<String> columnsForZoom) {
		this.columnsForZoom = columnsForZoom;
	}

	/**
	 * Metoda koja vraca vektor sa nazivima kolona za zoom mehanizam.
	 * 
	 * @return
	 */
	public Vector<String> getColumnsForZoom() {
		Vector<String> retVal = new Vector<String>();
		
		return retVal;
	}

	/**
	 * Metoda koja vraca vektor naziva labela primarnih kljuceva.
	 * 
	 * @return
	 */
	public Vector<String> getPrimaryKeysNames() {
		return null;
	}

	public String getTableName() {
		return tableName;
	}
	
	private int getTableRowIndex(Vector<Object> rowData) {
		
		return 0;
	}

	protected abstract void validateInputs() throws InvalidInputException;
	
	public boolean isAllLetters(String s) {
		return s.matches("[a-zA-Z]+");
	}

	public boolean isAllNumbersOrBigLetters(String s) {
		return s.matches("[A-Z0-9]+");
	}

	public boolean isKontrolniBrojGenerisan() {
		return kontrolniBrojGenerisan;
	}

	public void setKontrolniBrojGenerisan(boolean kontrolniBrojGenerisan) {
		this.kontrolniBrojGenerisan = kontrolniBrojGenerisan;
	}

	public JButton getBtnGenerisiKontrolniBroj() {
		return btnGenerisiKontrolniBroj;
	}

	
}
