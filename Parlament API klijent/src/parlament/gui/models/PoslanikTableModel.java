package parlament.gui.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import parlament.Poslanik;
import parlament.gui.GUIKontroler;

@SuppressWarnings("serial")
public class PoslanikTableModel extends AbstractTableModel{
	
	private final String[] kolone = new String[] { "ID", "Ime", "Prezime", "Datum rodjenja"};
	private List<Poslanik> poslanici;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	

	public PoslanikTableModel(List<Poslanik> poslanici) {
		if (poslanici == null) {
			this.poslanici = new LinkedList<>();
		}else{
			this.poslanici = poslanici;
		}
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return kolone[column];
	}

	@Override
	public int getRowCount() {
		return poslanici.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);
		switch(columnIndex){
		case 0: 
			return p.getId();
		case 1:
			return p.getIme();
		case 2:
			return p.getPrezime();
		case 3:
			if(p.getDatumRodjenja() != null)
				return sdf.format(p.getDatumRodjenja());
			else
				return "nepoznat";
		default:
			return "NN";
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);
		switch(columnIndex){
		case 0:
			break;
		case 1:
			if (value instanceof String) {
				String ime = (String) value;
				if (ime == null || ime.isEmpty()){
					GUIKontroler.prikaziPorukuGreske("Ime ne sme biti null niti prazan string.");
				}else{
					p.setIme(ime);
				}
			}
			break;
		case 2:
			if(value instanceof String){
				String prezime = (String) value;
				if (prezime == null || prezime.isEmpty()){
					GUIKontroler.prikaziPorukuGreske("Prezime ne sme biti null niti prazan string.");
				}else{
					p.setPrezime(prezime);
				}
			}
			break;
		case 3:
			if(value instanceof String){
				String datum = (String) value;
				try {
					if(datum.equals("nepoznat"))
						break;
					
					if(datum.charAt(2) == '.' && datum.charAt(5) == '.' && datum.charAt(10) == '.'){
						p.setDatumRodjenja(sdf.parse(datum));
					}else{
						GUIKontroler.prikaziPorukuGreske("Upisani datum mora biti u formatu: 'dd.MM.yyyy.' (npr. '02.08.1970.')");
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			break;
		default:
			return;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return false;
		return true;
	}
	
	public void ucitajPoslanikeUTabelu(List<Poslanik> poslanici){
		this.poslanici = poslanici;
		fireTableDataChanged();
	}
	
	public Poslanik getPoslanikByIndex(int index){
		return poslanici.get(index);
	}

}
