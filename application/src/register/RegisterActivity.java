package register;

import java.io.IOException;
import java.util.ArrayList;
import main.Connectivity;
import main.MyPreferences;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import tabs.TabActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.AvailableNodes;
import android.database.smartphoneDB;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.application.R;
public class RegisterActivity extends ActionBarActivity implements
		OnClickListener {
	// ##############################################################################################//
	private Button reg;
	private EditText username;
	private EditText password1;
	private EditText password2;
	private ArrayList<String> s = new ArrayList<String>();
	private AvailableNodes nodes = new AvailableNodes();
	private Button insert;
	private EditText uuids;
	private TextView stats;
	private static final String NAMESPACE = "http://server/";
	private static String URL ;
	private static final String METHOD_NAME = "register_android";
	private String name;
	private String pass1;
	private String resp;
	private smartphoneDB DB;
	private Connectivity connection;
	private String serverip;

	// ##############################################################################################//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_register);
		URL =  MyPreferences.getUserIp(getApplicationContext());
		setTitle("Network Monitor");
		insert = (Button) findViewById(R.id.insertbutton);
		stats = (TextView) findViewById(R.id.status);
		uuids = (EditText) findViewById(R.id.inserts);
		reg = (Button) findViewById(R.id.regb);
		username = (EditText) findViewById(R.id.username);
		password1 = (EditText) findViewById(R.id.pass);
		password2 = (EditText) findViewById(R.id.pass2);
		reg.setOnClickListener(this);
		setDB(new smartphoneDB(this));
		connection = new Connectivity();
		insert.setOnClickListener(this);
	}

	// ##############################################################################################//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	// ##############################################################################################//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.serverURL){
			AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
			alert.setTitle("Server IP");
			alert.setMessage("Insert server IP below");
			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  serverip = input.getText().toString();
			  URL =  "http://"+serverip+":9999/Add/AdderService?wsdl";
			  MyPreferences.setUserIp(RegisterActivity.this, URL);
			  }
			});
			alert.show();
		}
		return super.onOptionsItemSelected(item);
	}

	// ##############################################################################################//
	@Override
	public void onClick(View v) {
		name = username.getText().toString();
		pass1 = password1.getText().toString();
		String pass2 = password2.getText().toString();
		if (v.getId() == R.id.regb) {
			if (!pass1.trim().equals(pass2.trim())) {
				Toast.makeText(this, "Passwords don't match",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (name.trim().equals("") && !pass1.trim().equals("")
					&& !pass2.trim().equals("")) {
				Toast.makeText(this, "Please enter username",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (!name.trim().equals("")
					&& (pass1.trim().equals("") || pass2.trim().equals(""))) {
				Toast.makeText(this, "Please enter password",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (name.trim().equals("")
					&& (pass1.trim().equals("") || pass2.trim().equals(""))) {
				Toast.makeText(this, "Please enter username and password",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				new sendNodes().execute();
			}
		} else if (v.getId() == R.id.insertbutton) {
			String u = uuids.getText().toString();
			stats.setText("Inserted " + u);
			s.add(u);
			uuids.setText("");
		}
	}

	// ##############################################################################################//
	public smartphoneDB getDB() {
		return DB;
	}
	// ##############################################################################################//

	@Override
	protected void onDestroy() {
		DB.close();
		super.onDestroy();
	}

	// ##############################################################################################//
	public void setDB(smartphoneDB dB) {
		DB = dB;
	}

	// ##############################################################################################//
	private class sendNodes extends AsyncTask<Void, Void, Void> {
		private String conres = null;

		@Override
		protected Void doInBackground(Void... params) {
			if (connection.isURLReachable(getApplicationContext())) {
				conres = "Reachable";
				AvailableNodes an = new AvailableNodes();
				an.setNodes(s);
				DB.addfromAvailableNodes(an, name);
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				PropertyInfo propertyInfo = new PropertyInfo();
				propertyInfo.setName("name");
				propertyInfo.setValue(name);
				propertyInfo.setType(String.class);
				request.addProperty(propertyInfo);
				propertyInfo = new PropertyInfo();
				propertyInfo.setName("pass");
				propertyInfo.setValue(pass1);
				propertyInfo.setType(String.class);
				request.addProperty(propertyInfo);
				propertyInfo = new PropertyInfo();
				nodes.setNodes(s);
				propertyInfo.setName("nodes");
				propertyInfo.setValue(nodes);
				propertyInfo.setType(nodes.getClass());
				request.addProperty(propertyInfo);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.addMapping(NAMESPACE, "AvailableNodes",
						new AvailableNodes().getClass());
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call(
							"\"http://server/register_android\"", envelope);
					SoapPrimitive response = (SoapPrimitive) envelope
							.getResponse();
					resp = response.toString();
					
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			} else {
				conres = "NotReachable";
			}
			return null;
		}

		// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			if (conres.equals("Reachable")) {
				if (resp.equals("true")) {
					Intent intent = new Intent(getApplicationContext(),
							TabActivity.class);
					intent.putExtra("Username", name);
					intent.putExtra("Password", pass1);
					intent.putExtra("Admin", "n");
					MyPreferences.setUserName(RegisterActivity.this, name);
					MyPreferences.setUserPass(RegisterActivity.this, pass1);
					MyPreferences.setUserAdmin(RegisterActivity.this, "n");
					
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"Username already exists", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"The Server is down.Try again later.",
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}
		// ##############################################################################################//
	}
	// ##############################################################################################//
}
