package gui.actions.dialog;

import exceptions.InvalidExportException;
import gui.MainFrame;
import gui.dialogs.generic.PrenosIzvodaPresekDialog;
import gui.tasks.ExportPresek;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportPresekAction extends AbstractAction {

	private PrenosIzvodaPresekDialog dialog;
	private JFileChooser fileChooser;
	
	public ExportPresekAction(PrenosIzvodaPresekDialog dialog){
		this.dialog = dialog;
		putValue(NAME, "Export");
		File defaultLocation = new File("./testExportResults");
		fileChooser = new JFileChooser(defaultLocation);
		fileChooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter jksFilter = new FileNameExtensionFilter(
				"XML file", "xml");
		fileChooser.addChoosableFileFilter(jksFilter);
		fileChooser.setFileFilter(jksFilter);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (fileChooser.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
			dialog.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			File selectedFile = fileChooser.getSelectedFile();
			if (!selectedFile.getPath().toLowerCase().endsWith(".xml")) {
				selectedFile = new File(selectedFile.getPath() + ".xml");
			}
			JTable tableGrid = dialog.getTableGrid();
			int selectedRow = tableGrid.getSelectedRow();
			int idLica = ((BigDecimal)tableGrid.getValueAt(selectedRow, 0)).intValue();
			String brojRacuna = (String) tableGrid.getValueAt(selectedRow, 1);
			int brojIzvoda = ((BigDecimal)tableGrid.getValueAt(selectedRow, 2)).intValue();
			Date datumPrometa = (Date) tableGrid.getValueAt(selectedRow, 3);
			int brojPreseka = ((BigDecimal)tableGrid.getValueAt(selectedRow, 4)).intValue();

			try {
				String successMessage = null;
				ExportPresek.export(idLica, brojRacuna, datumPrometa, brojIzvoda, brojPreseka, 
						new FileOutputStream(selectedFile));
				successMessage = "Uspešno eksportovanje preseka.";
				if(successMessage != null){
					JOptionPane.showMessageDialog(dialog, successMessage, 
							"Uspeh", JOptionPane.INFORMATION_MESSAGE);
					dialog.getGenTableModel().open();
					tableGrid.setRowSelectionInterval(selectedRow, selectedRow);
				}
					
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(), 
						"Desila se neo�?ekivana greška sa bazom podataka, molimo vas da kontaktirate administratora sistema: \n" +
							e1.getMessage(), 
						"Greška sa bazom podataka", JOptionPane.ERROR_MESSAGE);
			} catch (InvalidExportException e1) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(), 
						"Dogodila se greška prilikom eksportovanja Presekae: \n" +
							e1.getMessage(), 
						"Greška", JOptionPane.ERROR_MESSAGE);
			} catch(Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(MainFrame.getInstance(), 
						"Exception has occured: \n" +
							e1.getMessage(), 
						"Exception", JOptionPane.ERROR_MESSAGE);
			}
			dialog.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
