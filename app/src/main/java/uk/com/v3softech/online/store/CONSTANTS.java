package uk.com.v3softech.online.store;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CONSTANTS {

    public static final long DELAY_SHORT = 750;
    public static final long DELAY_NORMAL = 1200;
    public static final long DELAY_LONG = 1700;
    public static final int MIN_RANGE_FILTER = 0;
    public static final int MAX_RANGE_FILTER = 20000;
    public static final String LINE_BREAKER = "\n";
    public static final String PHONE_ALIAS = "Phone" + LINE_BREAKER;
    public static final String EMAIL_ALIAS = "Email" + LINE_BREAKER;
    public static final String DELIMITER_PROPERTIES = "_";
    public static final String CURRENT_COUNTRY_DIALLING_CODE = "92";
    public static final String ADMIN_SMS_NUMBER = CURRENT_COUNTRY_DIALLING_CODE+"3040094025";
    public static final String DB_NAME = "personalize";
    public static final String CART_TABLE_NAME = "cart";
    public static final String CART_ITEM_ID = "_ID";
    public static final String CART_ITEM_NAME = "_NAME";
    public static final String CART_ITEM_PRICE = "_PRICE";
    public static final String CART_ITEM_QUANTITY = "_QUANTITY";
    public static final String CART_TABLE_MAKING_QUERY = "CREATE TABLE IF NOT EXISTS " + CART_TABLE_NAME + "(" + CART_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CART_ITEM_NAME + " VARCHAR," + CART_ITEM_PRICE + " VARCHAR," + CART_ITEM_QUANTITY + " VARCHAR)";
    public static final String ONE_SIGNAL_APP_ID = "8dc17da3-e243-41dc-8e4e-bba8c5a4a2aa";

    //yyyy/MM/dd HH:mm:ss
    public static String getTime() {
        Date d = new Date();
        String time = d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        return time;
    }

    public static String getDate() {
        Date d = new Date();
        String _date = d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getYear();
        return _date;
    }


}
