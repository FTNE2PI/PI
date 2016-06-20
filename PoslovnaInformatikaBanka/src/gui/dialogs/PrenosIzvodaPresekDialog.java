package gui.dialogs;

import gui.MainFrame;
import gui.actions.dialog.ExportPresekAction;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import components.JDigitsTextField;
import components.JIznosTextField;

import db.DBConnection;

@SuppressWarnings("serial")
public class PrenosIzvodaPresekDialog extends GenericDialog {
	
	private JDigitsTextField tfIDBrojPoslovnogLica;
	private JDigitsTextField tfBrojRacuna;
	private JDigitsTextField tfBrojPreseka;
	private JDigitsTextField tfBrojIzvoda;
	private JTextField tfDatumPrometa;
	private JIznosTextField tfPrethodnoStanje;
	private JDigitsTextField tfBrojPromenaUKorist;
	private JIznosTextField tfUkupnoUKorist;
	private JDigitsTextField tfBrojPromenaNaTeret;
	private JIznosTextField tfUkupnoNaTeret;
	private JIznosTextField tfNovoStanje;
	private JDigitsTextField tfBrojGresakaUKorist;
	private JDigitsTextField tfBrojGresakaNaTeret;
	@SuppressWarnings("rawtypes")
	private JComboBox cbStatusNaloga;
	private JButton btnExport;
	private JButton btnReport;

	public PrenosIzvodaPresekDialog(JFrame parent) {
		super(parent, "Prenos izvoda presek", "PRENOS_IZVODA_PRESEK", true);
		btnExport.setVisible(false);
		btnReport.setVisible(true);
		btnReport.setEnabled(false);
		formButtonsPanel.add(btnExport, 0);
		formButtonsPanel.add(btnReport, 1);
		btnReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Map<String, Object> params = new HashMap<String, Object>();
					  String IDBroj = tfIDBrojPoslovnogLica.getText();
					  String BrojRacuna = tfBrojRacuna.getText();
					  String BrojIzvoda = tfBrojIzvoda.getText();
					  String DatumPrometa = tfDatumPrometa.getText();
					  String BrojPreseka = tfBrojPreseka.getText();
					  params.put("IDBroj", IDBroj);
					  params.put("BrojRacuna", BrojRacuna);
					  params.put("BrojIzvoda", BrojIzvoda);
					  params.put("DatumPrometa", DatumPrometa);
					  params.put("BrojPreseka", BrojPreseka);
			          System.out.println(getClass().getResource("Izvestaji/SpisakRacuna.jasper"));
			          InputStream inputStream = new FileInputStream("Izvestaji/NalogZaPrenosSaAnalitikom.jrxml");
			          JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			         
			          JasperPrint jp = JasperFillManager.fillReport(jasperReport,
			          params, DBConnection.getConnection());
			          
			          JasperViewer jpv = new JasperViewer(jp,false);
			          //JasperViewer.viewReport(jp, false);
			          jpv.setSize(1000, 800);
			          jpv.setVisible(true);
			          jpv.toFront();
			        } catch (Exception ex) {
			          ex.printStackTrace();
			        }
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void initializeFormInputPanel() {
		tableGrid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//isReadOnly = true;
		btnExport = new JButton(new ExportPresekAction(this));
		btnReport = new JButton("Report");
		JPanel panIDBroj = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfIDBrojPoslovnogLica = new JDigitsTextField(8, 10, true, true);
		tfIDBrojPoslovnogLica.setName("L_ID");
		JButton btnZoom1 = new JButton("...");
		btnZoom1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PoslovnoLiceDialog dialog = new PoslovnoLiceDialog(MainFrame.getInstance());
				dialog.setColumnsForZoom(getColumnsForZoom());
				dialog.getToolbar().getZoomAction().setEnabled(true);
				dialog.setVisible(true);
				tfIDBrojPoslovnogLica.setText(dialog.getZoomMap().get(tfIDBrojPoslovnogLica.getName()));
			}
		});
		panIDBroj.add(tfIDBrojPoslovnogLica);
		panIDBroj.add(btnZoom1);
		addComponentToFormInputPanel(panIDBroj, "ID broj poslovnog lica", false);
		
		JPanel panBrojRacuna = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfBrojRacuna = new JDigitsTextField(14, 18, false, true);
		tfBrojRacuna.setName("BAR_RACUN");
		tfBrojIzvoda = new JDigitsTextField(3, 3, true, true);
		tfBrojIzvoda.setName("DSR_IZVOD");
		tfDatumPrometa = new JTextField(10);
		tfDatumPrometa.setName("DSR_DATUM");
		JButton btnZoom2 = new JButton("...");
		btnZoom2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DnevnoStanjeRacunaDialog dlgDnevnoStanjeRacuna = new DnevnoStanjeRacunaDialog(MainFrame.getInstance());
				dlgDnevnoStanjeRacuna.setColumnsForZoom(getColumnsForZoom());
				dlgDnevnoStanjeRacuna.getToolbar().getZoomAction().setEnabled(true);
				dlgDnevnoStanjeRacuna.setVisible(true);
				tfBrojRacuna.setText(dlgDnevnoStanjeRacuna.getZoomMap().get(tfBrojRacuna.getName()));
				tfBrojIzvoda.setText(dlgDnevnoStanjeRacuna.getZoomMap().get(tfBrojIzvoda.getName()));
				tfDatumPrometa.setText(dlgDnevnoStanjeRacuna.getZoomMap().get(tfDatumPrometa.getName()));
			}
		});
		panBrojRacuna.add(tfBrojRacuna);
		panBrojRacuna.add(btnZoom2);
		addComponentToFormInputPanel(panBrojRacuna, "Broj ra�?una", false);
		
		tfBrojPreseka = new JDigitsTextField(2, 2, true, true);
		tfBrojPreseka.setName("BNP_PRESEK");
		
		addComponentToFormInputPanel(tfBrojIzvoda, "Broj izvoda", false);
		addComponentToFormInputPanel(tfDatumPrometa, "Datum prometa", false);
		addComponentToFormInputPanel(tfBrojPreseka, "Broj preseka", false);
		
		tfPrethodnoStanje = new JIznosTextField(14, 15, 2, false);
		tfPrethodnoStanje.setText("0");
		tfPrethodnoStanje.setName("BNP_PRETHODNO");
		addComponentToFormInputPanel(tfPrethodnoStanje, "Prethodno stanje", false);
		
		
		tfBrojPromenaUKorist = new JDigitsTextField(4, 6, true, false);
		tfBrojPromenaUKorist.setText("0");
		tfBrojPromenaUKorist.setName("BNP_BRUKORIST");
		addComponentToFormInputPanel(tfBrojPromenaUKorist, "Broj promena u korist", false);
		
		tfUkupnoUKorist = new JIznosTextField(14, 15, 2, false);
		tfUkupnoUKorist.setText("0");
		tfUkupnoUKorist.setName("BNP_U_KORIST");
		addComponentToFormInputPanel(tfUkupnoUKorist, "Ukupno u korist", false);
		
		tfBrojPromenaNaTeret = new JDigitsTextField(4, 6, true, false);
		tfBrojPromenaNaTeret.setText("0");
		tfBrojPromenaNaTeret.setName("BNP_BRNATERET");
		addComponentToFormInputPanel(tfBrojPromenaNaTeret, "Broj promena na teret", false);
		
		tfUkupnoNaTeret = new JIznosTextField(14, 15, 2, false);
		tfUkupnoNaTeret.setText("0");
		tfUkupnoNaTeret.setName("BNP_UKTERET");
		addComponentToFormInputPanel(tfUkupnoNaTeret, "Ukupno na teret", false);
		
		tfNovoStanje = new JIznosTextField(14, 15, 2, false);
		tfNovoStanje.setText("0");
		tfNovoStanje.setName("BNP_NOVOSTANJE");
		addComponentToFormInputPanel(tfNovoStanje, "Novo stanje", false);
		
		tfBrojGresakaUKorist = new JDigitsTextField(4, 6, true, false);
		tfBrojGresakaUKorist.setText("0");
		tfBrojGresakaUKorist.setName("BNP_BRGRK");
		addComponentToFormInputPanel(tfBrojGresakaUKorist, "Broj grešaka u korist", false);
		
		tfBrojGresakaNaTeret = new JDigitsTextField(4, 6, true, false);
		tfBrojGresakaNaTeret.setText("0");
		tfBrojGresakaNaTeret.setName("BNP_BRGRT");
		addComponentToFormInputPanel(tfBrojGresakaNaTeret, "Broj grešaka na teret", false);
		
		
		String[] items = new String[] {"F", "P"};
		cbStatusNaloga = new JComboBox(items);
		cbStatusNaloga.setSelectedIndex(0);
		cbStatusNaloga.setName("BNP_STATUS");
		addComponentToFormInputPanel(cbStatusNaloga, "Status naloga", false);

	}
	
	@Override
	public void sync() {
		super.sync();
		if (tableGrid.getSelectedRow() != -1 && ((String)tableGrid.getValueAt(tableGrid.getSelectedRow(), 13)).equals("F"))
			btnExport.setVisible(true);
		else
			btnExport.setVisible(false);
		if(tableGrid.getSelectedRow() != -1)
			btnReport.setEnabled(true);
		else
			btnReport.setEnabled(false);
		
	}

	@Override
	protected void validateInputs() {
		
	}

}
