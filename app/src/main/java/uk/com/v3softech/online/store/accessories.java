package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class accessories extends AppCompatActivity {

    RecyclerView all;
    popups p;
    accessories_adapter _adapter;

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
        setContentView(R.layout.activity_accessories);



        try {
            all = (RecyclerView) findViewById(R.id.all);
              p = new popups(accessories.this);
              p.message("Loaded");


           all.setLayoutManager(new GridLayoutManager(accessories.this, 2));
            FirebaseRecyclerOptions<accessory> options2 = new FirebaseRecyclerOptions.Builder<accessory>().setQuery(FirebaseDatabase.getInstance().getReference("accessories"), accessory.class).build();
            _adapter = new accessories_adapter(options2,accessories.this);
            all.setAdapter(_adapter);

        } catch (Exception e) {
            p.message(e.getMessage());
        }


    }
    public void backtohome(View v){
        p.progress(accessories.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(accessories.this, MainScreenForOptions.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }
    @Override
    protected void onStart() {
        super.onStart();
        _adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        _adapter.stopListening();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}