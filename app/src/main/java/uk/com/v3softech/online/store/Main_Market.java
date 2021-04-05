package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.SearchView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class Main_Market extends AppCompatActivity {

    RecyclerView random, categories, flashSale, all;
    SearchView search;
    market1 _market;
    popups p;
    myAdapter_1 _adapter, _adapter_2;
categ_Adapter categ_adapter;
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
        setContentView(R.layout.activity_main__market);
        p = new popups(Main_Market.this);
        search = (SearchView) findViewById(R.id.search);
        random = (RecyclerView) findViewById(R.id.random);
        categories = (RecyclerView) findViewById(R.id.category);
        flashSale = (RecyclerView) findViewById(R.id.flashSale);
        all = (RecyclerView) findViewById(R.id.all);

        try {

            random.setLayoutManager(new LinearLayoutManager(Main_Market.this, LinearLayoutManager.HORIZONTAL, false));
            FirebaseRecyclerOptions<product> options = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("type"), product.class).build();
            _market = new market1(options);
            random.setAdapter(_market);

            flashSale.setLayoutManager(new LinearLayoutManager(Main_Market.this, LinearLayoutManager.HORIZONTAL, false));
            FirebaseRecyclerOptions<product> options1 = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("type"), product.class).build();
            _adapter = new myAdapter_1(options1,Main_Market.this);
            flashSale.setAdapter(_adapter);

            all.setLayoutManager(new GridLayoutManager(Main_Market.this, 2));
            FirebaseRecyclerOptions<product> options2 = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("type"), product.class).build();
            _adapter_2 = new myAdapter_1(options2,Main_Market.this);
            all.setAdapter(_adapter_2);

            categ_adapter = new categ_Adapter();
            categories.setLayoutManager(new LinearLayoutManager(Main_Market.this, LinearLayoutManager.HORIZONTAL, false));
            categories.setAdapter(categ_adapter);


            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent i = new Intent(Main_Market.this, search_result.class);
                    i.putExtra("query", query);
                    startActivity(i);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        } catch (Exception e) {
            p.message(e.getMessage());
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        _market.startListening();
        _adapter.startListening();
        _adapter_2.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        _market.stopListening();
        _adapter.stopListening();
        _adapter_2.stopListening();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}