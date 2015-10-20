package tabs;
import java.util.Locale;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import com.application.R;

public class Pages_Adapter extends FragmentPagerAdapter {
	// ##############################################################################################//
	//FragmentPager Adapter gia to basiko swipe view 
	TabActivity activity;
	// ##############################################################################################//
	public Pages_Adapter(FragmentManager fm, TabActivity tabActivity) {
		super(fm);
		activity = tabActivity;
	}

	// ##############################################################################################//
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return Uuids_Fragment.newInstance(position + 1, activity);
		case 1:
			return Interfaces_Fragment.newInstance(position + 1, activity);
		case 2:
			return Add_Malicious_Fragment.newInstance(position + 1, activity);
		case 3:
			return Remove_Terminals_Fragment
					.newInstance(position + 1, activity);
		}
		return null;
	}

	// ##############################################################################################//
	@Override
	public int getCount() {
		if (activity.isADMIN())
			return 4;
		else
			return 2;
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

	// ##############################################################################################//
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return activity.getString(R.string.Uuids).toUpperCase(l);
		case 1:
			return activity.getString(R.string.Interfaces).toUpperCase(l);
		case 2:
			return activity.getString(R.string.add_mal).toUpperCase(l);
		case 3:
			return activity.getString(R.string.remove_pc).toUpperCase(l);
		}
		return null;
	}
	// ##############################################################################################//
}
