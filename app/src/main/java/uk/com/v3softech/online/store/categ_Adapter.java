package uk.com.v3softech.online.store;

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

import java.util.List;
import java.util.TooManyListenersException;
import java.util.logging.LogRecord;

public class categ_Adapter extends RecyclerView.Adapter<categ_Adapter.holder>{

        public int[] data = {
                R.drawable.ic_motherboard,
                R.drawable.ic_casing,
                R.drawable.ic_ram_memory,
                R.drawable.ic_cpu,
                R.drawable.ic_cooling_system,
                R.drawable.ic_hdd,
                R.drawable.ic_power_supply,
                R.drawable.ic_graphics_card
        };
        public categ_Adapter() {

        }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categ_item,parent,false);
        return new holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
            try {
               holder.img.setImageResource(data[position]);
            }
            catch (Exception e){
                Log.d("DEBUG 56",e.getMessage());
            }
    }


    @Override
    public int getItemCount() {
        return data.length;
    }


    class holder extends RecyclerView.ViewHolder{
            ImageView img;
            public holder(@NonNull View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img1);

            }
        }


}
