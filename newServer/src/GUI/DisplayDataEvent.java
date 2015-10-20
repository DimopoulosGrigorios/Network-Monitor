package GUI;

import internalmemory.InternalMemory;
import internalmemory.MaliciousPatterns;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;


//Kaleitai gia na ektupwsei ston xrhsth gia poia malicious patterns/ips 
//exei enhmerwthei to PC pou epilexthke
public class DisplayDataEvent implements ActionListener {
	private Gui gui;
	private InternalMemory Memory;
	private JList<String> list;

	private JList<String> IPlist;
	private JList<String> Patlist;

	public DisplayDataEvent(Gui gui, InternalMemory memory, JList<String> list,
			JList<String> iplist, JList<String> patlist) {
		this.gui = gui;
		this.Memory = memory;
		this.list = list;
		this.IPlist = iplist;
		this.Patlist = patlist;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String choice = list.getSelectedValue().toString();
		if (choice != null)
			PrintPCData(choice);

	}

	public void PrintPCData(String choice) {
		DefaultListModel<String> patmodel = new DefaultListModel<String>();
		DefaultListModel<String> ipmodel = new DefaultListModel<String>();
		Map<String, MaliciousPatterns> mem = Memory.getMem();

		GridBagConstraints cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);

		for (String temp : mem.keySet()) {
			if (choice.equals(temp)) {

				for (String out : mem.get(choice).getMalIPs()) {

					ipmodel.addElement(out);
				}

			}
		}
		IPlist.setModel(ipmodel);

		for (String temp : mem.keySet()) {
			if (choice.equals(temp)) {

				for (String out : mem.get(choice).getMalPatterns()) {

					patmodel.addElement(out);
				}

			}
		}
		Patlist.setModel(patmodel);

		gui.validate();
		gui.repaint();

	}

}
