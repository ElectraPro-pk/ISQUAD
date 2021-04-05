package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class notifications extends AppCompatActivity {

    RecyclerView notis;
    popups p;
    notificationAdapter _noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        p = new popups(notifications.this);
        try {
            notis = (RecyclerView) findViewById(R.id.noti);
            notis.setLayoutManager(new LinearLayoutManager(notifications.this, LinearLayoutManager.VERTICAL, false));
            FirebaseRecyclerOptions<notification> options2 = new FirebaseRecyclerOptions.Builder<notification>().setQuery(FirebaseDatabase.getInstance().getReference("notifications").orderByChild("title"), notification.class).build();
            _noti = new notificationAdapter(options2, notifications.this);
            _noti.startListening();
            notis.setAdapter(_noti);



        } catch (Exception e) {
            p.message(e.getMessage());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        _noti.stopListening();
    }

    public void back(View view) {
        startActivity(new Intent(notifications.this, MainScreenForOptions.class));
        finish();
    }
}