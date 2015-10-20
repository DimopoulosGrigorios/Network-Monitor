package server;

import internalmemory.InternalMemory;
import internalmemory.MaliciousPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import android.AvailableNodes;
import databasesystem.*;
import GUI.Gui;

@WebService(endpointInterface = "server.AdderService")
public class AdderServiceImpl implements AdderService {

	private InternalMemory Memory;
	private Database Database;
	private Gui myGui;
	private String deleted = "";

	public AdderServiceImpl() {
		System.out.println("Enter mysql username: ");
		Scanner scanner = new Scanner(System.in);
		String username = scanner.nextLine();
		System.out.println("Enter mysql password: ");
		String password = scanner.nextLine();
		scanner.close();

		this.Database = new Database(username, password,
				"com.mysql.jdbc.Driver", "jdbc:mysql://localhost/", "Reports");
		this.Memory = new InternalMemory();
		this.myGui = new Gui(Memory, Database);
		myGui.setDefaultCloseOperation(0);
		myGui.setSize(1200, 600);
		myGui.setVisible(true);
		myGui.setTitle("Adder Service");

	}

	// GETTERS
	public InternalMemory getMemory() {
		return Memory;
	}

	public Database getDatabase() {
		return Database;
	}

	public Gui getMyGui() {
		return myGui;
	}

	// PRINTERS
	public void print() {
		Memory.print();
	}

	// INTERNALMEMORY INSERTS
	public void insertIpInMemory(String ip, String UUID) {
		Memory.insertIPinNode(ip, UUID);
	}

	public void insertPatternInMemory(String pattern, String UUID) {
		Memory.insertPatterninNode(pattern, UUID);
	}

	// DATABASE INSERT

	// --------------BASIC IMPLEMENTATION---------------------//
	// 1
	public boolean register(String nodeID) {
		Memory.insertNewNode(nodeID, null);
		Database.addRecordtoAssociated_Devices(nodeID, "ONLINE");
		return true;

	}

	// 2
	public MaliciousPatterns maliciousPatternRequest(String nodeID) {
		MaliciousPatterns ret = new MaliciousPatterns();
		MaliciousPatterns update = Memory.getMalList();
		Set<String> IPs;
		Set<String> Patterns;
		if (Memory.getMalList().getMalIPs().isEmpty()
				&& Memory.getMalList().getMalPatterns().isEmpty())
			return null;
		if (!Memory.getMem().isEmpty() && Memory.getMem().containsKey(nodeID)) {
			IPs = Memory.getPatternsFromNode(nodeID).getMalIPs();
			Patterns = Memory.getPatternsFromNode(nodeID).getMalPatterns();
			if (IPs.isEmpty() && Patterns.isEmpty()) {
				if (!update.getMalIPs().isEmpty())
					for (String temp : update.getMalIPs())
						Memory.insertIPinNode(nodeID, temp);
				if (!update.getMalPatterns().isEmpty())
					for (String temp : update.getMalPatterns())
						Memory.insertPatterninNode(nodeID, temp);
				ret = update;
			} else {
				if (!update.getMalIPs().isEmpty())
					for (String temp : update.getMalIPs()) {
						if (!IPs.contains(temp)) {
							Memory.insertIPinNode(nodeID, temp);
							ret.addIP(temp);
						}
					}
				if (!update.getMalPatterns().isEmpty())
					for (String temp : update.getMalPatterns()) {
						if (!Patterns.contains(temp)) {
							Memory.insertPatterninNode(nodeID, temp);
							ret.addPattern(temp);
						}
					}
			}
			return ret;
		} else
			return null;
	}

	// 3
	public void maliciousPatternsStatisticalReport(String nodeID,
			StatisticalReport m) {
		// Kalw copy constructor giati otan to statistical report apo thn meria
		// tou client eixe
		// kapoio keno arxikopoihmeno pedio meta to serialization htan null kai
		// oxi arxikopoihmeno
		// etsi me ton copy constructor arxikopoiw kai opoia pedia einai null
		StatisticalReport mn = new StatisticalReport(m.getPcUUID(),
				m.getMPreport(), m.getMIPreport());
		Database.addfromStatisticalReport(mn);
	}

	// 4
	public boolean unregister(String nodeID) {
		Memory.removeNode(nodeID);
		Database.updateAssociated_Devices(nodeID, "OFFLINE");

		return true;
	}

	// ----------------------------------//
	public boolean register_android(@WebParam(name = "name") String username,
			@WebParam(name = "pass") String password,
			@WebParam(name = "nodes") AvailableNodes nodes) {

		if (!Database.selectPASSWORDfromSmartphone_Users(username).equals(""))
			return false;

		Database.addRecordtoSmartphone_Users(username, password);
		Database.addfromAvailableNodes(nodes, username);

		return true;
	}

	public List<StatisticalReport> retrieveStatistics(String username,
			String password) {
		List<StatisticalReport> ret = new ArrayList<StatisticalReport>();
		ArrayList<ArrayList<String>> list = Database
				.selectfromAssociated_Devicesall();
		for (ArrayList<String> temp : list) {
			ret.add(Database.extractStatisticalReport(temp.get(0)));
		}

		return ret;

	}

	public String retrieveMaliciousPatterns(
			@WebParam(name = "username") String username,
			@WebParam(name = "password") String password) {
		String ret = "";
		for (String a : Memory.getMalList().getMalIPs()) {
			ret = ret + a + "#";
		}
		ret = ret + "//";
		for (String a : Memory.getMalList().getMalPatterns()) {
			ret = ret + a + "#";
		}
		return ret;
	}

	public void insertMaliciousPatterns(
			@WebParam(name = "username") String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "maliciousIP") String maliciousIP,
			@WebParam(name = "maliciousPatterns") String maliciousPatterns) {

		if (!maliciousIP.equals(""))
			Memory.insertIPtoList(maliciousIP);
		if (!maliciousPatterns.equals(""))
			Memory.insertPatterntoList(maliciousPatterns);

	}

	public boolean deletePC(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "uuid") String uuid) {
		if (!Database.selectfromAssociated_Devices(uuid).equals("no_data")) {
			deleted = deleted + "#" + uuid;
			while (!(Database.selectfromAssociated_Devices(uuid)
					.equals("OFFLINE"))) {
			}
			Database.deleteTerminal(uuid);
		}
		return true;
	}

	public int login(@WebParam(name = "name") String name,
			@WebParam(name = "pass") String pass) {
		if (name != null && pass != null) {

			if (Database.selectPASSWORDfromSmartphone_Users(name).equals(""))
				return 0;
			else {
				if (!Database.selectPASSWORDfromSmartphone_Users(name).equals(
						pass))
					return -1;
				String isAdmin = Database.selectADMINfromSmartphone_Users(name);
				if (isAdmin.equals("N"))
					return 1;
				else
					return 2;
			}

		}

		return 0;

	}
	public boolean logout(@WebParam(name = "username")String username, @WebParam(name = "password") String password)
	{
		return Database.selectPASSWORDfromSmartphone_Users(username).equals(password);
	}
	public String terminateClient() {
		return deleted;
	}
}
