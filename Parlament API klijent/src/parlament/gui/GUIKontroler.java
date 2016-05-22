package parlament.gui;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import parlament.Poslanik;
import parlament.gui.models.PoslanikTableModel;
import util.ParlamentAPIKomunikacija;
import util.ParlamentJsonUtility;

public class GUIKontroler {
	private static ParlamentGUI glavniProzor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					glavniProzor = new ParlamentGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniProzor.getContentPane(), "Da li zelite da izadjete iz programa?",
				"Zatvaranje aplikacije", JOptionPane.YES_NO_CANCEL_OPTION);

		if (opcija == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static void ucitajPoslanikeSaServisa() throws Exception {
		List<Poslanik> ucitaniPoslanici = ParlamentAPIKomunikacija.vratiPoslanike();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter("data/serviceMembers.json");
		
//		for (int i = 0; i < ucitaniPoslanici.size(); i++) {
//			Poslanik p = ucitaniPoslanici.get(i);
//			writer.write(gson.toJson(p));
//		}
		
		writer.write(gson.toJson(ucitaniPoslanici));
		writer.close();
	}

	public static void azurirajStatus(TextArea status, String poruka) {
		String text = status.getText();
		if(!text.isEmpty())
			poruka = text + "\n" + poruka;
		
		status.setText(poruka);
	}
	
	public static LinkedList<Poslanik> prebaciIzJsonUListu() throws Exception{
		FileReader reader = new FileReader("data/serviceMembers.json");
		Gson gson = new GsonBuilder().create();
		
		JsonArray pJson = gson.fromJson(reader, JsonArray.class);
		
		reader.close();
		
		LinkedList<Poslanik> poslanici = ParlamentJsonUtility.prebaciIzJsonUListu(pJson);
		
		return poslanici;
	}

	public static void prikaziPorukuGreske(String poruka) {
		JOptionPane.showMessageDialog(glavniProzor, poruka + "\nIzmenite unos.","Greska",JOptionPane.ERROR_MESSAGE);
	}

	public static void upisiUFajl(JTable table, String putanja) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(putanja);


		LinkedList<Poslanik> poslanici = new LinkedList<>();
		
		for (int i = 0; i < table.getRowCount(); i++) {
			PoslanikTableModel model = (PoslanikTableModel) table.getModel();
			Poslanik p = model.getPoslanikByIndex(i);
			poslanici.add(p);
		}
		
		writer.write(gson.toJson(poslanici));
		writer.close();
	}
}
