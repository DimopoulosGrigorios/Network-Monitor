package main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connectivity {

	// ##############################################################################################//
	public Connectivity() {
	}

	// ##############################################################################################//
	public boolean isURLReachable(Context context) {
		String serverURL = MyPreferences.getUserIp(context);
	
		if(serverURL.equals("null"))
			return false;
		
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			try {
				java.net.URL url = new java.net.URL(serverURL);
				HttpURLConnection urlc = (HttpURLConnection) url
						.openConnection();
				urlc.setConnectTimeout(10 * 1000);
				urlc.connect();
				if (urlc.getResponseCode() == 200) {
					return true;
				} else {
					return false;
				}
			} catch (MalformedURLException e1) {
				return false;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	// ##############################################################################################//
	public boolean isConnected(Context context) {
		boolean hasWifi = false;
		boolean hasMobile = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					hasWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					hasMobile = true;
		}
		return hasWifi || hasMobile;
	}
	// ##############################################################################################//
	
}