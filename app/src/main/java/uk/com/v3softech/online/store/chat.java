package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.internal.ContextUtils;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.net.URISyntaxException;


public class chat extends AppCompatActivity {

    popups p;
    messagesAdapter _ChatAdapter;
    RecyclerView _messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        p = new popups(chat.this);
        try {

            _messages = (RecyclerView) findViewById(R.id.messages);
            _messages.setLayoutManager(new LinearLayoutManager(chat.this, LinearLayoutManager.VERTICAL, true));
            FirebaseRecyclerOptions<user> options2 = new FirebaseRecyclerOptions.Builder<user>().setQuery(FirebaseDatabase.getInstance().getReference("users").orderByChild("name"), user.class).build();
            _ChatAdapter = new messagesAdapter(options2, chat.this);
            _ChatAdapter.startListening();
            _messages.setAdapter(_ChatAdapter);

        } catch (Exception e) {
            p.message(e.getMessage());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        _ChatAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        _ChatAdapter.stopListening();
    }

    public void back(View view) {
        finish();
    }
}