package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import databasesystem.Database;


//ektupwnei stis listes malList1 kai malList2 ta malicious ips/patterns pou anixneutikan gia 
//to sugkekrimeno interface kai einai perasmena sthn database
public class SelectionEvent implements ActionListener {

	private Database database;
	private JList<String> malList1;
	private JList<String> malList2;
	private JList<String> pCList;
	private String uuid;

	public SelectionEvent(Database database, JList<String> malList1,
			JList<String> malList2, JList<String> pCList,String uuid) {
		this.database = database;
		this.malList1 = malList1;
		this.malList2 = malList2;
		this.pCList = pCList;
		this.uuid = uuid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String choice = new String(pCList.getSelectedValue().toString());
		String strSub = choice.substring(choice.indexOf('(')+1,choice.indexOf(')'));
		ArrayList<ArrayList<String>> maliciousip = database.selectfromMalicious_Ip_Historywithip(uuid,strSub);
		ArrayList<ArrayList<String>> maliciouspattern = database.selectfromMalicious_Pattern_Historywithip(uuid,strSub);
		updateLists(maliciousip, maliciouspattern);
		
	}

	public void updateLists(ArrayList<ArrayList<String>> maliciousip,
			ArrayList<ArrayList<String>> maliciouspattern) {
		int i = 0;
		DefaultListModel <String> malmodel = new DefaultListModel<>();
		for(i = 0; i < maliciousip.size(); i++){
			malmodel.addElement(maliciousip.get(i).get(0) + "  (" +maliciousip.get(i).get(1)+", "+maliciousip.get(i).get(2)+")");
		}
		malList1.setModel(malmodel);
		DefaultListModel <String> malmodel2 = new DefaultListModel<>();
		for(i = 0; i < maliciouspattern.size(); i++){
			malmodel2.addElement(maliciouspattern.get(i).get(0) + "  (" +maliciouspattern.get(i).get(1)+", "+maliciouspattern.get(i).get(2)+")");
		}
		malList2.setModel(malmodel2);
	}

}
