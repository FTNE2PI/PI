package gui.actions;

import exceptions.InvalidNalogZaPlacanjeException;
import gui.MainFrame;
import gui.tasks.ImportNalogZaPlacanje;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportNalogZaPlacanjeAction extends AbstractAction {

	private JFileChooser fileChooser;
	
	public ImportNalogZaPlacanjeAction() {

		putValue(NAME,"Import naloga za placanje");
		File defaultLocation = new File("./testPrimeri");
		fileChooser = new JFileChooser(defaultLocation);
		fileChooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter jksFilter = new FileNameExtensionFilter("XML file", "xml");
		fileChooser.addChoosableFileFilter(jksFilter);
		fileChooser.setFileFilter(jksFilter);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
			MainFrame.getInstance().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			File xmlFile = fileChooser.getSelectedFile();
			String fileName = xmlFile.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(!extension.equalsIgnoreCase("xml"))
				JOptionPane.showMessageDialog(MainFrame.getInstance(), "Nije izabran fajl sa .xml ekstenzijom", 
						"Nepravilna ekstenzija", JOptionPane.ERROR_MESSAGE);
			else{
				try {
					String importResponse = ImportNalogZaPlacanje.importNalog(new FileInputStream(xmlFile));
					JOptionPane.showMessageDialog(MainFrame.getInstance(), "Izveštaj operacije importa: \n" +
							importResponse,
							"Izveštaj", JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"Desila se neo�?ekivana greška sa bazom podataka, molimo vas da kontaktirate administratora sistema: \n" +
								e1.getMessage(), 
							"Greška sa bazom podataka", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidNalogZaPlacanjeException e1) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"Nalog za plaćanje sadrži grešku zbog koje ne može nastaviti dalje, molimo vas da proverite nalog: \n" +
								e1.getMessage(), 
							"Nevalidan nalog plaćanja", JOptionPane.ERROR_MESSAGE);
				} catch(Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"Exception has occured: \n" +
								e1.getMessage(), 
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
			MainFrame.getInstance().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
	}

}
