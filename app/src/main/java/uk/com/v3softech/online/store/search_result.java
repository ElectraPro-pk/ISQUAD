package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import javax.microedition.khronos.egl.EGLDisplay;

public class search_result extends AppCompatActivity {

    RangeSeekBar rangeFilter;
    EditText min,max;
    popups p;
    RecyclerView all;
    myAdapter _adapter_2;
    filterAdapter filteradapter;
    ObservableSnapshotArray dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        rangeFilter = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        min = (EditText) findViewById(R.id.min);
        max = (EditText) findViewById(R.id.max);
        p = new popups(search_result.this);
        all = (RecyclerView)findViewById(R.id.searched) ;
        String searchedQuery = getIntent().getExtras().get("query").toString();
        max.setText(CONSTANTS.MAX_RANGE_FILTER+"");
        rangeFilter.setRangeValues(CONSTANTS.MIN_RANGE_FILTER,CONSTANTS.MAX_RANGE_FILTER);
        rangeFilter.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                       try {
                           min.setText(minValue.toString());
                           max.setText(maxValue.toString());
                           all.setLayoutManager(new GridLayoutManager(search_result.this, 2));
                           FirebaseRecyclerOptions<product> options2 = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("name").startAt(searchedQuery).endAt(searchedQuery+"\uf8ff"), product.class).build();
                           filteradapter = new filterAdapter(options2,minValue.toString(),maxValue.toString(),search_result.this);
                           filteradapter.startListening();
                           all.setAdapter(filteradapter);

                       }
                       catch(Exception e){
                           p.message(e.getMessage());
                       }
                     //   p.alert(search_result.this,"VALUES CHANGED 32",minValue.toString() + ":" +maxValue.toString());
            }

        });
      try {

          //p.alert(search_result.this,"L 44",searchedQuery);
          all.setLayoutManager(new GridLayoutManager(search_result.this, 2));
          FirebaseRecyclerOptions<product> options2 = new FirebaseRecyclerOptions.Builder<product>().setQuery(FirebaseDatabase.getInstance().getReference("items").orderByChild("name").startAt(searchedQuery).endAt(searchedQuery+"\uf8ff"), product.class).build();
          _adapter_2 = new myAdapter(options2,search_result.this);
          _adapter_2.startListening();
          all.setAdapter(_adapter_2);
      }
      catch(Exception e){
          p.alert(search_result.this,"E 51",e.getMessage());
          Log.d( "E67 ",e.getMessage());
      }
    }

    public void backtohome(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        _adapter_2.startListening();
       // filteradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        _adapter_2.stopListening();
      //  filteradapter.stopListening();
    }
}