package tabs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import main.MyPreferences;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.application.R;

public class Add_Malicious_Fragment extends Fragment implements OnClickListener {
	// ##############################################################################################//
	//Ayto einai to fragment pou xrhsimopoieitai gia na doume ta malicious pattern/ip ta opoia exei o server auth thn stigmh
	//kai na prosthesoume kai alla ths epiloghs mas
	private EditText text;
	private EditText text2;
	private TabActivity activity;
	private String ip;
	private String pattern;
	private int whichbutton;
	private static final String NAMESPACE = "http://server/";
	private static String URL;
	private static final String METHOD_NAME = "insertMaliciousPatterns";
	private String res;
	private static final String ARG_SECTION_NUMBER = "section_number";
	private String resp = null;
	private String resp2 = null;
	private ListView listviewmal=null;
	private ArrayAdapter<String> maliciousrep_adapter=null;
	private ArrayList<String> malrepLIST=new ArrayList<String>();
	private Integer pressedbutton=-1;
	// ##############################################################################################//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	// ##############################################################################################//
	public static Add_Malicious_Fragment newInstance(int sectionNumber,
			TabActivity activity2) {
		Add_Malicious_Fragment fragment = new Add_Malicious_Fragment(activity2);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	// ##############################################################################################//
	public Add_Malicious_Fragment(TabActivity activity2) {
		activity = activity2;
	}
	// ##############################################################################################//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_main,
				container, false);
		URL =  MyPreferences.getUserIp(activity.getApplicationContext());
		listviewmal=(ListView) rootView.findViewById(R.id.malList);
		maliciousrep_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, malrepLIST);
		listviewmal.setAdapter(maliciousrep_adapter);
		Button IPaddBUTTON = (Button) rootView.findViewById(R.id.ipaddbutton);
		text = (EditText) rootView.findViewById(R.id.iptext);
		text2 = (EditText) rootView.findViewById(R.id.patterntext);
		IPaddBUTTON.setOnClickListener(this);
		Button PATERNaddBUTTON = (Button) rootView.findViewById(R.id.paternaddbutton);
		PATERNaddBUTTON.setOnClickListener(this);
		Button PATTERNVIEWBUTTON = (Button) rootView.findViewById(R.id.patButton);
		PATTERNVIEWBUTTON.setOnClickListener(this);
		Button IPVIEWBUTTON = (Button) rootView.findViewById(R.id.ipButton);
		IPVIEWBUTTON.setOnClickListener(this);
		return rootView;
	}

	// ##############################################################################################//
	@Override
	public void onClick(View v) {
		ip = "";
		pattern = "";
		if (v.getId() == R.id.ipaddbutton) {
			whichbutton = R.id.ipaddbutton;
			ip = text.getText().toString().trim();
			pressedbutton=0;
			takeMalicious tm=new takeMalicious();
			tm.execute();
			try {
				tm.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (malrepLIST.contains(ip))
			{
				Toast.makeText(activity.getApplicationContext(),
						"Malicious Ip is already inserted", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (ip.trim().length() > 0) {
				Toast.makeText(activity.getApplicationContext(),
						"Inserting...Wait for Confirmation", Toast.LENGTH_SHORT)
						.show();
				new addMalicious().execute();
				text.setText("");
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Can't add an empty string.", Toast.LENGTH_SHORT)
						.show();

			}
		} else if (v.getId() == R.id.paternaddbutton) {
			whichbutton = R.id.paternaddbutton;
			pattern = text2.getText().toString().trim();
			pressedbutton=1;
			takeMalicious tm=new takeMalicious();
			tm.execute();
			try {
				tm.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (malrepLIST.contains(pattern))
			{
				Toast.makeText(activity.getApplicationContext(),
						"Malicious Pattern is already inserted", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (pattern.trim().length() > 0) {
				Toast.makeText(activity.getApplicationContext(),
						"Inserting...Wait for Confirmation", Toast.LENGTH_SHORT)
						.show();
				new addMalicious().execute();
				text2.setText("");
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Can't add an empty string.", Toast.LENGTH_SHORT)
						.show();

			}
		}
		else if(v.getId() == R.id.patButton)
		{
			pressedbutton=1;
			new takeMalicious().execute();
		}
		else if (v.getId() == R.id.ipButton)
		{
			pressedbutton=0;
			new takeMalicious().execute();
		}
	}

	// ##############################################################################################//
	//Klash AsyncTask gia epikoinwnia me ton athroisth kai prosthikh malicious stoixeiwn
	//An o server den einai energos tote ginetai apothikeush twn stoixeinw sthn bash
	private class addMalicious extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			if (activity.getConnection().isURLReachable(
					activity.getApplicationContext())) {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("username", activity.getUser());
				request.addProperty("password", activity.getPass());
				request.addProperty("maliciousIP", ip);
				request.addProperty("maliciousPatterns", pattern);
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
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				resp = "Done";
			} else {
				resp = "Database";
				if (whichbutton == R.id.paternaddbutton) {
					
					activity.getDB().addRecordtoTask_table(
							"AddMP",
							activity.getUser() + "#" + activity.getPass() + "#"
									+ pattern);
				} else if (whichbutton == R.id.ipaddbutton) {
					
					activity.getDB().addRecordtoTask_table(
							"AddMIP",
							activity.getUser() + "#" + activity.getPass() + "#"
									+ ip);
				}
			}
			return null;
		}

		// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (resp.equals("Database")) {
				Toast.makeText(
						activity.getApplicationContext(),
						"Can't access the network...Store your data in the database.",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Data inserted successfully!", Toast.LENGTH_SHORT)
						.show();

			}
		}

	}
	// ##############################################################################################//
	//Klash AsyncTask gia proskomish twn malicious ip kai pattern apo ton athroisth
	private class takeMalicious extends AsyncTask<Void, Void, Void> {
		private String[] parts2={};
		@Override
		protected Void doInBackground(Void... params) {
			if (activity.getConnection().isURLReachable(
					activity.getApplicationContext())) 
			{
				SoapObject request = new SoapObject(NAMESPACE, "retrieveMaliciousPatterns");
				request.addProperty("username", activity.getUser());
				request.addProperty("password", activity.getPass());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call("\"http://server/retrieveMaliciousPatterns\"",envelope);
					SoapPrimitive response = (SoapPrimitive) envelope
							.getResponse();
					res = response.toString();
					String[] parts = res.split("//");
					for (int i=0;i<parts.length;i++)
					{
						if (i==pressedbutton)
						{
							parts2=parts[i].split("#");
						}
					}
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				resp2="done";
			}
			else
			{
				resp2="notdone";
			}
			malrepLIST.clear();
			for (int i=0;i<parts2.length;i++)
			{
				malrepLIST.add(parts2[i]);
			}
			return null;
		}

		// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (resp2.equals("notdone")) {
				Toast.makeText(
						activity.getApplicationContext(),
						"Can't access the network...",
						Toast.LENGTH_SHORT).show();
			}
			malrepLIST.clear();
			maliciousrep_adapter.notifyDataSetChanged();
			for (int i=0;i<parts2.length;i++)
			{
				malrepLIST.add(parts2[i]);
				maliciousrep_adapter.notifyDataSetChanged();
			}
		}

	}
	// ##############################################################################################//

}