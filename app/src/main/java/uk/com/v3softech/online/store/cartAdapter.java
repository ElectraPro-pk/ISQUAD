package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.logging.LogRecord;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.holder> {

   private String data[];
   cartManager cart;
   Context  context;
    public cartAdapter(String[] _data, Context _context) {
        this.data = _data;
        this.context =_context;
        this.cart  = new cartManager(_context);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        try {
            String[] item_data = data[position].split(CONSTANTS.DELIMITER_PROPERTIES);
            holder.name.setText(item_data[0]);
            holder.price.setText(item_data[1]);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Log.d("I54",item_data[0]+CONSTANTS.DELIMITER_PROPERTIES+item_data[2]);
                  if(cart.RemoveItem(item_data[0])) {
                    context.startActivity(new Intent(context,yourCart.class));
                  }
                }
            });
        } catch (Exception e) {
            Log.d("E56",e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,price;
        public holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.cancel);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }


}
