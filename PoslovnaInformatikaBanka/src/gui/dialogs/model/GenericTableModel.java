package gui.dialogs.model;

import javax.swing.table.DefaultTableModel;

public class GenericTableModel extends DefaultTableModel{
	
	private String tableName;

	private static final int CUSTOM_ERROR_CODE = 50000;
	private static final String ERROR_RECORD_WAS_CHANGED = "Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost";
	private static final String ERROR_RECORD_WAS_DELETED = "Slog je obrisan od strane drugog korisnika";

}
