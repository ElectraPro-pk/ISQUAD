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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class notificationAdapter extends FirebaseRecyclerAdapter<notification, notificationAdapter.holder> {

    Context context;


    public notificationAdapter(@NonNull FirebaseRecyclerOptions<notification> options, Context _context) {
        super(options);
        this.context = _context;
    }

    @Override
    protected void onBindViewHolder(@NonNull notificationAdapter.holder holder, int position, @NonNull notification model) {
        try {
            holder.title.setText(model.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("I40", model.getDescription());
                    Dialog POP = new Dialog(context);
                    POP.setContentView(R.layout.custompopup);
                    TextView CLOSE = (TextView) POP.findViewById(R.id.close);
                    TextView TITLE = (TextView) POP.findViewById(R.id.title);
                    TextView DESP = (TextView) POP.findViewById(R.id.description);
                    TITLE.setText(model.getTitle());
                    DESP.setText(model.getDescription());
                    CLOSE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            POP.dismiss();
                        }
                    });
                    POP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    POP.show();
                }
            });
        } catch (Exception e) {
            Log.d("E34", e.getMessage());
        }
    }

    @NonNull
    @Override
    public notificationAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_notification, parent, false);
        return new notificationAdapter.holder(view);
    }


    class holder extends RecyclerView.ViewHolder {
        TextView title;

        public holder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);

        }
    }
}
