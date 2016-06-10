package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	
	private JMenu mnuBanka = new JMenu("File");
	private JMenu mnuOperacije = new JMenu("Operacije");
	private JMenu mnuKorisnik=new JMenu("Anonimus");
	private JMenu mnuIzvestaji = new JMenu("Izveštaji");
	private JMenuItem itemSpisakRacuna = new JMenuItem("Spisak računa zadate banke");
	private JMenuItem itemDrzave = new JMenuItem("Države");
	private JMenuItem itemValuta = new JMenuItem("Valuta");
	private JMenuItem itemBanka = new JMenuItem("Banka");
	private JMenuItem itemKursnaLista = new JMenuItem("Kursna lista");
	private JMenuItem itemKursUValuti = new JMenuItem("Kurs u valuti");
	private JMenuItem itemPoslovnoLice = new JMenuItem("Poslovno lice");
	private JMenuItem itemRacunPoslovnihLica = new JMenuItem("Račun poslovnih lica"); 
	private JMenuItem itemUkidanje = new JMenuItem("Ukidanje");
	private JMenuItem itemPrenosIzvodaPresek = new JMenuItem("Prenos izvoda presek");
	private JMenuItem itemDnevnoStanjeRacuna=new JMenuItem("Dnevno stanje računa");
	private JMenuItem itemAnalitikaPreseka=new JMenuItem("Analitika preseka");
	private JMenuItem itemAnalitikaIzvoda=new JMenuItem("Analitika izvoda");
	private JMenuItem itemMedjubankarskiNalog = new JMenuItem("Međubankarski nalog");
	private JMenuItem itemVezaMedjubankarskogNalogaIStavke = new JMenuItem("Veza međubankarskog naloga i stavki");
	private JMenuItem itemOdjaviSe=new JMenuItem("Odjavi se");
	
	public Menu() {
		super();
		
		mnuBanka.add(itemDrzave);
		mnuBanka.add(itemValuta);
		mnuBanka.add(itemBanka);
		mnuBanka.add(itemKursnaLista);
		mnuBanka.add(itemKursUValuti);
		mnuBanka.add(itemPoslovnoLice);
		mnuBanka.add(itemRacunPoslovnihLica);
		mnuBanka.add(itemUkidanje);
		mnuBanka.add(itemPrenosIzvodaPresek);
		mnuBanka.add(itemDnevnoStanjeRacuna);
		mnuBanka.add(itemAnalitikaPreseka);
		mnuBanka.add(itemAnalitikaIzvoda);
		mnuBanka.add(itemMedjubankarskiNalog);
		mnuBanka.add(itemVezaMedjubankarskogNalogaIStavke);
		mnuIzvestaji.add(itemSpisakRacuna);
		mnuKorisnik.add(itemOdjaviSe);
		add(mnuBanka);
		add(mnuOperacije);
		add(mnuIzvestaji);
		add(mnuKorisnik);
		
	}

	public JMenu getMnuKorisnik() {
		return mnuKorisnik;
	}

	public void setMnuKorisnik(JMenu mnuKorisnik) {
		this.mnuKorisnik = mnuKorisnik;
	}
	
	

}
