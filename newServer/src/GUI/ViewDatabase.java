package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import databasesystem.Database;


//Epilogh apo ta PC pou einai eggegramena sth vash dedomenwn (online/offline) 
//apo ena drop down box. Me to fire tou select button ananeonetai h lista
//me ta interfaces pou einai perasmena gia auto to pc. (kalesma uuidChoiceEvent)

public class ViewDatabase implements ActionListener {
	private JPanel panel;
	private Gui gui;

	private Database Database;

	public ViewDatabase(Gui gui, JPanel panel, Database Database) {
		this.gui = gui;

		this.panel = panel;

		this.Database = Database;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.updateUI();
		gui.revalidate();
		gui.repaint();

		startActionsDatabase();

	}

	public void startActionsDatabase() {
		GridBagConstraints cb;
		JComboBox<String> choicesBox;
		JButton choicesButton;
		JList<String> PCList;
		JScrollPane PCListScroller;
		JButton printB2;
		JList<String> MalList1;
		JScrollPane MalListScroller1;
		JList<String> MalList2;
		JScrollPane MalListScroller2;
		JLabel interfaceLabel;
		JLabel iplabel2;
		JLabel patlabel2;
		String[] choices;

		cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);

		ArrayList<ArrayList<String>> uuids = Database
				.selectfromAssociated_Devicesall();
		int i = 0;
		choices = new String[uuids.size()];
		for (i = 0; i < uuids.size(); i++) {
			choices[i] = uuids.get(i).get(0) + " (" + uuids.get(i).get(1) + ")";
		}
		JLabel title = new JLabel(
				"Choose from list of registered PCs (both offline and online)");
		cb.gridx = 0;
		cb.gridy = 0;
		cb.gridwidth = 4;
		panel.add(title, cb);
		choicesBox = new JComboBox<String>(choices);
		cb.gridx = 0;
		cb.gridy = 2;
		cb.gridwidth = 3;
		panel.add(choicesBox, cb);
		cb.gridwidth = 1;
		choicesButton = new JButton("Select");
		cb.gridx = 3;
		cb.gridy = 2;
		panel.add(choicesButton, cb);
		cb.gridx = 0;
		cb.gridy = 3;
		panel.add(new JSeparator(), cb);
		interfaceLabel = new JLabel("List of Interfaces");
		cb.gridx = 0;
		cb.gridy = 4;
		panel.add(interfaceLabel, cb);

		PCList = new JList<String>();
		PCList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PCList.setLayoutOrientation(JList.VERTICAL);
		PCList.setVisibleRowCount(-1);
		PCListScroller = new JScrollPane(PCList);
		PCListScroller.setPreferredSize(new Dimension(150, 350));
		PCListScroller.setBackground(Color.WHITE);

		cb.gridx = 0;
		cb.gridy = 5;
		panel.add(PCListScroller, cb);

		printB2 = new JButton("Print >>");
		cb.gridx = 1;
		cb.gridy = 5;
		panel.add(printB2, cb);

		iplabel2 = new JLabel("Malicious IPs  (count, last update time)");
		cb.gridx = 2;
		cb.gridy = 4;
		panel.add(iplabel2, cb);

		MalList1 = new JList<String>();
		MalList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MalList1.setLayoutOrientation(JList.VERTICAL);
		MalList1.setVisibleRowCount(-1);
		MalListScroller1 = new JScrollPane(MalList1);
		MalListScroller1.setPreferredSize(new Dimension(350, 350));
		MalListScroller1.setBackground(Color.WHITE);
		cb.gridx = 2;
		cb.gridy = 5;
		panel.add(MalListScroller1, cb);

		patlabel2 = new JLabel("Malicious Patterns  (count, last update time)");
		cb.gridx = 3;
		cb.gridy = 4;
		panel.add(patlabel2, cb);

		MalList2 = new JList<String>();
		MalList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MalList2.setLayoutOrientation(JList.VERTICAL);
		MalList2.setVisibleRowCount(-1);
		MalListScroller2 = new JScrollPane(MalList2);
		MalListScroller2.setPreferredSize(new Dimension(350, 350));
		MalListScroller2.setBackground(Color.WHITE);
		cb.gridx = 3;
		cb.gridy = 5;
		panel.add(MalListScroller2, cb);

		gui.validate();
		gui.repaint();

		UUIDChoiceEvent event = new UUIDChoiceEvent(Database, PCList, MalList1,
				MalList2, printB2, choicesBox);
		choicesButton.addActionListener(event);
	}

}