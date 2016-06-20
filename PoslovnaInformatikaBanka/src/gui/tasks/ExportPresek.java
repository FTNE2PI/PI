package gui.tasks;

import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import db.DBConnection;
import exceptions.InvalidExportException;
import gui.tasks.entities.presek.izvoda.PresekIzvoda;
import gui.tasks.entities.presek.izvoda.StavkaPreseka;
import gui.tasks.entities.presek.izvoda.ZaglavljePreseka;

public class ExportPresek {

	
	public static PresekIzvoda export(int idLica, String racun, Date datumPrometa, int brojIzvoda, 
			int brojPreseka, OutputStream output) throws SQLException, InvalidExportException, JAXBException{
		
		PresekIzvoda presek = new PresekIzvoda();
		presek.setZaglavlje(new ZaglavljePreseka());
		
		checkStatusPreseka(idLica, racun, datumPrometa, brojIzvoda, brojPreseka);
		putZaglavljeInPresek(idLica, racun, datumPrometa, brojIzvoda, brojPreseka, presek);
		putStavkeInPresek(idLica, racun, datumPrometa, brojIzvoda, brojPreseka, presek);
		
		JAXBContext jaxbContext = JAXBContext
				.newInstance(PresekIzvoda.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
		true);
		jaxbMarshaller.marshal(presek, output);

		updatePresekStatus(idLica, racun, datumPrometa, brojIzvoda, brojPreseka);
		DBConnection.getConnection().commit();
		System.out.println("Uspesno eksportovan Presek!");

		
		return presek;
		
	}

	
	private static void checkStatusPreseka(int idLica, String racun,
			Date datumPrometa, int brojIzvoda, int brojPreseka) 
					throws SQLException, InvalidExportException {
		
		String sql = "SELECT BNP_STATUS FROM PRENOS_IZVODA_PRESEK WHERE " +
				"L_ID = ? AND BAR_RACUN = ? AND DSR_DATUM = ? " +
				"AND BNP_PRESEK = ? AND DSR_IZVOD = ? ";
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
		stmt.setInt(1, idLica);
		stmt.setString(2, racun);
		stmt.setDate(3, datumPrometa);
		stmt.setInt(4, brojPreseka);
		stmt.setInt(5, brojIzvoda);
		
		ResultSet rset = stmt.executeQuery();
		if(rset.next()){
			String status = rset.getString(1);
			if(!status.equals("F")){
				throw new InvalidExportException("Izabran je presek koji nije u fazi formiranja (koji je već poslat");
			}
		}
		else
			throw new SQLException("Nije nadjen slog u tabeli PRENOS_IZVODA_PRESEK sa zadatim klju�?em");
		rset.close();
		stmt.close();
	}
	
	private static void putZaglavljeInPresek(int idLica, String racun,
			Date datumPrometa, int brojIzvoda, int brojPreseka, PresekIzvoda presek) 
					throws SQLException  {

		String sql = "SELECT BNP_PRETHODNO, BNP_BRUKORIST, BNP_U_KORIST," +
				"BNP_BRNATERET, BNP_UKTERET, BNP_NOVOSTANJE " +
				" FROM PRENOS_IZVODA_PRESEK WHERE " +
				"L_ID = ? AND BAR_RACUN = ? AND DSR_DATUM = ? " +
				"AND BNP_PRESEK = ? AND DSR_IZVOD = ? ";
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
		stmt.setInt(1, idLica);
		stmt.setString(2, racun);
		stmt.setDate(3, datumPrometa);
		stmt.setInt(4, brojPreseka);
		stmt.setInt(5, brojIzvoda);
		ResultSet rset = stmt.executeQuery();
		if(rset.next()){
			ZaglavljePreseka zaglavlje;
			if(presek.getZaglavlje() == null){
				zaglavlje = new ZaglavljePreseka();
				presek.setZaglavlje(zaglavlje);
			}
			else
				zaglavlje = presek.getZaglavlje();
			
			zaglavlje.setBrojRecuna(racun);
			GregorianCalendar calendarNaloga = new GregorianCalendar();
			calendarNaloga.setTime(datumPrometa);
			try {
				zaglavlje.setDatumNaloga(DatatypeFactory.newInstance().
						newXMLGregorianCalendar(calendarNaloga));
				zaglavlje.getDatumNaloga().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			zaglavlje.setBrojPreseka(BigInteger.valueOf(brojPreseka));
			zaglavlje.setPrethodnoStanje(rset.getBigDecimal(1));
			zaglavlje.setBrojPromenaUKorist(BigInteger.valueOf(rset.getInt(2)));
			zaglavlje.setUkupnoUKorist(rset.getBigDecimal(3));
			zaglavlje.setBrojPromenaNaTeret(BigInteger.valueOf(rset.getInt(4)));
			zaglavlje.setUkupnoNaTeret(rset.getBigDecimal(5));
			zaglavlje.setNovoStanje(rset.getBigDecimal(6));
			
		}
		else throw new SQLException("Nije nadjen traženi slog preseka");
		rset.close();
		stmt.close();
	}
	
	private static void putStavkeInPresek(int idLica, String racun,
			Date datumPrometa, int brojIzvoda, int brojPreseka,
			PresekIzvoda presek) throws SQLException{
		
		String sql = "SELECT asi.ASI_DUZNIK, asi.ASI_SVRHA, asi.ASI_POVERILAC, " +
				"asi.ASI_DATPRI, asi.ASI_RACDUZ, asi.ASI_MODZAD, asi.ASI_PBZAD, " +
				"asi.ASI_RACPOV, asi.ASI_MODODOB, asi.ASI_PBODO, asi.ASI_IZNOS, asi.ASI_TIPGRESKE " +
				"FROM ANALITIKA_PRESEKA anp " +
				"JOIN ANALITIKA_IZVODA asi  ON asi.BAR_RACUN = anp.BAR_RACUN " +
				"AND asi.DSR_DATUM = anp.DSR_DATUM AND asi.DSR_IZVOD = anp.DSR_IZVOD " +
				"AND asi.ASI_BROJSTAVKE = anp.ASI_BROJSTAVKE " +
				"WHERE " +
				"anp.L_ID = ? AND anp.BAR_RACUN = ? AND anp.DSR_DATUM = ? " +
				"AND anp.BNP_PRESEK = ? AND anp.DSR_IZVOD = ? ";
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
		stmt.setInt(1, idLica);
		stmt.setString(2, racun);
		stmt.setDate(3, datumPrometa);
		stmt.setInt(4, brojPreseka);
		stmt.setInt(5, brojIzvoda);
		ResultSet rset = stmt.executeQuery();
		while(rset.next()){
			StavkaPreseka stavka = new StavkaPreseka();
			stavka.setDuznikNalogodavac(rset.getString(1));
			stavka.setSvrhaPlacanja(rset.getString(2));
			stavka.setPrimalacPoverilac(rset.getString(3));
			GregorianCalendar calendarNaloga = new GregorianCalendar();
			calendarNaloga.setTime(rset.getDate(4));
			GregorianCalendar calendarValute = new GregorianCalendar();
			calendarValute.setTime(datumPrometa);
			try {
				stavka.setDatumNaloga(DatatypeFactory.newInstance().
						newXMLGregorianCalendar(calendarNaloga));
				stavka.getDatumNaloga().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				stavka.setDatumValute(DatatypeFactory.newInstance().
						newXMLGregorianCalendar(calendarValute));
				stavka.getDatumValute().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			stavka.setRacunDuznika(rset.getString(5));
			stavka.setModelZaduzenja(BigInteger.valueOf(rset.getLong(6)));
			stavka.setPozivNaBrojZaduzenja(rset.getString(7));
			stavka.setRacunPoverioca(rset.getString(8));
			stavka.setModelOdobrenja(BigInteger.valueOf(rset.getLong(9)));
			stavka.setPozivNaBrojOdobrenja(rset.getString(10));
			stavka.setIznos(rset.getBigDecimal(11));
			int tipGreske = rset.getInt(12);
			if(tipGreske != 1){
				stavka.setSmer("G");
			}
			else{
				if(racun.equals(stavka.getRacunDuznika()))
					stavka.setSmer("D");
				else if(racun.equals(stavka.getRacunPoverioca()))
					stavka.setSmer("P");
			}
			presek.getStavka().add(stavka);
			
		}
		
		rset.close();
		stmt.close();
		
	}
	
	private static void updatePresekStatus(int idLica, String racun,
			Date datumPrometa, int brojIzvoda, int brojPreseka) 
				throws SQLException{
		// TODO Auto-generated method stub
		String sql = "UPDATE PRENOS_IZVODA_PRESEK SET BNP_STATUS = ? " +
				"WHERE " +
				"L_ID = ? AND BAR_RACUN = ? AND DSR_DATUM = ? " +
				"AND BNP_PRESEK = ? AND DSR_IZVOD = ? ";
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
		stmt.setString(1, "P");
		stmt.setInt(2, idLica);
		stmt.setString(3, racun);
		stmt.setDate(4, datumPrometa);
		stmt.setInt(5, brojPreseka);
		stmt.setInt(6, brojIzvoda);
		int affectedRows = stmt.executeUpdate();
		if(affectedRows != 1)
			throw new SQLException("Affected rows u updatePresekStatus nije jednako 1 : " + affectedRows);
		stmt.close();
		
	}
}
