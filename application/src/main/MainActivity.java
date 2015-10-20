package main;

import login.LoginActivity;
import tabs.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.application.R;

public class MainActivity extends ActionBarActivity {
	// ##############################################################################################//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Network Monitor");
		if (MyPreferences.getUserName(MainActivity.this).length() == 0) {
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Intent intent = new Intent(getApplicationContext(),
					TabActivity.class);
			
			Log.d(MyPreferences.getUserName(MainActivity.this),
					MyPreferences.getUserAdmin(MainActivity.this));
			intent.putExtra("Username",
					MyPreferences.getUserName(MainActivity.this));
			intent.putExtra("Admin",
					MyPreferences.getUserAdmin(MainActivity.this));
			intent.putExtra("Password",
					MyPreferences.getUserPass(MainActivity.this));
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
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
		if (id == R.id.logout) {
			MyPreferences.clearUserName(MainActivity.this);
			MyPreferences.clearUserPass(MainActivity.this);
			MyPreferences.clearUserAdmin(MainActivity.this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	// ##############################################################################################//
}
