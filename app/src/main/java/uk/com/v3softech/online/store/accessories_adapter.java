package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class accessories_adapter extends FirebaseRecyclerAdapter<accessory,accessories_adapter.holder> {

    Context context;
    public accessories_adapter(@NonNull FirebaseRecyclerOptions<accessory> options, Context _context) {
        super(options);
        this.context = _context;
    }

    @Override
    protected void onBindViewHolder(@NonNull accessories_adapter.holder holder, int position, @NonNull accessory model) {
        holder.t.setText(model.getName());
        Log.d("N30", model.getName());
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               popups p = new popups(context);
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    String message = "Hi! Tell me about this Accessory \n"+ model.getName()+"\n"+model.getImage();
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    i.setType("text/plain");
                    i.putExtra("jid", CONSTANTS.ADMIN_SMS_NUMBER + "@s.whatsapp.net"); //phone number without "+" prefix
                    //i.setData(Uri.parse(url));
                    i.setPackage("com.whatsapp");
                    context.startActivity(i);
                }
                catch(Exception e){
                    p.message(e.getMessage());
                }

            }
        });
    }

    @NonNull
    @Override
    public accessories_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_slim,parent,false);
        return  new accessories_adapter.holder(view);
    }



    class holder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView t;
        public holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            t = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
