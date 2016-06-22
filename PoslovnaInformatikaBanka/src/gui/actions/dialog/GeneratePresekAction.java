package gui.actions.dialog;

import gui.MainFrame;
import gui.dialogs.generic.DnevnoStanjeRacunaDialog;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.PropertyResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import db.DBConnection;

public class GeneratePresekAction extends AbstractAction {

	
	private static final long serialVersionUID = 1L;
	private DnevnoStanjeRacunaDialog dialog;
	
	public GeneratePresekAction(DnevnoStanjeRacunaDialog dialog){
		this.dialog = dialog;
		putValue(NAME, "Generiši preseke");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JTable tableGrid = dialog.getTableGrid();
		int selectedRow = tableGrid.getSelectedRow();
		int maxStavki = Integer.parseInt(PropertyResourceBundle.getBundle("generatePresek").getString("maxStavki"));
		if(selectedRow != -1){
			dialog.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try{
				
				String brojRacuna = (String) tableGrid.getValueAt(selectedRow, 0);
				int brojIzvoda = ((BigDecimal)tableGrid.getValueAt(selectedRow, 1)).intValue();
				Date datumPrometa = (Date) tableGrid.getValueAt(selectedRow, 2);
				int idLica;
				String sqlIdLica = "SELECT L_ID FROM RACUN_POSLOVNIH_LICA WHERE BAR_RACUN = " + brojRacuna;
				Statement stmtIdLica = DBConnection.getConnection().createStatement();
				ResultSet rsetIdLica = stmtIdLica.executeQuery(sqlIdLica);
				if(rsetIdLica.next()){
					idLica = rsetIdLica.getInt(1);
				}
				else
					throw new SQLException("Nije nadjen racun i id poslovnog lica u tabeli RACUN_POSLOVNIH_LICA.");
				
				CallableStatement proc = DBConnection.getConnection().prepareCall(
						"{ call GENERATE_PRESEK(?,?,?,?,?,?)}");
				proc.setInt(1, idLica);
				proc.setString(2, brojRacuna);
				proc.setDate(3, datumPrometa);
				proc.setInt(4, brojIzvoda);
				proc.setInt(5, maxStavki);
				proc.registerOutParameter(6, Types.INTEGER);
				proc.executeUpdate();
				
				int odgovor = proc.getInt(6);
				DBConnection.getConnection().commit();
				String message;
				if(odgovor == 0)
					message = "Nije napravljen nijedan novi presek, najverovatnije jer ne postoji nijedna stavka koja nije već evidentirana";
				else if (odgovor%10 == 1)
					message = "Izgenerisan je " + odgovor + " presek";
				else
					message = "Izgenerisano je " + odgovor + " preseka";
				JOptionPane.showMessageDialog(dialog, message, "Zavrsena operacija", JOptionPane.INFORMATION_MESSAGE);
			
			
			}catch(SQLException e1){
				JOptionPane.showMessageDialog(MainFrame.getInstance(), 
						"Desila se neo�?ekivana greška sa bazom podataka, molimo vas da kontaktirate administratora sistema: \n" +
							e1.getMessage(), 
						"Greška sa bazom podataka", JOptionPane.ERROR_MESSAGE);
			} catch(Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(MainFrame.getInstance(), 
						"Exception has occured: \n" +
							e1.getMessage(), 
						"Exception", JOptionPane.ERROR_MESSAGE);
			}
			dialog.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
		else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), 
					"Desila se neo�?ekivana greška, vraćeno je da nije selektovan nijedan red u tabeli. \n",
					"Neo�?ekivana greška", JOptionPane.ERROR_MESSAGE);
		
	}

}
