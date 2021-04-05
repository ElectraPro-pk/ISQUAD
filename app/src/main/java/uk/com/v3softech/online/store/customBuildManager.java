package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class customBuildManager {

    SharedPreferences session;
    SharedPreferences.Editor editor;
    Context context;

    public customBuildManager(Context _context) {
        context = _context;
        session = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = session.edit();
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

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

}
