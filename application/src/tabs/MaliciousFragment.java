package tabs;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MaliciousFragment extends ListFragment {
	// ##############################################################################################//
	//Fragment pou ulopoieiei thn leitourgikothta tou Malicious Activity 
	private Malicious activity;
	private static final String ARG_SECTION_NUMBER = "section_number";
	// ##############################################################################################//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity.setAdapter_Currentmalicious(new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, activity
						.getCurrentmalicious()));
		setListAdapter(activity.getAdapter_Currentmalicious());
	}

	// ##############################################################################################//
	public static MaliciousFragment newInstance(int sectionNumber, Malicious act) {
		MaliciousFragment fragment = new MaliciousFragment(act);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	// ##############################################################################################//
	public MaliciousFragment(Malicious act) {
		activity = act;
	}

	// ##############################################################################################//
	//Gia na doume thn syxnothta kapoiou malicious arkei na epileksoume to stoixeio sthn lista
	//opou meta tha ginei pop ena bubble kai tha mas upodiksei thn pio prosfath frequency pou einai 
	//enhmerh h efarmogh
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemValue = (String) l.getItemAtPosition(position);
		String frequency = "no_frequency";
		if (activity.getSelected().equals("mip")) {
			if (activity.getMIP() != null) {
				for (ArrayList<String> item : activity.getMIP()) {
					if (item.get(0).equals(itemValue)) {
						frequency = item.get(1);
						break;
					}
				}
			}
		} else {
			if (activity.getMP() != null) {
				for (ArrayList<String> item : activity.getMP()) {
					
					if (item.get(0).equals(itemValue)) {
						frequency = item.get(1);
						break;
					}
				}
			}
		}
		Toast.makeText(getActivity().getApplicationContext(),
				"Frequency=" + frequency, Toast.LENGTH_LONG).show();
	}
	// ##############################################################################################//
}