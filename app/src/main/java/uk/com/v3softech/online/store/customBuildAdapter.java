package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;

import org.w3c.dom.Text;

public class customBuildAdapter extends FirebaseRecyclerAdapter<product, customBuildAdapter.holder> {

    sessionManager build;

    public customBuildAdapter(@NonNull FirebaseRecyclerOptions<product> options,Context _con) {
        super(options);
        build = new sessionManager(_con);

    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull product model) {
        holder.t.setText(model.getName());
        holder.price.setText(getNewPrice(model.getPrice(), model.getOff()) + "  rs");
        if (!model.getOff().equals("0")) {
            holder.discount.setText(model.getPrice() + " rs");
            holder.discountPer.setText(model.getOff() + "% Off");
        }

        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.layout.setBackgroundColor(Color.TRANSPARENT);
                view.setBackgroundColor(Color.argb(50,255,10,10));

                switch (model.getType()) {
                    case CustomBuild.KEY_MOTHERBOARD:
                        build.addCustom(CustomBuild.KEY_MOTHERBOARD, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_PROCESSOR:
                        build.addCustom(CustomBuild.KEY_PROCESSOR, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_GRAPHICS:
                        build.addCustom(CustomBuild.KEY_GRAPHICS, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_STORAGE:
                        build.addCustom(CustomBuild.KEY_STORAGE, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_POWER:
                        build.addCustom(CustomBuild.KEY_POWER, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_RAM:
                        build.addCustom(CustomBuild.KEY_RAM, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_COOLING:
                        build.addCustom(CustomBuild.KEY_COOLING, model.getName()+"_"+model.getPrice());
                        break;
                    case CustomBuild.KEY_CASING:
                        build.addCustom(CustomBuild.KEY_CASING, model.getName()+"_"+model.getPrice());
                        break;
                }
            }
            Object a = new CustomBuild();


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
        LinearLayoutCompat layout;

        public holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            t = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            discount = (TextView) itemView.findViewById(R.id.discount);
            discountPer = (TextView) itemView.findViewById(R.id.discountPer);
            layout = (LinearLayoutCompat) itemView.findViewById(R.id.layout);
        }
    }
}
