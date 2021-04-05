package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class sessionManager {

    SharedPreferences session;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_DOB = "dob";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_TYPE = "role";
    public static final String KEY_PIC = "profileURL";
    public final String ADMIN_EMAIL = "admin@isquad.com";

    public sessionManager(Context _context) {
        context = _context;
        session = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = session.edit();
    }

    public void createLoginSession(String name, String gender, String dob, String phone, String email, String address, String country, String role, String purl) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, name);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_DOB, dob);
        if (isAdmin(email)) {
            editor.putString(KEY_TYPE, "Administrator");
        } else {
            editor.putString(KEY_TYPE, role);
        }
        editor.putString(KEY_PIC, purl);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_COUNTRY, country);
        editor.commit();
    }

    private boolean isAdmin(String email) {
        if (email.equals(ADMIN_EMAIL)) {
            return true;
        } else {
            return false;
        }
    }

    public void addCustom(String key, String name_price) {
        editor.putString(key, name_price);
        editor.commit();
    }

    public HashMap<String, String> currentBuild() {
        HashMap<String, String> buildData = new HashMap<String, String>();
        buildData.put(CustomBuild.KEY_MOTHERBOARD, session.getString(CustomBuild.KEY_MOTHERBOARD, null));
        buildData.put(CustomBuild.KEY_PROCESSOR, session.getString(CustomBuild.KEY_PROCESSOR, null));
        buildData.put(CustomBuild.KEY_RAM, session.getString(CustomBuild.KEY_RAM, null));
        buildData.put(CustomBuild.KEY_GRAPHICS, session.getString(CustomBuild.KEY_GRAPHICS, null));
        buildData.put(CustomBuild.KEY_STORAGE, session.getString(CustomBuild.KEY_STORAGE, null));
        buildData.put(CustomBuild.KEY_POWER, session.getString(CustomBuild.KEY_POWER, null));
        buildData.put(CustomBuild.KEY_COOLING, session.getString(CustomBuild.KEY_COOLING, null));
        buildData.put(CustomBuild.KEY_CASING, session.getString(CustomBuild.KEY_CASING, null));
        return buildData;
    }

    public HashMap<String, String> current_user() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_FULLNAME, session.getString(KEY_FULLNAME, null));
        userData.put(KEY_GENDER, session.getString(KEY_GENDER, null));
        userData.put(KEY_DOB, session.getString(KEY_DOB, null));
        userData.put(KEY_TYPE, session.getString(KEY_TYPE, null));
        userData.put(KEY_EMAIL, session.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE, session.getString(KEY_PHONE, null));
        userData.put(KEY_ADDRESS, session.getString(KEY_ADDRESS, null));
        userData.put(KEY_COUNTRY, session.getString(KEY_COUNTRY, null));
        userData.put(KEY_PIC, session.getString(KEY_PIC, null));
        return userData;
    }

    public boolean checkLogin() {
        if (session.getBoolean(IS_LOGIN, true)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }


}
