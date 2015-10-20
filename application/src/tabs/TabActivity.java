package tabs;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import main.Connectivity;
import main.DataService;
import main.MainActivity;
import main.MyPreferences;
import android.content.Intent;
import android.database.smartphoneDB;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.application.R;

public class TabActivity extends ActionBarActivity {
	// ##############################################################################################//
	//Basikh sunarths pou fuloksenei kai dhmiourgei ta fragments pou fuloksenoun ta tabs
	//Xrhsh ths synarthshs ws kanali epikoinwnia metaksi twn fragments
	private String User;
	private String Pass;
	private boolean connection_status = false;
	private String clicked_UUID;
	private smartphoneDB DB;
	private boolean enablefiltering = false;
	private ArrayAdapter<String> adapter_uuid_remove = null;
	private Pages_Adapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private ArrayList<String> UuidsList = new ArrayList<String>();
	private ArrayList<String> InterafacesList = new ArrayList<String>();
	private ArrayAdapter<String> adapter_interfaces;
	private ArrayAdapter<String> adapter_uuid;
	private ArrayList<String> alluuids = new ArrayList<String>();
	private boolean isADMIN;
	private static final String NAMESPACE = "http://server/";
	private static final String METHOD_NAME1 = "logout";
	private Connectivity connection;
	private String response = "";
	private String URL = "";
	private Intent serviceIntent;
	// ##############################################################################################//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		serviceIntent = new Intent(this, DataService.class);
		startService(serviceIntent);
		setContentView(R.layout.activity_tab);
		User = getIntent().getStringExtra("Username");
		Pass = getIntent().getStringExtra("Password");
		URL = MyPreferences.getUserIp(getApplicationContext());
		setTitle("Network Monitor");
		isADMIN = getIntent().getStringExtra("Admin").equals("y");
		mSectionsPagerAdapter = new Pages_Adapter(getSupportFragmentManager(),
				this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mSectionsPagerAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mSectionsPagerAdapter.notifyDataSetChanged();
		connection = new Connectivity();
		setDB(new smartphoneDB(this));
		Intent serviceIntent = new Intent(this, DataService.class);
		startService(serviceIntent);
		alluuids = new ArrayList<String>();
		setAlluuids(DB.selectfromAssociated_Devicesall());
		ArrayList<String> uuid = DB.selectfromAssociated_Devicesall();
		if (uuid.size() > 0) {
			for (String item2 : uuid) {
				addUuidsList(item2);
			}
		}
	}

	// ##############################################################################################//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ##############################################################################################//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_filtering) {
			clearUuidsList();
			enablefiltering = !(enablefiltering);
			ArrayList<String> useruuid = DB.selectfromAssociated_Devices(User);
			ArrayList<String> uuid = DB.selectfromAssociated_Devicesall();
			if (enablefiltering) {
				if (useruuid.size() > 0) {
					for (String item2 : useruuid) {

						addUuidsList(item2);
					}
				}
			} else {
				if (uuid.size() > 0) {
					for (String item2 : uuid) {

						addUuidsList(item2);
					}
				}
			}
		} else if (id == R.id.logout) {
			stopService(serviceIntent);
			new logOut().execute();
			return true;
		} else if (id == R.id.refresh) {
			setAlluuids(DB.selectfromAssociated_Devicesall());
			adapter_uuid_remove.notifyDataSetChanged();
		} 
		return super.onOptionsItemSelected(item);
	}
	// ##############################################################################################//
	//Klhsh ths synarthshs logout tou server mesw ksoap.
	private class logOut extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			if (connection.isURLReachable(getApplicationContext())) {
				connection_status = true;
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
				request.addProperty("username", User);
				request.addProperty("password", Pass);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call("\"http://server/logout\"",
							envelope);
					response = envelope.getResponse().toString();
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			if (connection_status) {
				if (response.equals("false"))
					Toast.makeText(getApplicationContext(),
							"Error while logging out", Toast.LENGTH_SHORT)
							.show();
				else if (response.equals("true")) {
					MyPreferences.clearUserName(TabActivity.this);
					MyPreferences.clearUserPass(TabActivity.this);
					MyPreferences.clearUserAdmin(TabActivity.this);
					MyPreferences.clearUserIp(TabActivity.this);
					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					clearInterafacesList();
					clearUuidsList();
					startActivity(intent);
					finish();

				}
			} else {
				Toast.makeText(getApplicationContext(),
						"No server connection.", Toast.LENGTH_SHORT).show();
			}
			DB.close();
			super.onPostExecute(result);
		}
		// ##################################//
	}

	// ##############################################################################################//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			clearInterafacesList();
			clearUuidsList();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// ##############################################################################################//
	public String getClicked_UUID() {
		return clicked_UUID;
	}

	// ##############################################################################################//
	public void setClicked_UUID(String clicked_UUID) {
		this.clicked_UUID = clicked_UUID;
	}

	// ##############################################################################################//
	public String getUser() {
		return User;
	}

	// ##############################################################################################//
	public void setUser(String user) {
		User = user;
	}

	// ##############################################################################################//
	public ArrayList<String> getUuidsList() {
		return UuidsList;
	}

	// ##############################################################################################//
	public void setUuidsList(ArrayList<String> uuidsList) {
		UuidsList = uuidsList;
		adapter_uuid.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void addUuidsList(String newelem) {
		UuidsList.add(newelem);
		if (adapter_uuid == null) {
			this.setAdapter_uuid(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, this.getUuidsList()));
		}
		adapter_uuid.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void removeUuidsList(String elem) {
		UuidsList.remove(elem);
		adapter_uuid.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public String getUuidsList(Integer index) {
		return UuidsList.get(index);
	}

	// ##############################################################################################//
	public void clearUuidsList() {
		UuidsList.clear();
		adapter_uuid.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public ArrayList<String> getInterafacesList() {
		return InterafacesList;
	}

	// ##############################################################################################//
	public void setInterafacesList(ArrayList<String> interafacesList) {
		InterafacesList = interafacesList;
		adapter_interfaces.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void addInterafacesList(String newelem) {
		InterafacesList.add(newelem);
		adapter_interfaces.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void removeInterafacesList(String elem) {
		InterafacesList.remove(elem);
		adapter_interfaces.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public String getInterafacesList(Integer index) {
		return InterafacesList.get(index);
	}

	// ##############################################################################################//
	public void clearInterafacesList() {
		InterafacesList.clear();
		adapter_interfaces.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public ArrayList<String> getAlluuids() {
		alluuids = DB.selectfromAssociated_Devicesall();
		return alluuids;
	}

	// ##############################################################################################//
	public void setAlluuids(ArrayList<String> alluuids) {
		if (adapter_uuid_remove == null) {
			setAdapter_uuid_remove(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, getAlluuids()));
		}
		clearAlluuids();
		for (String uuiditem : alluuids) {
			addAlluuids(uuiditem);
		}
	}

	// ##############################################################################################//
	public void addAlluuids(String newelem) {
		alluuids.add(newelem);
		adapter_uuid_remove.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void removeAlluuids(String elem) {
		alluuids.remove(elem);
		adapter_uuid_remove.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public String getAlluuids(Integer index) {
		return alluuids.get(index);

	}

	// ##############################################################################################//
	public void clearAlluuids() {
		alluuids.clear();
		adapter_uuid_remove.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public ArrayAdapter<String> getAdapter_interfaces() {
		return adapter_interfaces;
	}

	// ##############################################################################################//
	public boolean isEnablefiltering() {
		return enablefiltering;
	}

	// ##############################################################################################//
	public void setEnablefiltering(boolean enablefiltering) {
		this.enablefiltering = enablefiltering;
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
	public void setAdapter_interfaces(ArrayAdapter<String> adapter_interfaces) {
		this.adapter_interfaces = adapter_interfaces;
	}

	// ##############################################################################################//
	public ArrayAdapter<String> getAdapter_uuid() {
		return adapter_uuid;
	}

	// ##############################################################################################//
	public void setAdapter_uuid(ArrayAdapter<String> adapter_uuid) {
		this.adapter_uuid = adapter_uuid;
	}

	// ##############################################################################################//
	public ArrayAdapter<String> getAdapter_uuid_remove() {
		return adapter_uuid_remove;
	}

	// ##############################################################################################//
	public void setAdapter_uuid_remove(ArrayAdapter<String> adapter_uuid_remove) {
		this.adapter_uuid_remove = adapter_uuid_remove;
	}

	// ##############################################################################################//
	public Pages_Adapter getmSectionsPagerAdapter() {
		return mSectionsPagerAdapter;
	}

	// ##############################################################################################//
	public void setmSectionsPagerAdapter(Pages_Adapter mSectionsPagerAdapter) {
		this.mSectionsPagerAdapter = mSectionsPagerAdapter;
	}

	// ##############################################################################################//
	public ViewPager getmViewPager() {
		return mViewPager;
	}

	// ##############################################################################################//
	public void setmViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
	}

	// ##############################################################################################//
	public boolean isADMIN() {
		return isADMIN;
	}

	// ##############################################################################################//
	public void setADMIN(boolean isADMIN) {
		this.isADMIN = isADMIN;
	}

	// ##############################################################################################//
	public String getPass() {
		return Pass;
	}

	// ##############################################################################################//
	public void setPass(String pass) {
		Pass = pass;
	}

	// ##############################################################################################//
	public Connectivity getConnection() {
		return connection;
	}

	// ##############################################################################################//
	public void setConnection(Connectivity connection) {
		this.connection = connection;
	}
	// ##############################################################################################//
}
