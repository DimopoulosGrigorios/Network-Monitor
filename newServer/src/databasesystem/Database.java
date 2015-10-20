package databasesystem;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.AvailableNodes;

public class Database {
	// H klash database ulopoiei to shmeio dhmiourgias kai diaxeirishs ths bashs
	// mysql gia thn //
	// apothikeysh dedomenwn apo tous clients//
	// ---------------------------------------------------------------------------------------//
	private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private String DB_URL = "jdbc:mysql://localhost/";
	private String USER;
	private String PASS;
	private String DBNAME;

	// ---------------------------------------------------------------------------------------//
	// Database constructors//
	public Database(String dbname) {
		DBNAME = dbname;
		create();
		create_the_tables();
	}

	public Database(String user, String pass, String jdbc_driver,
			String db_url, String dbname) {
		DBNAME = dbname;
		JDBC_DRIVER = jdbc_driver;
		DB_URL = db_url;
		USER = user;
		PASS = pass;
		create();
		create_the_tables();
	}

	// ---------------------------------------------------------------------------------------//
	// Database getters kai setters//
	public String getDBNAME() {
		return DBNAME;
	}

	public String getUSER() {
		return USER;
	}

	public String getPASS() {
		return PASS;
	}

	public String getDB_URL() {
		return DB_URL;
	}

	public String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public void setUSER(String usr) {
		USER = usr;
	}

	public void setPASS(String pass) {
		PASS = pass;
	}

	public void setDBNAME(String DB) {
		DBNAME = DB;
	}

	public void setDB_URL(String URL) {
		DB_URL = URL;
	}

	public void setJDBC_DRIVER(String DRVR) {
		JDBC_DRIVER = DRVR;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh pou dhmiourgei thn database//
	public void create() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE " + DBNAME;
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh pou dhmiourgei ta apaitoumena tables sthn database//
	public void create_the_tables() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String sql;
			sql = "CREATE TABLE " + "Associated_Devices"
					+ "(PC_Uuid VARCHAR(255),Connection_Status VARCHAR(255),"
					+ "PRIMARY KEY ( PC_Uuid ))";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE "
					+ "Malicious_Pattern_History"
					+ "(PC_Uuid VARCHAR(255),Interface_ip VARCHAR(255),"
					+ "Malicious_Pattern VARCHAR(255),"
					+ "Frequency VARCHAR(255),Recency VARCHAR(255),"
					+ "PRIMARY KEY ( PC_Uuid,Interface_ip,Malicious_Pattern,Recency))";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE "
					+ "Malicious_Ip_History"
					+ "(PC_Uuid VARCHAR(255),Interface_ip VARCHAR(255),"
					+ "Malicious_Ip VARCHAR(255),"
					+ "Frequency VARCHAR(255),Recency VARCHAR(255),"
					+ "PRIMARY KEY ( PC_Uuid,Interface_ip,Malicious_Ip,Recency))";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE " + "Client_Interfaces"
					+ "(PC_Uuid VARCHAR(255),Interface_ip VARCHAR(255),"
					+ "Interface_name VARCHAR(255),"
					+ "PRIMARY KEY ( PC_Uuid,Interface_ip))";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE " + "Smartphone_Users"
					+ "(Username VARCHAR(255),Password VARCHAR(255),"
					+ "Admin VARCHAR(1)," + "PRIMARY KEY ( Username))";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE " + "Nodes_Ownership"
					+ "(PC_Uuid VARCHAR(255),Username VARCHAR(255),"
					+ "PRIMARY KEY ( PC_Uuid))";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh arxikopoihshs syndeshs me mysql//
	public Connection select() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL + DBNAME, USER, PASS);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh diagrafh listas//
	public void drop() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String sql = "DROP DATABASE " + DBNAME;
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh eisagwghs eggrafhs ston pinaka Associated Devices//
	public void addRecordtoAssociated_Devices(String uuid, String conectivity) {
		Connection conn = null;
		Statement stmt = null;
		try {
			if (selectfromAssociated_Devices(uuid).equals("no_data")) {
				conn = select();
				stmt = conn.createStatement();
				String uuid2 = "\"" + uuid + "\"";
				String conectivity2 = "\"" + conectivity + "\"";
				String sql = "INSERT INTO Associated_Devices VALUES(" + uuid2
						+ "," + conectivity2 + ")";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------------------------------------------------------------//
	public void addRecordtoNodes_Ownership(String uuid, String username) {
		Connection conn = null;
		Statement stmt = null;
		try {
			if (selectfromNodes_Ownership(uuid).equals("no_data")) {
				conn = select();
				stmt = conn.createStatement();
				String uuid2 = "\"" + uuid + "\"";
				String username2 = "\"" + username + "\"";
				String sql = "INSERT INTO Nodes_Ownership VALUES(" + uuid2
						+ "," + username2 + ")";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// --------------------------------------------------------------------------------------------------------------//

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh eisagwghs eggrafhs sto ston pinaka Malicious Pattern History//
	public void addRecordtoMalicious_Pattern_History(String uuid,
			String interface_ip, String malicious_pattern, String frequency,
			String recency) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String interface_ip2 = "\"" + interface_ip + "\"";
			String malicious_pattern2 = "\"" + malicious_pattern + "\"";
			String frequency2 = "\"" + frequency + "\"";
			String recency2 = "\"" + recency + "\"";
			String sql = "INSERT INTO Malicious_Pattern_History VALUES ("
					+ uuid2 + "," + interface_ip2 + "," + malicious_pattern2
					+ "," + frequency2 + "," + recency2 + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh eisagwghs eggrafhs ston pinaka Client Interfaces//
	public void addRecordtoClient_Interfaces(String uuid, String interface_ip,
			String interface_name) {
		Connection conn = null;
		Statement stmt = null;
		try {
			if (selectfromClient_Interfaces(uuid, interface_ip).equals(
					"no_data")) {
				conn = select();
				stmt = conn.createStatement();
				String uuid2 = "\"" + uuid + "\"";
				String interface_ip2 = "\"" + interface_ip + "\"";
				String interface_name2 = "\"" + interface_name + "\"";
				String sql = "INSERT INTO Client_Interfaces VALUES(" + uuid2
						+ "," + interface_ip2 + "," + interface_name2 + ")";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------
	public boolean addRecordtoSmartphone_Users(String Username, String Password) {
		Connection conn = null;
		Statement stmt = null;
		try {
			if (selectPASSWORDfromSmartphone_Users(Username).equals("")) {
				conn = select();
				stmt = conn.createStatement();
				String username2 = "\"" + Username + "\"";
				String password2 = "\"" + Password + "\"";
				String admin2 = "\"" + "N" + "\"";
				String sql = "INSERT INTO Smartphone_Users VALUES(" + username2
						+ "," + password2 + "," + admin2 + ")";
				stmt.executeUpdate(sql);
			} else {
				return false;
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return true;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Eisagwgh eggrafhs sto pinaka Malicious Ip History//
	public void addRecordtoMalicious_Ip_History(String uuid,
			String interface_ip, String malicious_ip, String frequency,
			String recency) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String interface_ip2 = "\"" + interface_ip + "\"";
			String malicious_ip2 = "\"" + malicious_ip + "\"";
			String frequency2 = "\"" + frequency + "\"";
			String recency2 = "\"" + recency + "\"";
			String sql = "INSERT INTO Malicious_Ip_History VALUES (" + uuid2
					+ "," + interface_ip2 + "," + malicious_ip2 + ","
					+ frequency2 + "," + recency2 + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse stoixeio apo ton pinaka Associated Devices bash to Pc UUID//
	public String selectfromAssociated_Devices(String selection) {
		Connection conn = null;
		Statement stmt = null;
		String rtrn = "";
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "SELECT Connection_Status  FROM Associated_Devices WHERE PC_Uuid="
					+ selection2;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return "no_data";
			}
			rtrn = rs.getString("Connection_Status");
			rs.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// --------------------------------------------------------------------------------------------------------------//
	public String selectfromNodes_Ownership(String selection) {
		Connection conn = null;
		Statement stmt = null;
		String rtrn = "";
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "SELECT Username  FROM Nodes_Ownership WHERE PC_Uuid="
					+ selection2;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return "no_data";
			}
			rtrn = rs.getString("Username");
			rs.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epestrepse oles ths sundemenes kai mh suskeues//
	public ArrayList<ArrayList<String>> selectfromAssociated_Devicesall() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String sql = "SELECT *  FROM Associated_Devices";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String PC_Uuid = rs.getString("PC_Uuid");
				partofrtrn.add(PC_Uuid);
				String Connection_Status = rs.getString("Connection_Status");
				partofrtrn.add(Connection_Status);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Sunarthsh pou epistrefei apo Malicious Pattern History oles tis eggrafes
	// pou exoun uuid kai recency auto pou orizetai//
	public ArrayList<ArrayList<String>> selectfromMalicious_Pattern_History(
			String uuid, String recency) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String recency2 = "\"" + recency + "\"";
			String sql = "SELECT Interface_ip,Malicious_Pattern,Frequency  FROM Malicious_Pattern_History WHERE PC_Uuid="
					+ uuid2 + "&&Recency=" + recency2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String Interface_ip = rs.getString("Interface_ip");
				partofrtrn.add(Interface_ip);
				String Malicious_Pattern = rs.getString("Malicious_Pattern");
				partofrtrn.add(Malicious_Pattern);
				String Frequency = rs.getString("Frequency");
				partofrtrn.add(Frequency);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse apo ton pinaka Malicious Pattern History bash tou uuid kai tou
	// interface ip//
	public ArrayList<ArrayList<String>> selectfromMalicious_Pattern_Historywithip(
			String uuid, String ip) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String ip2 = "\"" + ip + "\"";
			String sql = "SELECT Recency,Malicious_Pattern,Frequency  FROM Malicious_Pattern_History WHERE PC_Uuid="
					+ uuid2 + "&&Interface_ip=" + ip2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String Malicious_Pattern = rs.getString("Malicious_Pattern");
				partofrtrn.add(Malicious_Pattern);
				String Frequency = rs.getString("Frequency");
				partofrtrn.add(Frequency);
				String Recency = rs.getString("Recency");
				partofrtrn.add(Recency);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse apo ton Pinaka Client Interfaces bash tou Uuid kai tou interface
	// ip//
	public String selectfromClient_Interfaces(String uuid, String ip) {
		Connection conn = null;
		Statement stmt = null;
		String rtrn = "";
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String ip2 = "\"" + ip + "\"";
			String sql = "SELECT Interface_name FROM Client_Interfaces WHERE PC_Uuid="
					+ uuid2 + "&&Interface_ip=" + ip2;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return "no_data";
			}
			rtrn = rs.getString("Interface_name");
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse apo ton pinaka CLient Interfaces oles tis eggrafes me uuid to
	// kathorismeno//
	public ArrayList<ArrayList<String>> selectfromClient_Interfacesall(
			String uuid) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String sql = "SELECT  Interface_ip,Interface_name FROM Client_Interfaces WHERE PC_Uuid="
					+ uuid2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String Interface_ip = rs.getString("Interface_ip");
				partofrtrn.add(Interface_ip);
				String Interface_name = rs.getString("Interface_name");
				partofrtrn.add(Interface_name);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ----------------------------------------------------------------------------------------------------------------//
	public AvailableNodes selectfromNodes_Ownershipsall(String username) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> rtrn = new ArrayList<String>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String username2 = "\"" + username + "\"";
			String sql = "SELECT  Pc_Uuid FROM Nodes_Ownership WHERE Username="
					+ username2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Pc_Uuid = rs.getString("Pc_Uuid");
				rtrn.add(Pc_Uuid);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		AvailableNodes av = new AvailableNodes();
		av.setNodes(rtrn);
		return av;
	}

	// --------------------------------------------------------------------------------------------------------------//
	public String selectPASSWORDfromSmartphone_Users(String username) {
		Connection conn = null;
		Statement stmt = null;
		String Password = "no_data";
		try {
			conn = select();
			stmt = conn.createStatement();
			String username2 = "\"" + username + "\"";
			String sql = "SELECT  Password FROM Smartphone_Users WHERE Username="
					+ username2;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return "";
			}
			Password = rs.getString("Password");
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return Password;
	}
	// --------------------------------------------------------------------------------------------------------------//
	public String selectADMINfromSmartphone_Users(String username) {
		Connection conn = null;
		Statement stmt = null;
		String Admin = "no_data";
		try {
			conn = select();
			stmt = conn.createStatement();
			String username2 = "\"" + username + "\"";
			String sql = "SELECT  Admin FROM Smartphone_Users WHERE Username="
					+ username2;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return "no_data";
			}
			Admin = rs.getString("Admin");
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return Admin;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse apo ton pinaka Malicious Ip History tis eggrafes me uuid kai
	// recency auto pou dinetai//
	public ArrayList<ArrayList<String>> selectfromMalicious_Ip_History(
			String uuid, String recency) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String recency2 = "\"" + recency + "\"";
			String sql = "SELECT Interface_ip,Malicious_Ip,Frequency  FROM Malicious_Ip_History WHERE PC_Uuid="
					+ uuid2 + "&&Recency=" + recency2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String Interface_ip = rs.getString("Interface_ip");
				partofrtrn.add(Interface_ip);
				String Malicious_Ip = rs.getString("Malicious_Ip");
				partofrtrn.add(Malicious_Ip);
				String Frequency = rs.getString("Frequency");
				partofrtrn.add(Frequency);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Epelekse tis eggrafes apo ton pinaka Malicious Ip History me uuid kai
	// interface ip to kathorismeno//
	public ArrayList<ArrayList<String>> selectfromMalicious_Ip_Historywithip(
			String uuid, String ip) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> rtrn = new ArrayList<ArrayList<String>>();
		try {
			conn = select();
			stmt = conn.createStatement();
			String uuid2 = "\"" + uuid + "\"";
			String ip2 = "\"" + ip + "\"";
			String sql = "SELECT Malicious_Ip,Frequency,Recency  FROM Malicious_Ip_History WHERE PC_Uuid="
					+ uuid2 + "&&Interface_ip=" + ip2;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ArrayList<String> partofrtrn = new ArrayList<String>();
				String Malicious_Ip = rs.getString("Malicious_Ip");
				partofrtrn.add(Malicious_Ip);
				String Frequency = rs.getString("Frequency");
				partofrtrn.add(Frequency);
				String Recency = rs.getString("Recency");
				partofrtrn.add(Recency);
				rtrn.add(partofrtrn);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return rtrn;
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Eishgage dedomena sthn database apo to statistical report//
	public void addfromStatisticalReport(StatisticalReport report) {
		String[][] mipreport = report.getMIPreport();
		String[][] mpreport = report.getMPreport();
		DateFormat formatter = new SimpleDateFormat("MM dd kk:mm:ss z yyyy");
		Date date = new Date();
		for (int i = 0; i < mipreport.length; i++) {
			String interface_ip = mipreport[i][0];
			String interface_name = mipreport[i][1];
			String malicious_ip = mipreport[i][2];
			String frequency = mipreport[i][3];
			addRecordtoClient_Interfaces(report.getPcUUID(), interface_ip,
					interface_name);
			addRecordtoMalicious_Ip_History(report.getPcUUID(), interface_ip,
					malicious_ip, frequency, formatter.format(date));

		}
		for (int i = 0; i < mpreport.length; i++) {
			String interface_ip = mpreport[i][0];
			String interface_name = mpreport[i][1];
			String malicious_pattern = mpreport[i][2];
			String frequency = mpreport[i][3];
			addRecordtoClient_Interfaces(report.getPcUUID(), interface_ip,
					interface_name);
			addRecordtoMalicious_Pattern_History(report.getPcUUID(),
					interface_ip, malicious_pattern, frequency,
					formatter.format(date));
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	//Synarthsh pou kanei extract to pio prosfato statistical report gia ena client
	public StatisticalReport extractStatisticalReport(String Uuid) {
		ArrayList<ArrayList<String>> mipreportarr = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> mpreportarr = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> interfaces = selectfromClient_Interfacesall(Uuid);
		for (ArrayList<String> interface1 : interfaces) {
			String name = interface1.get(1);
			String ip = interface1.get(0);
			ArrayList<ArrayList<String>> MIPS = takemostrecent(selectfromMalicious_Ip_Historywithip(
					Uuid, ip));
			ArrayList<ArrayList<String>> MPTS = takemostrecent(selectfromMalicious_Pattern_Historywithip(
					Uuid, ip));
			for (ArrayList<String> mip : MIPS) {
				ArrayList<String> miptemp = new ArrayList<String>();
				miptemp.add(ip);
				miptemp.add(name);
				miptemp.add(mip.get(0));
				miptemp.add(mip.get(1));
				mipreportarr.add(miptemp);
			}
			for (ArrayList<String> mpt : MPTS) {
				ArrayList<String> mpttemp = new ArrayList<String>();
				mpttemp.add(ip);
				mpttemp.add(name);
				mpttemp.add(mpt.get(0));
				mpttemp.add(mpt.get(1));
				mpreportarr.add(mpttemp);
			}

		}
		
		String[][] mipreport = new String[mipreportarr.size()][4];
		String[][] mpreport = new String[mpreportarr.size()][4];
		for (int i = 0; i < mipreportarr.size(); i++) {
			mipreport[i][0] = mipreportarr.get(i).get(0);
			mipreport[i][1] = mipreportarr.get(i).get(1);
			mipreport[i][2] = mipreportarr.get(i).get(2);
			mipreport[i][3] = mipreportarr.get(i).get(3);
		}
		for (int i = 0; i < mpreportarr.size(); i++) {
			mpreport[i][0] = mpreportarr.get(i).get(0);
			mpreport[i][1] = mpreportarr.get(i).get(1);
			mpreport[i][2] = mpreportarr.get(i).get(2);
			mpreport[i][3] = mpreportarr.get(i).get(3);
		}
		return (new StatisticalReport(Uuid, mpreport, mipreport));
	}
	// ------------------------------------------------------------------------------------------------------------//
	//Sunarthsh pou xrhsimopoieitai gia thn euresh twn pio prosfatwn stoixeiwn apo ta tables me ta malicious patterns/ips
	public ArrayList<ArrayList<String>> takemostrecent(
			ArrayList<ArrayList<String>> IN) {
		DateFormat formatter = new SimpleDateFormat("MM dd kk:mm:ss z yyyy");
		ArrayList<ArrayList<String>> back = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> item : IN) {
			for (ArrayList<String> item2 : IN) {
				if (item.get(0).equals(item2.get(0))) {
					Date date1 = null;
					try {
						date1 = formatter.parse(item.get(2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date date2 = null;
					try {
						date2 = formatter.parse(item2.get(2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (date1.compareTo(date2) < 0) {
						item = item2;
					}
				}
			}
			ArrayList<String> itemtemp = new ArrayList<String>();
			itemtemp.add(item.get(0));
			itemtemp.add(item.get(1));
			if (!back.contains(itemtemp)) {
				back.add(itemtemp);
			}
		}
		return back;
	}

	// --------------------------------------------------------------------------------------------------------------//
	public void addfromAvailableNodes(AvailableNodes aN, String uN) {
		ArrayList<String> lista = aN.getNodes();
		for (String item : lista) {
			addRecordtoNodes_Ownership(item, uN);
		}
	}

	// ------------------------------------------------------------------------------------------------------------//
	// Synarthsh pou kanei update thn eggrafh me Uuid to connectivity pou exei
	// oristei//
	public void updateAssociated_Devices(String Uuid, String connectivity) {
		Connection conn = null;
		Statement stmt = null;
		try {
			if (!selectfromAssociated_Devices(Uuid).equals("no_data")) {
				conn = select();
				stmt = conn.createStatement();
				String connectivity2 = "\"" + connectivity + "\"";
				String Uuid2 = "\"" + Uuid + "\"";
				String sql = "UPDATE Associated_Devices "
						+ "SET Connection_Status =" + connectivity2
						+ " WHERE  PC_Uuid=" + Uuid2;
				stmt.executeUpdate(sql);
			} else {
				addRecordtoAssociated_Devices(Uuid, connectivity);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deletefromAssociated_Devices(String selection) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "DELETE  FROM Associated_Devices WHERE PC_Uuid="
					+ selection2;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deletefromClient_Interfaces(String selection) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "DELETE  FROM Client_Interfaces WHERE PC_Uuid="
					+ selection2;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deletefromMalicious_Ip_History(String selection) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "DELETE  FROM Malicious_Ip_History WHERE PC_Uuid="
					+ selection2;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deletefromMalicious_Pattern_History(String selection) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "DELETE  FROM Malicious_Pattern_History WHERE PC_Uuid="
					+ selection2;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deletefromNodes_Ownership(String selection) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = select();
			stmt = conn.createStatement();
			String selection2 = "\"" + selection + "\"";
			String sql = "DELETE  FROM Nodes_Ownership WHERE PC_Uuid="
					+ selection2;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	// ------------------------------------------------------------------------------------------------------------//
	public void deleteTerminal(String Pc_Uuid)
	{
		deletefromAssociated_Devices(Pc_Uuid);
		deletefromClient_Interfaces(Pc_Uuid);
		deletefromMalicious_Ip_History(Pc_Uuid);
		deletefromMalicious_Pattern_History(Pc_Uuid);
		deletefromNodes_Ownership(Pc_Uuid);
	}
}
