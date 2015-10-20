package GUI;

import internalmemory.InternalMemory;
import internalmemory.MaliciousPatterns;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

//Event pou kaleitai otan kapoios epileksei thn epilogh View Memory apto menu
public class ViewMemory implements ActionListener {

	private JPanel panel;
	private Gui gui;

	private InternalMemory Memory;

	private JLabel listlabel;
	private JLabel patlabel;
	private JLabel iplabel;
	private JButton printB;
	private JList<String> Patlist;
	private JList<String> IPlist;
	private JList<String> list;
	private JScrollPane PatlistScroller;
	private JScrollPane IPlistScroller;
	private JScrollPane listScroller;

	public ViewMemory(Gui gui, JPanel panel, InternalMemory Memory) {
		this.gui = gui;

		this.Memory = Memory;
		this.panel = panel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.updateUI();
		gui.revalidate();
		gui.repaint();

		startActionsViewMemory();

	}
	//Dhmiourgei mia lista (list) me ola ta eggegramena PC
	//Analoga me thn epilogh apo auti th lista ananewnontai oi listes Patlist kai IPlist
	//me to kalesma tou Display Data Event
	public void startActionsViewMemory() {
		GridBagConstraints cb;
		cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);
		
		String[] choices = null;
		int i = 0;
		DefaultListModel<String> listModel = new DefaultListModel<>();

		Patlist = new JList<>();
		Patlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Patlist.setLayoutOrientation(JList.VERTICAL);
		Patlist.setVisibleRowCount(-1);

		IPlist = new JList<>();
		IPlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPlist.setLayoutOrientation(JList.VERTICAL);
		IPlist.setVisibleRowCount(-1);

		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		PatlistScroller = new JScrollPane(Patlist);
		PatlistScroller.setPreferredSize(new Dimension(250, 250));
		PatlistScroller.setBackground(Color.WHITE);

		IPlistScroller = new JScrollPane(IPlist);
		IPlistScroller.setPreferredSize(new Dimension(250, 250));
		IPlistScroller.setBackground(Color.WHITE);

		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(550, 250));
		listScroller.setBackground(Color.WHITE);

		cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);

		JLabel title = new JLabel(
				"Choose from list of registered machines to view updated malicious ips/patterns for this machine");
		cb.gridx = 0;
		cb.gridy = 0;
		cb.gridwidth = 4;
		panel.add(title, cb);
		cb.gridwidth = 1;

		listlabel = new JLabel("Registered Machines");
		cb.gridx = 0;
		cb.gridy = 2;
		panel.add(listlabel, cb);

		iplabel = new JLabel("Malicious IPs");
		cb.gridx = 2;
		cb.gridy = 2;
		panel.add(iplabel, cb);

		patlabel = new JLabel("Malicious Patterns");
		cb.gridx = 3;
		cb.gridy = 2;
		panel.add(patlabel, cb);

		cb.gridx = 0;
		cb.gridy = 3;
		panel.add(listScroller, cb);

		cb.gridx = 2;
		cb.gridy = 3;
		panel.add(IPlistScroller, cb);

		cb.gridx = 3;
		cb.gridy = 3;
		panel.add(PatlistScroller, cb);

		printB = new JButton("Print >>");
		cb.gridx = 1;
		cb.gridy = 3;
		panel.add(printB, cb);
		gui.validate();
		gui.repaint();
		Map<String, MaliciousPatterns> mem = Memory.getMem();
		if (!mem.keySet().isEmpty()) {
			choices = new String[mem.keySet().size()];
			for (String temp : mem.keySet()) {
				choices[i] = temp;
				i++;

			}
			for (String temp : choices) {
				listModel.addElement(temp);
			}
			DisplayDataEvent event = new DisplayDataEvent(gui, Memory, list,
					IPlist, Patlist);
			printB.addActionListener(event);

		}

	}

}