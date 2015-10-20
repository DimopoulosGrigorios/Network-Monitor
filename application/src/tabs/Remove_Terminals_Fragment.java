package tabs;

import java.io.IOException;
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
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Remove_Terminals_Fragment extends ListFragment {
	// ##############################################################################################//
	//Fragment pou fuloksenei to tab pou diagrafoume termatika
	private TabActivity activity;
	private static final String NAMESPACE = "http://server/";
	private static String URL;
	private static final String METHOD_NAME = "deletePC";
	private static final String ARG_SECTION_NUMBER = "section_number";
	private String rem_uuid;
	// ##############################################################################################//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity.setAdapter_uuid_remove(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, activity.getAlluuids()));
		URL =  MyPreferences.getUserIp(activity.getApplicationContext());
		setListAdapter(activity.getAdapter_uuid_remove());
	}
	// ##############################################################################################//
	public static Remove_Terminals_Fragment newInstance(int sectionNumber,
			TabActivity act) {
		Remove_Terminals_Fragment fragment = new Remove_Terminals_Fragment(act);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	// ##############################################################################################//
	public Remove_Terminals_Fragment(TabActivity act) {
		activity = act;
	}

	// ##############################################################################################//
	//Otan klickaroume epanw se ena stoixeio ths listas to termatiko diagrafetai h se diaforetikh periptwsh to 
	//aithma apothikeuetai sthn bash gia melontikh apostolh
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemValue = (String) l.getItemAtPosition(position);
		rem_uuid = itemValue;
		Toast.makeText(activity.getApplicationContext(),
				"Deleting Terminal...Wait for Confirmation.",
				Toast.LENGTH_SHORT).show();
		new removeUUID().execute();
	}
	// ##############################################################################################//
	private class removeUUID extends AsyncTask<Void, Void, Void> {
		private String res = "false";
		private String event = "NotDone";

		@Override
		protected Void doInBackground(Void... params) {
			if (activity.getConnection().isURLReachable(
					activity.getApplicationContext())) {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("uuid", rem_uuid);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(request);
				final HttpTransportSE androidHttpTransport = new HttpTransportSE(
						URL);
				androidHttpTransport.debug = true;
				try {
					androidHttpTransport.call(
							"\"http://server/deletePC\"",
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
				event = "done";
			} else {
				activity.getDB().addRecordtoTask_table("Delete", rem_uuid);
			}
			return null;
		}

		// ##############################################################################################//
		@Override
		protected void onPostExecute(Void result) {
			if (event.equals("done")) {
				if (res.equals("true")) {
					activity.removeAlluuids(rem_uuid);
					activity.getDB().deleteTerminal(rem_uuid);
					activity.clearUuidsList();
					activity.clearInterafacesList();
					Toast.makeText(activity.getApplicationContext(),
							"Terminal deleted successfully.",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(activity.getApplicationContext(),
							"Unable to delete Terminal.",
							Toast.LENGTH_SHORT).show();

				}
			} else {
				activity.removeAlluuids(rem_uuid);
				activity.clearUuidsList();
				activity.clearInterafacesList();
				Toast.makeText(
						activity.getApplicationContext(),
						"Can't access the network... Storing your data in the database.",
						Toast.LENGTH_SHORT).show();
			}
		}
		// ##############################################################################################//
	}
	// ##############################################################################################//
}
