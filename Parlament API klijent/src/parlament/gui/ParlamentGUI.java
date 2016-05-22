package parlament.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import parlament.gui.models.PoslanikTableModel;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel eastPanel;
	private JPanel southPanel;
	private TextArea textAreaStatus;
	private JButton btnGetMembers;
	private JButton btnFillTable;
	private JButton btnUpdateMembers;
	private JScrollPane centralScrollPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ParlamentGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				GUIKontroler.ugasiAplikaciju();
			}
		});
		setTitle("Parlament Members");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 630, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getEastPanel(), BorderLayout.EAST);
		contentPane.add(getSouthPanel(), BorderLayout.SOUTH);
		contentPane.add(getCentralScrollPane(), BorderLayout.CENTER);
	}

	private JPanel getEastPanel() {
		if (eastPanel == null) {
			eastPanel = new JPanel();
			eastPanel.setPreferredSize(new Dimension(160, 10));
			eastPanel.add(getBtnGetMembers());
			eastPanel.add(getBtnFillTable());
			eastPanel.add(getBtnUpdateMembers());
		}
		return eastPanel;
	}
	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setPreferredSize(new Dimension(10, 75));
			southPanel.setLayout(new BorderLayout(0, 0));
			southPanel.add(getTextAreaStatus(), BorderLayout.CENTER);
			Border blackline = BorderFactory.createLineBorder(Color.black);
			TitledBorder title = BorderFactory.createTitledBorder(blackline, "STATUS");
			title.setTitleJustification(TitledBorder.LEFT);
			southPanel.setBorder(title);
		}
		return southPanel;
	}
	private TextArea getTextAreaStatus() {
		if (textAreaStatus == null) {
			textAreaStatus = new TextArea();
			textAreaStatus.setEditable(false);
		}
		return textAreaStatus;
	}
	private JButton getBtnGetMembers() {
		if (btnGetMembers == null) {
			btnGetMembers = new JButton("GET members");
			btnGetMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						GUIKontroler.ucitajPoslanikeSaServisa();
						GUIKontroler.azurirajStatus(textAreaStatus, "Poslanici su uspesno ucitani sa servisa.");
					} catch (Exception e) {
						GUIKontroler.azurirajStatus(textAreaStatus, "Desila se greska. Pokusajte ponovo.");
					}
				}
			});
			btnGetMembers.setPreferredSize(new Dimension(140, 23));
		}
		return btnGetMembers;
	}
	private JButton getBtnFillTable() {
		if (btnFillTable == null) {
			btnFillTable = new JButton("Fill table");
			btnFillTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						osveziTabelu();
						GUIKontroler.azurirajStatus(textAreaStatus, "Tabela je popunjena podacima preuzetih sa servisa.");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			btnFillTable.setPreferredSize(new Dimension(140, 23));
		}
		return btnFillTable;
	}
	private JButton getBtnUpdateMembers() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						GUIKontroler.upisiUFajl(table, "data/updatedMembers.json");
						GUIKontroler.azurirajStatus(textAreaStatus, "Poslanici sa svim izmenama su uspesno sacuvani u novi fajl.");
					} catch (Exception e) {
						GUIKontroler.azurirajStatus(textAreaStatus, "Greska prilikom upisivanja.");

					}
				}
			});
			btnUpdateMembers.setPreferredSize(new Dimension(140, 23));
		}
		return btnUpdateMembers;
	}
	private JScrollPane getCentralScrollPane() {
		if (centralScrollPane == null) {
			centralScrollPane = new JScrollPane();
			centralScrollPane.setViewportView(getTable());
		}
		return centralScrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			PoslanikTableModel model = new PoslanikTableModel(null);
			table.setModel(model);
		}
		return table;
	}
	
	private void osveziTabelu() throws Exception{
		PoslanikTableModel model = (PoslanikTableModel) table.getModel();
		model.ucitajPoslanikeUTabelu(GUIKontroler.prebaciIzJsonUListu());
	}
}
