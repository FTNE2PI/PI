package gui.dialogs;

import gui.MainFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AnalitikaPresekaDialog extends GenericDialog {
	
	private JTextField tfIDBrojPoslovnogLica;
	private JTextField tfBrojRacuna;
	private JTextField tfDatumNaloga;
	private JTextField tfBrojPreseka;
	private JTextField tfBrojIzvoda;
	private JTextField tfBrojStavke;
	
	private PrenosIzvodaPresekDialog dlgPrenosIzvodaPresek;
	private AnalitikaIzvodaDialog dlgAnalitikaIzvoda;
	
	public AnalitikaPresekaDialog(JFrame parent) {
		
		super(parent, "Analitika preseka", "ANALITIKA_PRESEKA", true);
	}

	@Override
	protected void initializeFormInputPanel() {
		
		JPanel panPrenos = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfIDBrojPoslovnogLica = new JTextField(10);
		tfIDBrojPoslovnogLica.setName("L_ID");
		tfDatumNaloga = new JTextField(10);
		tfDatumNaloga.setName("DSR_DATUM");
		tfBrojPreseka = new JTextField(2);
		tfBrojPreseka.setName("BNP_PRESEK");
		JButton btnZoom1 = new JButton("...");
		btnZoom1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dlgPrenosIzvodaPresek = new PrenosIzvodaPresekDialog(MainFrame.getInstance());
				dlgPrenosIzvodaPresek.setColumnsForZoom(getColumnsForZoom());
				dlgPrenosIzvodaPresek.getToolbar().getZoomAction().setEnabled(true);
				dlgPrenosIzvodaPresek.setVisible(true);
				tfIDBrojPoslovnogLica.setText(dlgPrenosIzvodaPresek.getZoomMap().get(tfIDBrojPoslovnogLica.getName()));
				tfBrojPreseka.setText(dlgPrenosIzvodaPresek.getZoomMap().get(tfBrojPreseka.getName()));
			}
		});
		
		panPrenos.add(tfIDBrojPoslovnogLica);
		panPrenos.add(btnZoom1);
		addComponentToFormInputPanel(panPrenos, "ID Broj poslovnog lica", true);
		
		JPanel panPrenos2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfBrojRacuna = new JTextField(18);
		tfBrojRacuna.setName("BAR_RACUN");
	
		tfBrojIzvoda = new JTextField(3);
		tfBrojIzvoda.setName("DSR_IZVOD");
		
		
		tfBrojStavke = new JTextField(8);
		tfBrojStavke.setName("ASI_BROJSTAVKE");
		
		JButton btnZoom2 = new JButton("...");
		btnZoom2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dlgAnalitikaIzvoda = new AnalitikaIzvodaDialog(MainFrame.getInstance());
				dlgAnalitikaIzvoda.setColumnsForZoom(getColumnsForZoom());
				dlgAnalitikaIzvoda.getToolbar().getZoomAction().setEnabled(true);
				dlgAnalitikaIzvoda.setVisible(true);
				tfBrojRacuna.setText(dlgAnalitikaIzvoda.getZoomMap().get(tfBrojRacuna.getName()));
				tfBrojIzvoda.setText(dlgAnalitikaIzvoda.getZoomMap().get(tfBrojIzvoda.getName()));
				tfDatumNaloga.setText(dlgAnalitikaIzvoda.getZoomMap().get(tfDatumNaloga.getName()));
				tfBrojStavke.setText(dlgAnalitikaIzvoda.getZoomMap().get(tfBrojStavke.getName()));
			}
		});
		
		panPrenos2.add(tfBrojRacuna);
		panPrenos2.add(btnZoom2);
		addComponentToFormInputPanel(panPrenos2, "Broj raÄ?una", true);
		addComponentToFormInputPanel(tfDatumNaloga, "Datum naloga", true);
		addComponentToFormInputPanel(tfBrojIzvoda, "Broj izvoda", true);
		addComponentToFormInputPanel(tfBrojPreseka, "Broj preseka", true);
		addComponentToFormInputPanel(tfBrojStavke, "Broj stavke", true);

	}

	@Override
	protected void validateInputs() {
		
	}

}
