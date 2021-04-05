package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class build extends AppCompatActivity {

    popups p;
    order o;
    DatabaseReference reff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        p = new popups(build.this);
        o = new order();

        try {
            mAuth = FirebaseAuth.getInstance();
            customBuildManager buildC = new customBuildManager(build.this);

            HashMap<String, String> data = buildC.currentBuild();
            String[] motherboard = splitString("_", data.get(CustomBuild.KEY_MOTHERBOARD));
            String[] processor = splitString("_", data.get(CustomBuild.KEY_PROCESSOR));
            String[] graphics = splitString("_", data.get(CustomBuild.KEY_GRAPHICS));
            String[] cooling = splitString("_", data.get(CustomBuild.KEY_COOLING));
            String[] casing = splitString("_", data.get(CustomBuild.KEY_CASING));
            String[] ram = splitString("_", data.get(CustomBuild.KEY_RAM));
            String[] storage = splitString("_", data.get(CustomBuild.KEY_STORAGE));
            String[] power = splitString("_", data.get(CustomBuild.KEY_POWER));

            int Total_Price = getTotal(motherboard[1], processor[1], graphics[1], cooling[1], casing[1], ram[1], storage[1], power[1]);

            buildC.addCustom("TOTAL_BUILD", Total_Price + "");

            TextView mb_name = (TextView) findViewById(R.id.mb_name);
            TextView mb_price = (TextView) findViewById(R.id.mb_price);
            TextView pr_name = (TextView) findViewById(R.id.pr_name);
            TextView pr_price = (TextView) findViewById(R.id.pr_price);
            TextView gr_name = (TextView) findViewById(R.id.gr_name);
            TextView gr_price = (TextView) findViewById(R.id.gr_price);
            TextView s_name = (TextView) findViewById(R.id.st_name);
            TextView s_price = (TextView) findViewById(R.id.st_price);
            TextView c_name = (TextView) findViewById(R.id.c_name);
            TextView c_price = (TextView) findViewById(R.id.c_price);
            TextView cas_name = (TextView) findViewById(R.id.cas_name);
            TextView cas_price = (TextView) findViewById(R.id.cas_price);
            TextView pow_name = (TextView) findViewById(R.id.pow_name);
            TextView pow_price = (TextView) findViewById(R.id.pow_price);
            TextView r_name = (TextView) findViewById(R.id.r_name);
            TextView r_price = (TextView) findViewById(R.id.r_price);
            TextView total_price = (TextView) findViewById(R.id.total);


            mb_name.setText(motherboard[0]);
            mb_price.setText(motherboard[1] + " rs");
            pr_name.setText(processor[0]);
            pr_price.setText(processor[1] + " rs");
            gr_name.setText(graphics[0]);
            gr_price.setText(graphics[1] + " rs");
            cas_name.setText(casing[0]);
            cas_price.setText(casing[1] + " rs");
            c_name.setText(cooling[0]);
            c_price.setText(cooling[1] + " rs");
            r_name.setText(ram[0]);
            r_price.setText(ram[1] + " rs");
            s_name.setText(storage[0]);
            s_price.setText(storage[1] + " rs");
            pow_name.setText(power[0]);
            pow_price.setText(power[1] + " rs");
            total_price.setText(Total_Price + " rs");


            ArrayList<String> order_data = new ArrayList<>();
            order_data.add(data.get(CustomBuild.KEY_MOTHERBOARD));
            order_data.add(data.get(CustomBuild.KEY_PROCESSOR));
            order_data.add(data.get(CustomBuild.KEY_GRAPHICS));
            order_data.add(data.get(CustomBuild.KEY_COOLING));
            order_data.add(data.get(CustomBuild.KEY_CASING));
            order_data.add(data.get(CustomBuild.KEY_RAM));
            order_data.add(data.get(CustomBuild.KEY_STORAGE));
            order_data.add(data.get(CustomBuild.KEY_POWER));

            o.setOrder_detail(order_data);
            o.setOrder_status("Pending");
            o.setOrder_date(CONSTANTS.getDate());
            o.setOrder_time(CONSTANTS.getTime());
            o.setTotal(String.valueOf(Total_Price));
        } catch (Exception e) {
            p.alert(build.this, "E24", e.getMessage());
        }
    }

    public int getTotal(String m, String p, String g, String c, String cas, String r, String s, String pow) {
        int total = 0;
        total = Integer.parseInt(m) + Integer.parseInt(p) + Integer.parseInt(g) + Integer.parseInt(c) + Integer.parseInt(cas) + Integer.parseInt(r) + Integer.parseInt(s) + Integer.parseInt(pow);
        return total;
    }

    public String[] splitString(String delim, String str) {
        return str.split(delim);
    }

    public void MakeOrder(View view) {
        p.progress(build.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    reff = FirebaseDatabase.getInstance().getReference().child("orders");
                    String id = reff.push().getKey();
                    o.setOrder_id(id);
                    reff.child(id).setValue(o);

                    Intent i = new Intent(build.this, success.class);
                    i.putExtra("message", "Ordered Successfully");
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    p.message(e.getMessage());
                }


            }
        }, CONSTANTS.DELAY_LONG);
    }
}