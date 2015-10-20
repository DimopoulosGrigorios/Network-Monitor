package tabs;

import java.util.ArrayList;
import android.database.smartphoneDB;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.application.R;

public class Malicious extends ActionBarActivity {
	// ##############################################################################################//
	//Activity to opoio ginetai pop up se periptwsh pou paththei kapoio interface gia analutikh parousiash
	//Twn statistikwn
	private String Pc_Uuid;
	private String Interface_name;
	private String Interface_ip;
	private smartphoneDB DB = new smartphoneDB(this);
	private ArrayList<ArrayList<String>> MIP;
	private ArrayList<ArrayList<String>> MP;
	public ArrayList<String> Currentmalicious;
	private ArrayAdapter<String> adapter_Currentmalicious;
	private Page_Adapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private String selected = "nothing";
	// ##############################################################################################//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		mSectionsPagerAdapter = new Page_Adapter(getSupportFragmentManager(),
				this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		String Pc_Uuid = getIntent().getStringExtra("Pc_Uuid");

		String Interface_ip = getIntent().getStringExtra("Interface_ip");
		MIP = DB.selectfromMalicious_Ip_Historywithip(
				Pc_Uuid, Interface_ip);
		MP = DB.selectfromMalicious_Pattern_Historywithip(
				Pc_Uuid, Interface_ip);
		Currentmalicious = new ArrayList<String>();
	}

	// ##############################################################################################//
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu2, menu);
		return true;
	}

	// ##############################################################################################//
	//H epilogh gia emfanish malicious ip h malicious pattern ginetai apo to menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		clearCurrentmalicious();
		if (id == R.id.mipreport) {
			selected = "mip";
			if (MIP != null) {
				for (ArrayList<String> item2 : DB.takemostrecent(MIP)) {
					addCurrentmalicious(item2.get(0));
				}
			}
		} else if (id == R.id.mpreport) {
			selected = "mp";
			if (MP != null) {
				for (ArrayList<String> item2 : DB.takemostrecent(MP)) {
					addCurrentmalicious(item2.get(0));
				}
			}
		}
		return super.onOptionsItemSelected(item);
	}

	// ##############################################################################################//
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			clearCurrentmalicious();
			DB.close();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// ##############################################################################################//
	@Override
	protected void onStop() {
		clearCurrentmalicious();
		super.onStop();
		finish();
	}

	// ##############################################################################################//
	@Override
	protected void onPause() {
		clearCurrentmalicious();
		super.onPause();
		finish();
	}

	// ##############################################################################################//
	public ArrayList<String> getCurrentmalicious() {
		return Currentmalicious;
	}

	// ##############################################################################################//
	public void setCurrentmalicious(ArrayList<String> currentmalicious) {
		this.Currentmalicious = currentmalicious;
		adapter_Currentmalicious.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void addCurrentmalicious(String newelem) {
		Currentmalicious.add(newelem);
		adapter_Currentmalicious.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public void removeCurrentmalicious(String elem) {
		Currentmalicious.remove(elem);
		adapter_Currentmalicious.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public String getCurrentmalicious(Integer index) {
		return Currentmalicious.get(index);

	}

	// ##############################################################################################//
	public void clearCurrentmalicious() {
		Currentmalicious.clear();
		adapter_Currentmalicious.notifyDataSetChanged();
	}

	// ##############################################################################################//
	public ArrayList<ArrayList<String>> getMIP() {
		return MIP;
	}

	// ##############################################################################################//
	public void setMIP(ArrayList<ArrayList<String>> mIP) {
		MIP = mIP;
	}

	// ##############################################################################################//
	public void addMIP(ArrayList<String> newelem) {
		MIP.add(newelem);
	}

	// ##############################################################################################//
	public void removeMIP(ArrayList<String> elem) {
		MIP.remove(elem);
	}

	// ##############################################################################################//
	public ArrayList<String> getMIP(Integer index) {
		return MIP.get(index);

	}

	// ##############################################################################################//
	public void clearMIP() {
		MIP.clear();
	}

	// ##############################################################################################//
	public ArrayList<ArrayList<String>> getMP() {
		return MP;
	}

	// ##############################################################################################//
	public void setMP(ArrayList<ArrayList<String>> mP) {
		MP = mP;
	}

	// ##############################################################################################//
	public void addMP(ArrayList<String> newelem) {
		MP.add(newelem);
	}

	// ##############################################################################################//
	public void removeMP(ArrayList<String> elem) {
		MP.remove(elem);
	}

	// ##############################################################################################//
	public ArrayList<String> getMP(Integer index) {
		return MP.get(index);

	}

	// ##############################################################################################//
	public void clearMP() {
		MIP.clear();
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
	public String getPc_Uuid() {
		return Pc_Uuid;
	}

	// ##############################################################################################//
	public void setPc_Uuid(String pc_Uuid) {
		Pc_Uuid = pc_Uuid;
	}

	// ##############################################################################################//
	public String getInterface_name() {
		return Interface_name;
	}

	// ##############################################################################################//
	public void setInterface_name(String interface_name) {
		Interface_name = interface_name;
	}

	// ##############################################################################################//
	public String getInterface_ip() {
		return Interface_ip;
	}

	// ##############################################################################################//
	public void setInterface_ip(String interface_ip) {
		Interface_ip = interface_ip;
	}

	// ##############################################################################################//
	public Page_Adapter getmSectionsPagerAdapter() {
		return mSectionsPagerAdapter;
	}

	// ##############################################################################################//
	public void setmSectionsPagerAdapter(Page_Adapter mSectionsPagerAdapter) {
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
	public ArrayAdapter<String> getAdapter_Currentmalicious() {
		return adapter_Currentmalicious;
	}

	// ##############################################################################################//
	public void setAdapter_Currentmalicious(
			ArrayAdapter<String> adapter_Currentmalicious) {
		this.adapter_Currentmalicious = adapter_Currentmalicious;
	}

	// ##############################################################################################//
	public String getSelected() {
		return selected;
	}

	// ##############################################################################################//
	public void setSelected(String selected) {
		this.selected = selected;
	}
	// ##############################################################################################//
}
