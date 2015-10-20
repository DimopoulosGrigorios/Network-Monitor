package tabs;

import java.util.Locale;

import com.application.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Page_Adapter extends FragmentPagerAdapter {
	// ##############################################################################################//
	//FragmentPager Adapter gia to fragment pou filoksenei h Malicious Activity
	private Malicious activity;
	// ##############################################################################################//
	public Page_Adapter(FragmentManager fm, Malicious act) {
		super(fm);
		activity = act;
	}

	// ##############################################################################################//
	@Override
	public Fragment getItem(int position) {
		return MaliciousFragment.newInstance(position + 1, activity);
	}

	// ##############################################################################################//
	@Override
	public int getCount() {
		return 1;
	}

	// ##############################################################################################//
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		return activity.getString(R.string.malicious).toUpperCase(l);
	}
	// ##############################################################################################//
}
