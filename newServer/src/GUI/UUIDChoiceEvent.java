package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;

import databasesystem.Database;


//Vriskei to uuid pou epilexthke apo apo to drop down box
//kai ektupwnei sth lista PCList ta interfaces gia to sugkekrimeno uuid
public class UUIDChoiceEvent implements ActionListener {

	private Database Database;
	private JButton printB2;
	private JComboBox<String> cbox;
	private JList<String> PCList;
	private JList<String> MalList1;
	private JList<String> MalList2;
	private String uuid;

	public UUIDChoiceEvent(Database Database, JList<String> PCList,
			JList<String> MalList1, JList<String> MalList2, JButton printB2,
			JComboBox<String> cbox) {
		this.Database = Database;
		this.PCList = PCList;
		this.MalList1 = MalList1;
		this.MalList2 = MalList2;
		this.printB2 = printB2;
		this.cbox = cbox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (cbox.getSelectedIndex() > -1) {
			uuid = cbox.getSelectedItem().toString();
			uuid = uuid.replace(" (ONLINE)", "");
			uuid = uuid.replace(" (OFFLINE)", "");
			ArrayList<ArrayList<String>> Interfaces = Database
					.selectfromClient_Interfacesall(uuid);
			updateList(Interfaces);
		}

	}

	public void updateList(ArrayList<ArrayList<String>> Interfaces) {

		int i;
		DefaultListModel<String> pcmodel = new DefaultListModel<>();
		for (i = 0; i < Interfaces.size(); i++) {
			pcmodel.addElement(Interfaces.get(i).get(1) + " ("
					+ Interfaces.get(i).get(0) + ")");
		}
		PCList.setModel(pcmodel);

		SelectionEvent event = new SelectionEvent(Database, MalList1, MalList2,
				PCList, uuid);
		printB2.addActionListener(event);

	}
}
