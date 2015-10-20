package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.DataService.TaskProcessingThread.addMalicious;
import main.DataService.TaskProcessingThread.removeUUID;
import main.DataService.UpdaterThread.getfromServer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Intent;
import android.database.StatisticalReport;
import android.database.smartphoneDB;
import android.os.AsyncTask;
import android.os.IBinder;

public class DataService extends Service {
	// ##############################################################################################//
	private smartphoneDB DB;
	private static final String NAMESPACE = "http://server/";
	private static String URL;
	private static final String METHOD_NAME1 = "insertMaliciousPatterns";
	private static final String METHOD_NAME2 = "deletePC";
	private static final String METHOD_NAME3 = "retrieveStatistics";
	private Thread updateThread;
	private Thread TaskprocessThread;
	private SoapObject response;
	private Connectivity connection;
	private UpdaterThread update;
	private TaskProcessingThread Taskprocess;
	private List<addMalicious> addMals;
	private List<getfromServer> servertasks;
	private List<removeUUID> removes;

	// ##############################################################################################//
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// ##############################################################################################//
	@Override
	public void onCreate() {
		super.onCreate();
	}

	// ##############################################################################################//

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		setDB(new smartphoneDB(this));
		addMals = new LinkedList<addMalicious>();
		servertasks = new LinkedList<getfromServer>();
		removes = new LinkedList<removeUUID>();
		connection = new Connectivity();
		URL = MyPreferences.getUserIp(getApplicationContext());
		update = new UpdaterThread();
		updateThread = new Thread(update);
		updateThread.start();
		Taskprocess = new TaskProcessingThread();
		TaskprocessThread = new Thread(Taskprocess);
		TaskprocessThread.start();
		return START_STICKY;
	}

	// ##############################################################################################//

	@Override
	public void onDestroy() {

		update.stop();

		Taskprocess.stop();

		updateThread.interrupt();
		TaskprocessThread.interrupt();
		try {
			while (updateThread.isAlive())
				TaskprocessThread.join();
			while (TaskprocessThread.isAlive())
				updateThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DB.close();
		super.onDestroy();
	}

	// ##############################################################################################//
	public smartphoneDB getDB() {
		return DB;
	}

	// ##############################################################################################//
	public void setDB(smartphoneDB dB) {
		DB = dB;
	}

	// ##############################################################################################//
	// Thread mesa sto service pou diekperewnei ta aithmata pou anamenoun na
	// mpei online o server
	public class TaskProcessingThread implements Runnable {
		// ##################################//
		private String username;
		private String password;
		private String maliciousip;
		private String maliciouspattern;
		private String uuidrem;
		private boolean running = true;

		// ##################################//
		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

				}
				if (connection.isURLReachable(getApplicationContext())) {
					ArrayList<String> ret = DB
							.takemostrecentRecordfromTask_table();
					if (ret.size() > 0) {

						uuidrem = "";
						maliciousip = "";
						maliciouspattern = "";
						addMalicious addMal;
						if (ret.get(0).equals("AddMIP")) {
							String[] parts = ret.get(1).split("#");
							username = parts[0];
							password = parts[1];
							maliciousip = parts[2];
							addMal = new addMalicious();
							addMals.add(addMal);
							addMal.execute();

						} else if (ret.get(0).equals("AddMP")) {
							String[] parts = ret.get(1).split("#");
							username = parts[0];
							password = parts[1];
							maliciouspattern = parts[2];
							addMal = new addMalicious();
							addMals.add(addMal);
							addMal.execute();

						} else {
							uuidrem = ret.get(1);
							removeUUID remove = new removeUUID();
							removes.add(remove);
							remove.execute();

						}
					}
				}
			}
		}

		public void stop() {
			running = false;
		}

		// ##################################//
		// Eisagwgh malicious me xrhsh ksoap
		public class addMalicious extends AsyncTask<Void, Void, Void> {
			@Override
			protected Void doInBackground(Void... params) {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				request.addProperty("username", username);
				request.addProperty("password", password);
				request.addProperty("maliciousIP", maliciousip);
				request.addProperty("maliciousPatterns", maliciouspattern);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call(
							"\"http://server/insertMaliciousPatterns\"",
							envelope);
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				return null;
			}
			// ##################################//
		}

		// ##################################//
		// Diagrafh termatikou me xrhsh ksoap
		public class removeUUID extends AsyncTask<Void, Void, Void> {
			String res;

			// ##################################//
			@Override
			protected Void doInBackground(Void... params) {
				res = "Notdone";
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
				request.addProperty("username", username);
				request.addProperty("password", password);
				request.addProperty("uuid", uuidrem);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call("\"http://server/deletePC\"",
							envelope);
					SoapPrimitive response = (SoapPrimitive) envelope
							.getResponse();
					res = response.toString();
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				return null;
			}

			// ##################################//
			// An diagrafh to termatiko apo to server diegrapse to kai esy sthn
			// topikh sou bash
			@Override
			protected void onPostExecute(Void result) {
				if (res != null) {
					if (res.equals("true")) {
						DB.deleteTerminal(uuidrem);
					}
				}
			}

		}

		// ##################################//
	}

	// ##############################################################################################//
	//Thread pou periodika elegxei an uparxei sindesh me ton server kai se periptwshpou uparxei
	//lamvanei ta pio prosfata StatisticalReports ths vashs tou server
	public class UpdaterThread implements Runnable {
		// ##################################//
		private boolean running = true;

		@Override
		public void run() {
			while (running) {
				try {

					if (connection.isURLReachable(getApplicationContext())) {
						getfromServer servertask = new getfromServer();
						servertasks.add(servertask);
						servertask.execute();
					}
					Thread.sleep(30000);
				} catch (InterruptedException e) {

				}
			}
		}

		public void stop() {
			running = false;
		}

		// ##################################//
		
		//kalei th sinarthsh retrieveStatistics tou server gia lhpsh ths listas twn StatisticalReport
		public class getfromServer extends AsyncTask<Void, Void, Void> {
			// ##################################//
			@Override
			protected Void doInBackground(Void... params) {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call(
							"\"http://server/retrieveStatistics\"", envelope);
					
					response = (SoapObject) envelope.bodyIn;
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				return null;
			}

			// ##################################//
			@Override
			protected void onPostExecute(Void result) {

				String uuid = null;

				int i, j, k;
				SoapObject obj = response;
				if (obj != null) {
					for (j = 0; j < obj.getPropertyCount(); j++) {
						List<String[]> mpreports = new ArrayList<String[]>();
						List<String[]> mipreports = new ArrayList<String[]>();
						SoapObject obj2 = (SoapObject) obj.getProperty(j);
						for (i = 0; i < obj2.getPropertyCount(); i++) {
							PropertyInfo propinfo = new PropertyInfo();
							obj2.getPropertyInfo(i, propinfo);

							if (propinfo.getName().equals("pcUUID")) {
								uuid = new String(obj2.getProperty(i)
										.toString());
							} else if (propinfo.getName().equals("MPreport")) {
								SoapObject obj3 = (SoapObject) obj2
										.getProperty(i);
								String[] report = new String[obj3
										.getPropertyCount()];
								for (int l = 0; l < obj3.getPropertyCount(); l++) {
									String rep = new String(obj3.getProperty(l)
											.toString());
									report[l] = rep;
								}
								mpreports.add(report);
							} else if (propinfo.getName().equals("MIPreport")) {
								SoapObject obj3 = (SoapObject) obj2
										.getProperty(i);
								String[] report = new String[obj3
										.getPropertyCount()];
								for (int l = 0; l < obj3.getPropertyCount(); l++) {
									String rep = new String(obj3.getProperty(l)
											.toString());
									report[l] = rep;
								}
								mipreports.add(report);
							}
						}
						StatisticalReport report;
						String[][] mp = null;
						String[][] mip = null;
						if (!mpreports.isEmpty())
							mp = new String[mpreports.size()][4];
						if (!mipreports.isEmpty())
							mip = new String[mipreports.size()][4];
						if (!mpreports.isEmpty())
							for (k = 0; k < mpreports.size(); k++) {
								mp[k] = mpreports.get(k);
							}
						if (!mipreports.isEmpty())
							for (k = 0; k < mipreports.size(); k++) {
								mip[k] = mipreports.get(k);
							}
						report = new StatisticalReport(uuid, mp, mip);

						DB.addfromStatisticalReport(report);
					}
				}
				super.onPostExecute(result);
			}
			// ##################################//
		}
		// ##################################//
	}
	// ##############################################################################################//
}
