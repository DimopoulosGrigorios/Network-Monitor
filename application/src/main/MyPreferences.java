package main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyPreferences {
	// ##############################################################################################//
	static final String PREF_USER_NAME = "username";
	static final String PREF_USER_PASS = "password";
	static final String PREF_USER_ADMIN = "admin";
	static final String PREF_USER_IP = "serverip";

	// ##############################################################################################//
	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	// ##############################################################################################//

	public static void setUserIp(Context ctx, String userIp) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_IP, userIp);
		editor.commit();
	}

	// ##############################################################################################//
	public static String getUserIp(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_IP, "");
	}
	// ##############################################################################################//
	public static void clearUserIp(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.clear();
		editor.commit();
	}
	// ##############################################################################################//
	public static void setUserPass(Context ctx, String userPass) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_PASS, userPass);
		editor.commit();
	}

	// ##############################################################################################//
	public static String getUserPass(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
	}

	// ##############################################################################################//
	public static void clearUserPass(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.clear();
		editor.commit();
	}

	// ##############################################################################################//
	public static void setUserName(Context ctx, String userName) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_NAME, userName);
		editor.commit();
	}

	// ##############################################################################################//
	public static String getUserName(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
	}

	// ##############################################################################################//
	public static void clearUserName(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.clear();
		editor.commit();
	}

	// ##############################################################################################//
	public static void setUserAdmin(Context ctx, String userAdmin) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_USER_ADMIN, userAdmin);
		editor.commit();
	}

	// ##############################################################################################//
	public static String getUserAdmin(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_USER_ADMIN, "");
	}

	// ##############################################################################################//
	public static void clearUserAdmin(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.clear();
		editor.commit();
	}
	// ##############################################################################################//
}
