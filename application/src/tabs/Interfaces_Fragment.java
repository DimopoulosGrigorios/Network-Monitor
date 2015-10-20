package tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Interfaces_Fragment extends ListFragment {
	private TabActivity activity;
	private static final String ARG_SECTION_NUMBER = "section_number";

	// ##############################################################################################//
	//Fragment to opoio fuloksenei kathe fora ta interfaces tou ekatstotai terminal pou exei klickaristei 
	//sto prwto tab pou fenontai ta uuid
	//Gia analutikh parousiash tou kathe interface arkei to clickarisma se ena stoixeio ths listas
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity.setAdapter_interfaces(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, activity
						.getInterafacesList()));
		setListAdapter(activity.getAdapter_interfaces());
	}

	// ##############################################################################################//
	public static Interfaces_Fragment newInstance(int sectionNumber,
			TabActivity activity2) {
		Interfaces_Fragment fragment = new Interfaces_Fragment(activity2);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	// ##############################################################################################//
	public Interfaces_Fragment(TabActivity activity2) {
		activity = activity2;
	}

	// ##############################################################################################//
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemValue = (String) l.getItemAtPosition(position);
		String[] parts = itemValue.split("---");
		String Interface_name = parts[0];
		String Interface_ip = parts[1];
		Intent intent = new Intent(getActivity(), Malicious.class);
		intent.putExtra("Pc_Uuid", activity.getClicked_UUID());
		intent.putExtra("Interface_name", Interface_name);
		intent.putExtra("Interface_ip", Interface_ip);
		startActivity(intent);

	}
	// ##############################################################################################//
}
