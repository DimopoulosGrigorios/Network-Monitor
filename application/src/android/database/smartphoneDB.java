package android.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SimpleDateFormat")
public class smartphoneDB extends SQLiteOpenHelper {
	// ##############################################################################################//
	public static final String DATABASE_NAME = "DataBasesmartphones.db";
	public static final String Associated_Devices = "Associated_Devices";
	public static final String Malicious_Pattern_History = "Malicious_Pattern_History";
	public static final String Malicious_Ip_History = "Malicious_Ip_History";
	public static final String Client_Interfaces = "Client_Interfaces";
	public static final String Task_table = "Task_table";
	public static final String Login_log = "Login_log";

	// ##############################################################################################//
	public smartphoneDB(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	// ##############################################################################################//
	//Dhmiourgia pinakwn ths bashs tou kinhtou
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table Associated_Devices(PC_Uuid text,Ownership text,PRIMARY KEY(PC_Uuid))");
		db.execSQL("create table Malicious_Pattern_History"
				+ "(PC_Uuid text,Interface_ip text,Malicious_Pattern text,Frequency text,Recency text,PRIMARY KEY(PC_Uuid,Interface_ip,Malicious_Pattern,Recency))");
		db.execSQL("create table Malicious_Ip_History"
				+ "(PC_Uuid text,Interface_ip text,Malicious_Ip text,Frequency text,Recency text,PRIMARY KEY(PC_Uuid,Interface_ip,Malicious_Ip,Recency))");
		db.execSQL("create table Client_Interfaces"
				+ "(PC_Uuid text,Interface_ip text,Interface_name text,PRIMARY KEY (PC_Uuid,Interface_ip))");
		db.execSQL("create table Task_table"
				+ "(Waiting_task text,Arguement text,Time text,PRIMARY KEY(Time))");
	}

	// ##############################################################################################//
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiou ston pinaka me ta termatika kai to ownership
	public void addRecordtoAssociated_Devices(String PC_Uuid, String Ownership) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		String Pc_Uuid2 = "\"" + PC_Uuid + "\"";
		ArrayList<String> check = selectfromAssociated_DevicesUuid(PC_Uuid);
		if (0 == check.size()) {
			contentValues.put("PC_Uuid", PC_Uuid);
			contentValues.put("Ownership", Ownership);
			db.insert("Associated_Devices", null, contentValues);
		} else {
			if (check.get(0).equals("") && (!Ownership.equals(""))) {
				db.delete("Associated_Devices", "Pc_Uuid=" + Pc_Uuid2, null);
				contentValues.put("PC_Uuid", PC_Uuid);
				contentValues.put("Ownership", Ownership);
				db.insert("Associated_Devices", null, contentValues);
			}
		}
	}
	// ##############################################################################################//
	//Diagrafh termatikou kai kathe egrafhs tou pou uparxei apo thn bash
	public Integer deleteTerminal(String Pc_Uuid) {
		String Pc_Uuid2 = "\"" + Pc_Uuid + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		int rtrn;
		rtrn = db.delete("Associated_Devices", "Pc_Uuid=" + Pc_Uuid2, null);
		rtrn = rtrn
				+ db.delete("Client_Interfaces", "Pc_Uuid=" + Pc_Uuid2, null);
		rtrn = rtrn
				+ db.delete("Malicious_Ip_History", "Pc_Uuid=" + Pc_Uuid2, null);
		rtrn = rtrn
				+ db.delete("Malicious_Pattern_History", "Pc_Uuid=" + Pc_Uuid2,
						null);
		return rtrn;
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiou ston pinaka me tis eggegramenes diepafes termatikwn 
	public void addRecordtoClient_Interfaces(String PC_Uuid,
			String Interface_ip, String Interface_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		if (null == selectfromClient_Interfaces(PC_Uuid, Interface_ip)) {
			contentValues.put("PC_Uuid", PC_Uuid);
			contentValues.put("Interface_ip", Interface_ip);
			contentValues.put("Interface_name", Interface_name);
			db.insert("Client_Interfaces", null, contentValues);
		}
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiou ston pinaka me ta malicious ip
	public void addRecordtoMalicious_Ip_History(String PC_Uuid,
			String Interface_ip, String Malicious_Ip, String Frequency,
			String Recency) {
		
		if (selectfromMalicious_Ip_History(PC_Uuid, Recency, Malicious_Ip, Interface_ip).size()==0)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("PC_Uuid", PC_Uuid);
			contentValues.put("Interface_ip", Interface_ip);
			contentValues.put("Malicious_Ip", Malicious_Ip);
			contentValues.put("Frequency", Frequency);
			contentValues.put("Recency", Recency);
			db.insert("Malicious_Ip_History", null, contentValues);
		}
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiou ston pinaka me ta malicious pattern
	public void addRecordtoMalicious_Pattern_History(String PC_Uuid,
			String Interface_ip, String Malicious_Pattern, String Frequency,
			String Recency) {
		if(selectfromMalicious_Pattern_History(PC_Uuid, Recency, Malicious_Pattern, Interface_ip).size()==0)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("PC_Uuid", PC_Uuid);
			contentValues.put("Interface_ip", Interface_ip);
			contentValues.put("Malicious_Pattern", Malicious_Pattern);
			contentValues.put("Frequency", Frequency);
			contentValues.put("Recency", Recency);
			db.insert("Malicious_Pattern_History", null, contentValues);
		}
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiou ston pinaka twn waiting tasks ta aithmata pou ekremoun pros ton server dhladh
	public void addRecordtoTask_table(String Waiting_task, String Arguement) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		DateFormat formatter = new SimpleDateFormat("MM dd kk:mm:ss z yyyy");
		Date date = new Date();
		contentValues.put("Waiting_task", Waiting_task);
		contentValues.put("Arguement", Arguement);
		contentValues.put("Time", formatter.format(date));
		db.insert("Task_table", null, contentValues);
	}

	// ##############################################################################################//
	//Epelekse ta termatika pou anoikoun sto sygkekrimeno  user
	public ArrayList<String> selectfromAssociated_Devices(String Ownership) {
		SQLiteDatabase db = this.getReadableDatabase();
		String Ownership2 = "\"" + Ownership + "\"";
		Cursor cursor = db.rawQuery(
				"select PC_Uuid from Associated_Devices where Ownership="
						+ Ownership2 + "", null);
		ArrayList<String> ret = new ArrayList<String>();
		try{
		if (cursor.moveToFirst()) {
			ret = new ArrayList<String>();
			do {
				ret.add((cursor.getString(0)));
			} while (cursor.moveToNext());
		}
		ret.removeAll(futuredelete());
		}
		finally
		{
			cursor.close();
		}
		return ret;
	}

	// ##############################################################################################//
	//Epelekse apo ta apothikeumena termatika auta me id auto pou orizetai kai pare se poion anoikei
	public ArrayList<String> selectfromAssociated_DevicesUuid(String PC_Uuid) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		Cursor cursor = db.rawQuery(
				"select Ownership from Associated_Devices where PC_Uuid="
						+ PC_Uuid2 + "", null);
		ArrayList<String> ret = new ArrayList<String>();
		try
		{
		if (cursor.moveToFirst()) {
			ret = new ArrayList<String>();
			do {
				ret.add((cursor.getString(0)));
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret;
	}

	// ##############################################################################################//
	//Pare ola ta apothikeumena termatika
	public ArrayList<String> selectfromAssociated_Devicesall() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select PC_Uuid from Associated_Devices",
				null);
		ArrayList<String> ret = new ArrayList<String>();
		try{
		if (cursor.moveToFirst()) {
			ret = new ArrayList<String>();
			do {
				ret.add((cursor.getString(0)));
			} while (cursor.moveToNext());
		}
		ret.removeAll(futuredelete());
		}
		finally
		{
			cursor.close();
		}
		return ret;
	}

	// ##############################################################################################//
	//Synarthsh pou mas deixnei poia termatika prokeitai na diagrafoun sto mellon
	//Xrhsimopoieitai otan o server einai down wste na mhn parousiazontai auta ta sygkekrimena termatika ston 
	//xrhsth mexris otou diagrafoun apo thn bash
	public ArrayList<String> futuredelete() {
		ArrayList<ArrayList<String>> ret = selectfromTask_tableall();
		ArrayList<String> retrn = new ArrayList<String>();
		if (ret.size() > 0) {
			for (ArrayList<String> item : ret) {
				if (item.get(0).equals("Delete")) {
					retrn.add(item.get(1));
				}
			}
		}
		return retrn;
	}

	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta interfaces to onoma autou tou interface pou brisketai sto pcuuid me 
	//interface ip auto pou orizetai
	public String selectfromClient_Interfaces(String PC_Uuid,
			String Interface_ip) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Interface_ip2 = "\"" + Interface_ip + "\"";
		Cursor cursor = db.rawQuery(
				"select Interface_name from Client_Interfaces where PC_Uuid="
						+ PC_Uuid2 + " AND " + "Interface_ip=" + Interface_ip2,
				null);
		String ret = null;
		try{
		if (cursor.moveToFirst()) {
			do {
				ret = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret;
	}

	// ##############################################################################################//
	//Epelekse ola ta interfaces apo ena termatiko
	public ArrayList<ArrayList<String>> selectfromClient_Interfacesall(
			String PC_Uuid) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		Cursor cursor = db.rawQuery(
				"select Interface_name,Interface_ip from Client_Interfaces where PC_Uuid="
						+ PC_Uuid2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try
		{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret2.add(ret);
			} while (cursor.moveToNext());
			}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious ip auta pou anoikoun ston pcuuid kai exoun to sugkekrimeno recency
	public ArrayList<ArrayList<String>> selectfromMalicious_Ip_History(
			String PC_Uuid, String Recency) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Recency2 = "\"" + Recency + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Interface_ip,Malicious_Ip,Frequency  FROM Malicious_Ip_History WHERE PC_Uuid="
								+ PC_Uuid2 + " AND Recency=" + Recency2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try
		{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious ip
	public ArrayList<ArrayList<String>> selectfromMalicious_Ip_History(
			String PC_Uuid, String Recency,String Malicious_IP,String Interface_ip) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Recency2 = "\"" + Recency + "\"";
		String MALICIOUSIP2 = "\"" + Malicious_IP + "\"";
		String Interface_ip2 = "\"" + Interface_ip + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Interface_ip,Malicious_Ip,Frequency  FROM Malicious_Ip_History WHERE PC_Uuid="
								+ PC_Uuid2 + " AND Recency=" + Recency2+ " AND Malicious_Ip="+MALICIOUSIP2+ " AND Interface_Ip="+Interface_ip2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try
		{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}
	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious ip
	public ArrayList<ArrayList<String>> selectfromMalicious_Pattern_History(
			String PC_Uuid, String Recency,String Malicious_Pattern,String Interface_ip) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Recency2 = "\"" + Recency + "\"";
		String MALICIOUSPATTERN2 = "\"" + Malicious_Pattern + "\"";
		String Interface_ip2 = "\"" + Interface_ip + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Interface_ip,Malicious_Pattern,Frequency  FROM Malicious_Pattern_History WHERE PC_Uuid="
								+ PC_Uuid2 + " AND Recency=" + Recency2+ " AND Malicious_Pattern="+MALICIOUSPATTERN2+ " AND Interface_Ip="+Interface_ip2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}
	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious ip  auta pou anoikoun ston termatiko pou orizetai kai 
	//anoikoun ston sugkekrimeno interface me to orizomeno ip
	public ArrayList<ArrayList<String>> selectfromMalicious_Ip_Historywithip(
			String PC_Uuid, String Interface_ip) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Interface_ip2 = "\"" + Interface_ip + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Malicious_Ip,Frequency,Recency  FROM Malicious_Ip_History WHERE PC_Uuid="
								+ PC_Uuid2
								+ " AND  Interface_ip="
								+ Interface_ip2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious pattern auta pou anoikoun ston pcuuid kai exoun to sugkekrimeno recency
	public ArrayList<ArrayList<String>> selectfromMalicious_Pattern_History(
			String PC_Uuid, String Recency) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Recency2 = "\"" + Recency + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Interface_ip,Malicious_Pattern,Frequency  FROM Malicious_Pattern_History WHERE PC_Uuid="
								+ PC_Uuid2 + " AND Recency=" + Recency2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Epelekse apo ton pinaka me ta malicious pattern  auta pou anoikoun ston termatiko pou orizetai kai 
	//anoikoun ston sugkekrimeno interface me to orizomeno ip
	public ArrayList<ArrayList<String>> selectfromMalicious_Pattern_Historywithip(
			String PC_Uuid, String Interface_ip) {
		SQLiteDatabase db = this.getReadableDatabase();
		String PC_Uuid2 = "\"" + PC_Uuid + "\"";
		String Interface_ip2 = "\"" + Interface_ip + "\"";
		Cursor cursor = db
				.rawQuery(
						"SELECT Malicious_Pattern,Frequency,Recency  FROM Malicious_Pattern_History WHERE PC_Uuid="
								+ PC_Uuid2
								+ " AND  Interface_ip="
								+ Interface_ip2, null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try
		{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Sunarthsh pou xrhsimopoieitai gia ton diaxwrismo twn pio prosfatwn stoixeiwn mia sulloghs malicious ip/patterns
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
					if (date1.compareTo(date2) <= 0) {
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

	// ##############################################################################################//
	//Eisagwgh apo Available Nodes
	public void addfromAvailableNodes(AvailableNodes aN, String uN) {
		ArrayList<String> lista = aN.getNodes();
		for (String item : lista) {
			addRecordtoAssociated_Devices(item, uN);
		}
	}

	// ##############################################################################################//
	//Eisagwgh stoixeiwn apo StatisticalReport
	public void addfromStatisticalReport(StatisticalReport report) {
		String[][] mipreport = report.getMIPreport();
		String[][] mpreport = report.getMPreport();
		DateFormat formatter = new SimpleDateFormat("MM dd kk:mm:ss z yyyy");
		Date date = new Date();
		addRecordtoAssociated_Devices(report.getPcUUID(), "");
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

	// ##############################################################################################//
	//Epilogh olwn twn erggrafwn apo ton pinaka me ta etoima pou ekremoun pros twn athroisth
	public ArrayList<ArrayList<String>> selectfromTask_tableall() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Task_table", null);
		ArrayList<String> ret = null;
		ArrayList<ArrayList<String>> ret2 = new ArrayList<ArrayList<String>>();
		try{
		if (cursor.moveToFirst()) {
			ret2 = new ArrayList<ArrayList<String>>();
			do {
				ret = new ArrayList<String>();
				ret.add((cursor.getString(0)));
				ret.add((cursor.getString(1)));
				ret.add((cursor.getString(2)));
				ret2.add(ret);
			} while (cursor.moveToNext());
		}
		}
		finally
		{
			cursor.close();
		}
		return ret2;
	}

	// ##############################################################################################//
	//Pare to aithma pou egine prwto apo ton pinaka me ta aithmata pou ekremoun gia ton server
	public ArrayList<String> takemostrecentRecordfromTask_table() {
		ArrayList<ArrayList<String>> logs = selectfromTask_tableall();
		ArrayList<String> mo_recent = new ArrayList<String>();
		DateFormat formatter = new SimpleDateFormat("MM dd kk:mm:ss z yyyy");
		Date date1 = null;
		Date date = null;
		if (logs.size() <= 0)
			return mo_recent;
		for (ArrayList<String> item : logs) {
			try {
				date1 = formatter.parse(item.get(2));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if (date == null) {
				try {
					date = formatter.parse(item.get(2));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				mo_recent = item;
				continue;
			} else {
				if ((date1.compareTo(date) < 0)) {
					try {
						date1 = formatter.parse(item.get(2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					mo_recent = item;
				}
			}
		}
		deletefromTask_table(mo_recent.get(2));
		return mo_recent;
	}

	// ##############################################################################################//
	//Diegrapse apo to Task table
	public void deletefromTask_table(String Time) {
		String Time2 = "\"" + Time + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("Task_table", "Time=" + Time2, null);
	}

	// ##############################################################################################//
	//Diegrapse thn bash
	public void drop(Context context) {
		context.deleteDatabase(DATABASE_NAME);
	}
	// ##############################################################################################//
}