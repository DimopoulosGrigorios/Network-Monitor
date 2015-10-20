package login;
import java.io.IOException;

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

import register.RegisterActivity;
import tabs.TabActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.R;

public class LoginActivity extends ActionBarActivity implements OnClickListener {
	// ##############################################################################################//
	private EditText username;
	private EditText password;
	private Button login;
	private Button register;
	private static final String NAMESPACE = "http://server/";
	private static String URL;
	private static final String METHOD_NAME = "login";
	private String name;
	private String pass;
	private String res;
	private Connectivity connection;
	private boolean connection_status;
	private String serverip = "null";

	// ##############################################################################################//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		URL = MyPreferences.getUserIp(getApplicationContext());
		setTitle("Network Monitor");
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.loginb);
		login.setOnClickListener(this);
		register = (Button) findViewById(R.id.regButton);
		register.setOnClickListener(this);
		connection = new Connectivity();

	}

	// ##############################################################################################//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loginmenu, menu);
		return true;
	}

	// ##############################################################################################//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.aboutm) {
			String aboutext = "Implemented and Deployed by: \n Grigorios Dimopoulos \n (1115201100198) & \n Panagiota Poulopoulou \n (1115201100110)";
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoginActivity.this);
			builder.setTitle("About Network Monitor");
			builder.setMessage(aboutext);
			final AlertDialog alert = builder.create();
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							alert.cancel();
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							alert.cancel();
						}
					});
			alert.show();
		}else if(id == R.id.serverURL){
			AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
			alert.setTitle("Server IP");
			alert.setMessage("Insert server IP below");
			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  serverip = input.getText().toString();
			  URL =  "http://"+serverip+":9999/Add/AdderService?wsdl";
				MyPreferences.setUserIp(LoginActivity.this, URL);
			  }
			});
			alert.show();
		}
		return super.onOptionsItemSelected(item);
	}

	// ##############################################################################################//
	@Override
	public void onClick(View v) {
		name = username.getText().toString().trim();
		pass = password.getText().toString().trim();

		if (v.getId() == R.id.loginb) {
			if (!name.trim().equals("") && !pass.trim().equals("")) {
				new attemptLogin().execute();
			} else if (name.trim().equals("") && !pass.trim().equals("")) {
				Toast.makeText(this, "Please enter username",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (!name.trim().equals("") && pass.trim().equals("")) {
				Toast.makeText(this, "Please enter password",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (name.trim().equals("") && pass.trim().equals("")) {
				Toast.makeText(this, "Please enter username and password",
						Toast.LENGTH_SHORT).show();
				return;
			}
		} else if (v.getId() == R.id.regButton) {
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
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
	//Kalesma sunarthshs login tou server gia ekxwrish twn stoixiwn tou xrhsth tou kinhtou sth vash tou server
	private class attemptLogin extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			if (connection.isURLReachable(getApplicationContext())) {
				connection_status = true;
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				PropertyInfo propertyInfo = new PropertyInfo();
				propertyInfo.setName("name");
				propertyInfo.setValue(name);
				propertyInfo.setType(String.class);
				request.addProperty(propertyInfo);
				propertyInfo = new PropertyInfo();
				propertyInfo.setName("pass");
				propertyInfo.setValue(pass);
				propertyInfo.setType(String.class);
				request.addProperty(propertyInfo);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call("\"http://server/login\"",
							envelope);
					SoapPrimitive response = (SoapPrimitive) envelope
							.getResponse();
					res = response.toString();
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			} else {
				connection_status = false;
			}
			return null;
		}

		// ##############################################################################################//

		// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			if (connection_status) {
				if (res.equals("1")) {
					// ACTIVITY GIA APLO USER
					MyPreferences.setUserName(LoginActivity.this, name);
					MyPreferences.setUserPass(LoginActivity.this, pass);
					MyPreferences.setUserAdmin(LoginActivity.this, "n");
					
					Intent intent = new Intent(getApplicationContext(),
							TabActivity.class);
					intent.putExtra("Username", name);
					intent.putExtra("Password", pass);
					intent.putExtra("Admin", "n");
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if (res.equals("2")) {
					MyPreferences.setUserName(LoginActivity.this, name);
					MyPreferences.setUserPass(LoginActivity.this, pass);
					MyPreferences.setUserAdmin(LoginActivity.this, "y");
				
					Intent intent = new Intent(getApplicationContext(),
							TabActivity.class);
					intent.putExtra("Username", name);
					intent.putExtra("Admin", "y");
					intent.putExtra("Password", pass);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if (res.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"Username does not exist", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getApplicationContext(),
							"The password you have entered is incorrect",
							Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			} else {
				Toast.makeText(
						getApplicationContext(),
						"No internet connection detected. Check your settings and retry.",
						Toast.LENGTH_SHORT).show();
			}
		}
		// ##############################################################################################//
	}
	// ##############################################################################################//
}
