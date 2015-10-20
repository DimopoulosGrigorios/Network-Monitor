package internalmemory;

import java.util.HashMap;

import java.util.Map;

public class InternalMemory {

	private Map<String, MaliciousPatterns> Mem;

	private MaliciousPatterns MalList;

	public InternalMemory() {
		this.Mem = new HashMap<String, MaliciousPatterns>();
		this.MalList = new MaliciousPatterns();
	}

	// PRINTERS
	public String print() {
		String ret = MalList.print();
		for (String key : Mem.keySet()) {
			System.out.println(key);
			Mem.get(key).print();
		}
		return ret;
	}

	public String printIPs() {
		String ret = "";
		int i = 1;
		for (String temp : MalList.getMalIPs()) {
			ret = ret + i + ". " + temp + "\n";
			i++;
		}
		return ret;
	}

	public String printPatterns() {
		String ret = "";
		int i = 1;
		for (String temp : MalList.getMalPatterns()) {
			ret = ret + i + ". " + temp + "\n";
			i++;
		}
		return ret;
	}

	// ACCESSORS
	public MaliciousPatterns getMalList() {
		return MalList;

	}

	public Map<String, MaliciousPatterns> getMem() {
		return Mem;
	}

	public MaliciousPatterns getPatternsFromNode(String UUID) {
		return Mem.get(UUID);
	}

	// UPDATER

	public void updateNode(String UUID, MaliciousPatterns Patterns) {
		removeNode(UUID);
		insertNewNode(UUID, Patterns);
	}

	// SETTERS
	public void setMalList(MaliciousPatterns MalList) {
		this.MalList = MalList;
	}

	// INSERTS
	public void insertIPtoList(String IP) {
		MalList.addIP(IP);
	}

	public void insertPatterntoList(String pattern) {
		MalList.addPattern(pattern);
	}

	public void insertNewNode(String UUID, MaliciousPatterns Patterns) {
		if (Patterns == null) {
			MaliciousPatterns mal = new MaliciousPatterns();
			Mem.put(UUID, mal);
		} else
			Mem.put(UUID, Patterns);
	}

	public void insertIPinNode(String UUID, String IP) {
		Mem.get(UUID).addIP(IP);
	}

	public void insertPatterninNode(String UUID, String pattern) {
		Mem.get(UUID).addPattern(pattern);
	}

	// REMOVER
	public void removeNode(String UUID) {
		Mem.remove(UUID);
	}

	// GUI FUNCTIONS
	public void insertFromGuiIPs(String IP) {

		MalList.addIP(IP);

	}

	public void insertFromGuiPatterns(String Pattern) {

		MalList.addPattern(Pattern);
	}
}
