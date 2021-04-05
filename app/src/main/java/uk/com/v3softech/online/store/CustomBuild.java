package uk.com.v3softech.online.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomBuild extends AppCompatActivity {

    RecyclerView rc;
    customBuildAdapter madapter;
    private ImageButton back, next;
    private TextView header;
    private int STEP = 1;
    private TextView BUILD;
    public static final String KEY_MOTHERBOARD = "motherboard";
    public static final String KEY_PROCESSOR = "processor";
    public static final String KEY_RAM = "ram";
    public static final String KEY_GRAPHICS = "graphics";
    public static final String KEY_STORAGE = "storage";
    public static final String KEY_POWER = "power";
    public static final String KEY_COOLING = "cooling";
    public static final String KEY_CASING = "casing";

    private HashMap<String, String> BUILD_DATA;

    private static final String[] COMPONENT_SEQUENCE = {KEY_MOTHERBOARD, KEY_PROCESSOR, KEY_RAM, KEY_GRAPHICS, KEY_STORAGE, KEY_POWER, KEY_COOLING, KEY_CASING};
    private ProgressBar pb;
    popups p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_custom_build);

        p = new popups(CustomBuild.this);
        pb = (ProgressBar) findViewById(R.id.pg);
        back = (ImageButton) findViewById(R.id.back);
        next = (ImageButton) findViewById(R.id.next);
        header = (TextView) findViewById(R.id.title);
        BUILD = (TextView) findViewById(R.id.build);
        back.setVisibility(View.GONE);
        try {
customBuildManager buildIt = new customBuildManager(CustomBuild.this);
//buildIt.clearSession();
            cartManager cart  = new cartManager(CustomBuild.this);


            rc = (RecyclerView) findViewById(R.id.rc);
            rc.setLayoutManager(new GridLayoutManager(this, 2));
            //FirebaseRecyclerOptions<product> options = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").child(COMPONENT_SEQUENCE[STEP - 1]), product.class).build();
            FirebaseRecyclerOptions<product> options = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("type").equalTo(COMPONENT_SEQUENCE[STEP - 1]), product.class).build();
            madapter = new customBuildAdapter(options, CustomBuild.this);
            rc.setAdapter(madapter);
            madapter.startListening();
            header.setText(COMPONENT_SEQUENCE[STEP - 1]);


        } catch (Exception e) {
            p.alert(this, "E26", e.getMessage());
        }
    }

    public void build(View v) {
        Intent i = new Intent(CustomBuild.this, build.class);
        startActivity(i);
        finish();
    }

    public  void Forward(View v) {

        if (STEP < 8) {
            STEP++;
            if (isLast()) {
                next.setVisibility(View.GONE);
            }
            back.setVisibility(View.VISIBLE);
            updateUI(STEP - 1);
        }
    }

    private void updateUI(int step) {

        try {
            p.progress(CustomBuild.this);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (isLast()) {
                        BUILD.setVisibility(View.VISIBLE);
                    } else {
                        BUILD.setVisibility(View.GONE);
                    }
                    rc = (RecyclerView) findViewById(R.id.rc);
                    rc.setLayoutManager(new GridLayoutManager(CustomBuild.this, 2));
                    // FirebaseRecyclerOptions<product> options = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").child(COMPONENT_SEQUENCE[step]), product.class).build();
                    FirebaseRecyclerOptions<product> options = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("type").equalTo(COMPONENT_SEQUENCE[step]), product.class).build();
                    madapter = new customBuildAdapter(options, CustomBuild.this);
                    rc.setAdapter(madapter);
                    madapter.startListening();
                    header.setText(COMPONENT_SEQUENCE[step]);
                }
            }, CONSTANTS.DELAY_SHORT);

        } catch (Exception e) {
            p.alert(this, "E103", e.getMessage());
        }
    }

    private boolean isLast() {
        if (STEP == 8) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFirst() {
        if (STEP == 1) {
            return true;
        } else {
            return false;
        }
    }


    public void Reverse(View v) {

        if (STEP > 1) {
            STEP--;
            if (isFirst()) {
                back.setVisibility(View.GONE);
            }
            next.setVisibility(View.VISIBLE);
            updateUI(STEP - 1);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        madapter.stopListening();
    }

    public void Cancel(View v) {
        Intent i = new Intent(this, MainScreenForOptions.class);
        startActivity(i);
        finish();
    }
}