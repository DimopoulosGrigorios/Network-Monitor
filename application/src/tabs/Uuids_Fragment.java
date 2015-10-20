package tabs;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Uuids_Fragment extends ListFragment {
	// ##############################################################################################//
	//Fragment to opoio fuloksenei to prwto tab me ta uuid
	//Gia na enhmerwthei to deutero tab tha prepei na klikaristei kapoio uuid se auto to tab
	//To filtering energopoihte apo to menu
	private TabActivity activity;
	private static final String ARG_SECTION_NUMBER = "section_number";

	// ##############################################################################################//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (activity.getAdapter_uuid()==null)
		{
			activity.setAdapter_uuid(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, activity.getUuidsList()));
		}
		setListAdapter(activity.getAdapter_uuid());
	}

	// ##############################################################################################//
	public static Uuids_Fragment newInstance(int sectionNumber, TabActivity act) {
		Uuids_Fragment fragment = new Uuids_Fragment(act);
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	// ##############################################################################################//
	public Uuids_Fragment(TabActivity act) {
		activity = act;
	}

	// ##############################################################################################//
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemValue = (String) l.getItemAtPosition(position);
		activity.clearInterafacesList();
		activity.setClicked_UUID(itemValue);
		ArrayList<ArrayList<String>> interfaces = activity.getDB()
				.selectfromClient_Interfacesall(itemValue);
		if (interfaces != null) {
			for (ArrayList<String> item : interfaces) {
				activity.addInterafacesList(item.get(0) + "---" + item.get(1)
						+ "");
			}
		}
	}
	// ##############################################################################################//
}
