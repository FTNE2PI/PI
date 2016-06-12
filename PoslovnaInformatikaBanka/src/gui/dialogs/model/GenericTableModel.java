package gui.dialogs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import table.property.ColumnProperties;
import table.property.LookUpElementProperties;
import table.property.TableProperties;
import table.property.ZoomElementProperties;
import table.property.ZoomProperties;
import utils.SortUtils;
import db.DBConnection;

public class GenericTableModel extends DefaultTableModel{
	
	private String tableName;

	private static final int CUSTOM_ERROR_CODE = 50000;
	private static final String ERROR_RECORD_WAS_CHANGED = "Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost";
	private static final String ERROR_RECORD_WAS_DELETED = "Slog je obrisan od strane drugog korisnika";
	
	private TableProperties tableProperties;

	public GenericTableModel(TableProperties tableProperties) {
		super(tableProperties.getColumnsLabel(), 0);
		this.tableName = tableProperties.getTableName();
		this.tableProperties = tableProperties;

	}
	
	public void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);

		while (rset.next()) {
			Vector<Object> rowData = new Vector<Object>();

			for (String name : tableProperties.getColumnsNameForForm()) {
				ColumnProperties cp = tableProperties.getColumnProperties(name);
				if (cp != null) {
					if (cp.getType().equals("java.sql.Date")) {
						rowData.add(rset.getDate(name));
					} else
						rowData.add(rset.getObject(name));
				} else
					rowData.add(rset.getObject(name));
			}

			addRow(rowData);

		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void open() throws SQLException {
		fillData(getSQLForOpenWithOrderBy());
	}
	
	private String getSQLForOpenWithOrderBy() {
		String retVal = "";
		// provera da li radimo spajanje tabela ili ne
		if (tableProperties.getZoom().isEmpty()) {
			retVal = "SELECT DISTINCT ";
			int i = 0;
			for (ColumnProperties cp : tableProperties.getColumns()) {
				i++;
				if (i != tableProperties.getColumns().size()) {
					retVal = retVal + cp.getName() + ", ";
				} else if (i == tableProperties.getColumns().size()) {
					retVal = retVal + cp.getName();
				}
			}
			retVal = retVal + " FROM " + tableName + " ORDER BY "
					+ tableProperties.getColumns().get(0).getName();
		} else if (!tableProperties.getZoom().isEmpty()) {
			retVal = "SELECT DISTINCT ";
			Vector<String> lookUpColumns = new Vector<String>();
			Vector<String> zoomTables = new Vector<String>();
			for (ZoomProperties zp : tableProperties.getZoom()) {
				zoomTables.add(zp.getTable());
				for (LookUpElementProperties lookUp : zp.getLookUpElements()) {
					lookUpColumns.add(lookUp.getName());
				}

			}

			for (int i = 0; i < tableProperties.getColumns().size(); i++) {
				if (i != tableProperties.getColumns().size() - 1) {
					retVal = retVal + tableName + "."
							+ tableProperties.getColumns().get(i).getName()
							+ ", ";
				} else if (i == tableProperties.getColumns().size() - 1) {
					retVal = retVal + tableName + "."
							+ tableProperties.getColumns().get(i).getName();
				}
			}
			int i = 0;
			for (String t : zoomTables) {
				for (String c : lookUpColumns) {
					i++;
					if (i != lookUpColumns.size() && lookUpColumns.size() > 0) {
						retVal = retVal + ", " + t + "." + c + ", ";
					} else if (i == lookUpColumns.size()) {
						retVal = retVal + ", " + t + "." + c;
					}
				}

			}
			retVal = retVal + " FROM " + tableName;
			for (String t : zoomTables) {
				int j = 0;
				ZoomProperties zp = tableProperties.getZoomItemByTableName(t);
				String tName = "";
				if (tableProperties.checkZoomElements(zp) > zp
						.getZoomElements().size()) {
					int index = 0;
					tName = t;
					for (ZoomElementProperties zep : zp.getZoomElements()) {
						index++;
						String tableNameWithPrefix = t + index;
						retVal = retVal + " JOIN " + t + " "
								+ tableNameWithPrefix + " ON " + tableName
								+ "." + zep.getTo() + " = "
								+ tableNameWithPrefix + "." + zep.getFrom();
					}
				} else if (!t.equals(tName)) {
					retVal = retVal + " JOIN " + t + " ON ";
					for (ZoomElementProperties zep : zp.getZoomElements()) {
						j++;
						if (j != zp.getZoomElements().size()) {
							retVal = retVal + tableName + "." + zep.getTo()
									+ " = " + t + "." + zep.getFrom() + " AND ";
						} else if (j == zp.getZoomElements().size()) {
							retVal = retVal + tableName + "." + zep.getTo()
									+ " = " + t + "." + zep.getFrom();
						}
					}
				}

			}

			retVal = retVal + " ORDER BY "
					+ tableProperties.getColumns().get(0).getName();

		}
		System.out.println(retVal);
		return retVal;
	}
	
	private String getSQLForOpen() {
		String retVal = "";
		// provera da li radimo spajanje tabela ili ne
		if (tableProperties.getZoom().isEmpty()) {
			retVal = "SELECT DISTINCT ";
			int i = 0;
			for (ColumnProperties cp : tableProperties.getColumns()) {
				i++;
				if (i != tableProperties.getColumns().size()) {
					retVal = retVal + cp.getName() + ", ";
				} else if (i == tableProperties.getColumns().size()) {
					retVal = retVal + cp.getName();
				}
			}
			retVal = retVal + " FROM " + tableName;
		} else if (!tableProperties.getZoom().isEmpty()) {
			retVal = "SELECT DISTINCT ";
			Vector<String> lookUpColumns = new Vector<String>();
			Vector<String> zoomTables = new Vector<String>();
			for (ZoomProperties zp : tableProperties.getZoom()) {
				zoomTables.add(zp.getTable());
				for (LookUpElementProperties lookUp : zp.getLookUpElements()) {
					lookUpColumns.add(lookUp.getName());
				}

			}

			for (int i = 0; i < tableProperties.getColumns().size(); i++) {
				if (i != tableProperties.getColumns().size() - 1) {
					retVal = retVal + tableName + "."
							+ tableProperties.getColumns().get(i).getName()
							+ ", ";
				} else if (i == tableProperties.getColumns().size() - 1) {
					retVal = retVal + tableName + "."
							+ tableProperties.getColumns().get(i).getName();
				}
			}
			int i = 0;
			for (String t : zoomTables) {
				for (String c : lookUpColumns) {
					i++;
					if (i != lookUpColumns.size() && lookUpColumns.size() > 0) {
						retVal = retVal + ", " + t + "." + c + ", ";
					} else if (i == lookUpColumns.size()) {
						retVal = retVal + ", " + t + "." + c;
					}
				}

			}
			retVal = retVal + " FROM " + tableName;

			for (String t : zoomTables) {
				int j = 0;
				ZoomProperties zp = tableProperties.getZoomItemByTableName(t);
				String tName = "";
				if (tableProperties.checkZoomElements(zp) > zp
						.getZoomElements().size()) {
					int index = 0;
					tName = t;
					for (ZoomElementProperties zep : zp.getZoomElements()) {
						index++;
						String tableNameWithPrefix = t + index;
						retVal = retVal + " JOIN " + t + " "
								+ tableNameWithPrefix + " ON " + tableName
								+ "." + zep.getTo() + " = "
								+ tableNameWithPrefix + "." + zep.getFrom();
					}
				} else if (!t.equals(tName)) {
					retVal = retVal + " JOIN " + t + " ON ";
					for (ZoomElementProperties zep : zp.getZoomElements()) {
						j++;
						if (j != zp.getZoomElements().size()) {
							retVal = retVal + tableName + "." + zep.getTo()
									+ " = " + t + "." + zep.getFrom() + " AND ";
						} else if (j == zp.getZoomElements().size()) {
							retVal = retVal + tableName + "." + zep.getTo()
									+ " = " + t + "." + zep.getFrom();
						}
					}
				}
			}

		}
		System.out.println(retVal);
		return retVal;
	}
	
	private String getSQLForCheckRow() {
		String retVal = "";
		if (tableProperties.getZoom().isEmpty()) {
			retVal = "SELECT * FROM " + tableName + " WHERE ";
			int i = 0;
			int size = tableProperties.getPrimaryKeysFromTable().size();
			for (String pKey : tableProperties.getPrimaryKeysFromTable()) {
				i++;
				if (i != size) {
					retVal = retVal + pKey + " = ? AND ";
				} else if (i == size) {
					retVal = retVal + pKey + " = ?";
				}
			}
		} else if (!tableProperties.getZoom().isEmpty()) {
			retVal = getSQLForOpen();
			for (int i = 0; i < tableProperties.getPrimaryKeysFromTable()
					.size(); i++) {
				String columnName = tableProperties.getPrimaryKeysFromTable()
						.get(i);
				retVal = retVal + " AND " + tableName + "." + columnName
						+ " = ?";
			}
		}
		System.out.println(retVal);
		return retVal;
	}
	
	private void checkRow(int index, Vector<Object> primKeys)
			throws SQLException {

		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement selectStmt = DBConnection.getConnection()
				.prepareStatement(getSQLForCheckRow());

		for (int i = 0; i < tableProperties.getPrimaryKeysFromTable().size(); i++) {
			selectStmt.setObject(i + 1, primKeys.get(i));
		}

		ResultSet rset = selectStmt.executeQuery();

		Vector<Object> values = new Vector<Object>();
		Boolean postoji = false;
		String errorMsg = "";
		while (rset.next()) {
			for (String name : tableProperties.getColumnsNameForForm()) {
				ColumnProperties cp = tableProperties.getColumnProperties(name);
				if (cp != null) {
					if (cp.getType().equals("java.sql.Date")) {
						values.add(rset.getDate(cp.getName()));
					} else
						values.add(rset.getObject(name));
				} else
					values.add(rset.getObject(name));
			}
			postoji = true;
		}
		boolean flag = false;
		for (int i = 0; i < values.size(); i++) {
			if (SortUtils.getLatCyrCollator().compare(
					values.get(i).toString().trim(),
					getValueAt(index, i).toString().trim()) != 0) {
				flag = true;
				break;
			}
		}
		if (!postoji) {
			removeRow(index);
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_DELETED;
		} else if (flag) {

			for (int i = 0; i < values.size(); i++) {
				setValueAt(values.get(i), index, i);
			}
			fireTableDataChanged();
			errorMsg = ERROR_RECORD_WAS_CHANGED;
		}
		rset.close();
		selectStmt.close();
		DBConnection.getConnection().setTransactionIsolation(
				Connection.TRANSACTION_READ_COMMITTED);
		if (errorMsg != "") {
			DBConnection.getConnection().commit();
			throw new SQLException(errorMsg, "", CUSTOM_ERROR_CODE);
		}
	}
	
}