package uk.com.v3softech.online.store;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.facebook.internal.LockOnGetVariable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class cartManager {

    SQLiteDatabase db;
    Context context;
    popups p;
    Cursor cursor;

    public cartManager(Context _context) {
        this.context = _context;
        p = new popups(_context);
        try {
            db = context.openOrCreateDatabase(CONSTANTS.DB_NAME, Context.MODE_PRIVATE, null);
            db.execSQL(CONSTANTS.CART_TABLE_MAKING_QUERY);
        } catch (Exception e) {
            p.message(e.getMessage());
        }

    }

    public boolean InsertItem(String name, String price, String quantity) {
        try {
            String QUERY = "INSERT INTO " + CONSTANTS.CART_TABLE_NAME + "(" + CONSTANTS.CART_ITEM_NAME + "," + CONSTANTS.CART_ITEM_PRICE + "," + CONSTANTS.CART_ITEM_QUANTITY + ") VALUES('" + name + "','" + price + "','" + quantity + "')";
            db.execSQL(QUERY);
            return true;
        } catch (Exception e) {
            Log.d("E34", e.getMessage());
            return false;
        }
    }

    public boolean RemoveItem(String name) {
        try {
            String QUERY_SELECT = "SELECT * FROM " + CONSTANTS.CART_TABLE_NAME + " WHERE " + CONSTANTS.CART_ITEM_NAME + " = '" + name + "' LIMIT 1";
            cursor = db.rawQuery(QUERY_SELECT, null);
            if(cursor.getCount() > 0) {
                Log.d("I51", cursor.getString(1) + "");
                String QUERY = "DELETE FROM " + CONSTANTS.CART_TABLE_NAME + " WHERE " + CONSTANTS.CART_ITEM_ID + " = " + cursor.getInt(0);
                db.execSQL(QUERY);
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            Log.d("E46", e.getMessage());
            return false;
        }
    }

    public boolean empty() {
        try {
            String QUERY = "SELECT * FROM " + CONSTANTS.CART_TABLE_NAME;
            cursor = db.rawQuery(QUERY, null);
            if (cursor.getCount() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            Log.d("E60", e.getMessage());
            return false;
        }
    }

    public boolean Clear() {
        try {
            String QUERY = "DELETE FROM " + CONSTANTS.CART_TABLE_NAME;
            db.execSQL(QUERY);
            return true;
        } catch (Exception e) {
            Log.d("E71", e.getMessage());
            return false;
        }
    }

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        try {
            String QUERY = "SELECT * FROM " + CONSTANTS.CART_TABLE_NAME;
            cursor = db.rawQuery(QUERY, null);

            while (cursor.moveToNext()) {
                StringBuffer ITEM = new StringBuffer();
                ITEM.append(cursor.getString(1).toString() + CONSTANTS.DELIMITER_PROPERTIES);
                ITEM.append(cursor.getString(2).toString() + CONSTANTS.DELIMITER_PROPERTIES);
                ITEM.append(cursor.getInt(0) + "");
                //ITEM.append(cursor.getString(2).toString() + CONSTANTS.DELIMITER_PROPERTIES);
                items.add(ITEM.toString());
            }

        } catch (Exception e) {
            Log.d("E91", e.getMessage());
        }
        return items;
    }

}
