package uk.com.v3softech.online.store;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class chatAdapter extends FirebaseRecyclerAdapter<_chat, chatAdapter.holder> {

    Context context;


    public chatAdapter(@NonNull FirebaseRecyclerOptions<_chat> options, Context _context) {
        super(options);
        this.context = _context;
    }

    @Override
    protected void onBindViewHolder(@NonNull chatAdapter.holder holder, int position, @NonNull _chat model) {
        try {
            holder.chat_text.setText(model.getMessage());
        } catch (Exception e) {
            Log.d("E34", e.getMessage());
        }
    }

    @NonNull
    @Override
    public chatAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_notification, parent, false);
        return new chatAdapter.holder(view);
    }


    class holder extends RecyclerView.ViewHolder {

        TextView chat_text;

        public holder(@NonNull View itemView) {
            super(itemView);
            chat_text = (TextView) itemView.findViewById(R.id.notification_title);

        }
    }
}
