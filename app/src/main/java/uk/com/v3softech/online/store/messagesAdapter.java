package uk.com.v3softech.online.store;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class messagesAdapter extends FirebaseRecyclerAdapter<user, messagesAdapter.holder> {

    Context context;
    chatAdapter _chatAdapter;
    DatabaseReference reff;
    FirebaseAuth mAuth;

    public messagesAdapter(@NonNull FirebaseRecyclerOptions<user> options, Context _context) {
        super(options);
        this.context = _context;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull messagesAdapter.holder holder, int position, @NonNull user model) {
        try {
            holder.from_id.setText(model.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.customchatpoup);
                    TextView _username = (TextView) dialog.findViewById(R.id.username);
                    RecyclerView _chats = (RecyclerView) dialog.findViewById(R.id.message);
                    EditText _message_text = (EditText) dialog.findViewById(R.id.messageText);
                    ImageButton _send = (ImageButton) dialog.findViewById(R.id.sendBtn);
                    TextView _close = (TextView) dialog.findViewById(R.id.close);
                    _username.setText(model.getName());
                    _chats.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    FirebaseRecyclerOptions<_chat> options2 = new FirebaseRecyclerOptions.Builder<_chat>().setQuery(FirebaseDatabase.getInstance().getReference("chat").orderByChild("messageTo").equalTo(model.getName()), _chat.class).build();
                    _chatAdapter = new chatAdapter(options2, context);
                    _chatAdapter.startListening();
                    _chats.setAdapter(_chatAdapter);
                    sessionManager user = new sessionManager(context);
                    HashMap<String, String> usrD = user.current_user();
                    _close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            _chatAdapter.stopListening();
                        }
                    });
                    _send.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Log.d("M69", _message_text.getText().toString());


                            _chat new_message = new _chat();
                            new_message.setMessageDate(CONSTANTS.getDate());
                            new_message.setMessageTime(CONSTANTS.getTime());
                            new_message.setMessageTo(model.getName());
                            new_message.setMessageFrom(usrD.get(sessionManager.KEY_FULLNAME));
                            new_message.setMessage(_message_text.getText().toString());

                            reff = FirebaseDatabase.getInstance().getReference().child("chat");
                            String id = reff.push().getKey();
                            reff.child(id).setValue(new_message);
                            _chats.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            FirebaseRecyclerOptions<_chat> options2 = new FirebaseRecyclerOptions.Builder<_chat>().setQuery(FirebaseDatabase.getInstance().getReference("chat").orderByChild("messageTo").equalTo(model.getName()), _chat.class).build();
                            _chatAdapter = new chatAdapter(options2, context);
                            _chatAdapter.startListening();
                            _chats.setAdapter(_chatAdapter);
                            _message_text.setText("");
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
        } catch (Exception e) {
            Log.d("E34", e.getMessage());
        }
    }

    @NonNull
    @Override
    public messagesAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_notification, parent, false);
        return new messagesAdapter.holder(view);
    }


    class holder extends RecyclerView.ViewHolder {

        TextView from_id;

        public holder(@NonNull View itemView) {
            super(itemView);
            from_id = (TextView) itemView.findViewById(R.id.notification_title);
        }
    }
}
