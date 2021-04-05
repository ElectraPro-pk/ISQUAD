package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import static java.lang.Integer.parseInt;

public class filterAdapter extends FirebaseRecyclerAdapter<product, filterAdapter.holder> {


    int MIN_FILTER, MAX_FILTER;
    Context context;

    public filterAdapter(@NonNull FirebaseRecyclerOptions<product> options, String min, String max, Context _context) {
        super(options);
        this.context = _context;
        this.MIN_FILTER = Integer.valueOf(min);
        this.MAX_FILTER = Integer.valueOf(max);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull product model) {
        int newPrice = Integer.valueOf(getNewPrice(model.getPrice(), model.getOff()));
        Log.d("newPrice",newPrice+"");
        holder.t.setText(model.getName());
        holder.price.setText(newPrice + "  rs");
        if (!model.getOff().equals("0")) {
            holder.discount.setText(model.getPrice() + " rs");
            holder.discountPer.setText(model.getOff() + "% Off");
        }
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
        if (newPrice > MIN_FILTER && newPrice < MAX_FILTER) {

        } else {
            holder.itemView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, singleProduct.class);
                i.putExtra("title", model.getName());
                i.putExtra("desc", model.getDescription());
                i.putExtra("price", String.valueOf(newPrice));
                i.putExtra("image",model.getImage());
                i.putExtra("type",model.getType());
                context.startActivity(i);
            }
        });

    }

    private String getNewPrice(String total, String off) {
        String newPrice = "0";
        if (!off.equals("0")) {
            float Price = (Integer.valueOf(off) * Integer.valueOf(total)) / 100;
            int _newPrice = Math.round(Integer.valueOf(total) - Price);
            newPrice = String.valueOf(_newPrice);
        } else {
            newPrice = total;
        }

        return newPrice;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_slim, parent, false);
        return new holder(view);
    }


    class holder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView t, price, discount, discountPer;

        public holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            t = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            discount = (TextView) itemView.findViewById(R.id.discount);
            discountPer = (TextView) itemView.findViewById(R.id.discountPer);
        }
    }
}
