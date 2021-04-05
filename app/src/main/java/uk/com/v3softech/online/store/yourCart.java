package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class yourCart extends AppCompatActivity {


    popups p;
    RecyclerView items;
    TextView total;
    cartManager cart;
    cartAdapter _cart;
    order o;
    DatabaseReference reff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_your_cart);
        p = new popups(this);
        total = (TextView) findViewById(R.id.total);
        o = new order();



        try {
            cart = new cartManager(yourCart.this);

            mAuth = FirebaseAuth.getInstance();
            items = (RecyclerView) findViewById(R.id.cart_items);


            sessionManager userSession = new sessionManager(yourCart.this);

            HashMap<String, String> user = userSession.current_user();
            CircleImageView ib = (CircleImageView) findViewById(R.id.dp);


            Glide.with(ib.getContext()).load(user.get(sessionManager.KEY_PIC)).into(ib);


            ArrayList<String> c = cart.getItems();
            if (c.size() == 0) {
                p.alert(yourCart.this, "Info", "Cart Empty");
            }
            String TOtal = getTotal(c);
            total.setText(getTotal(c));
            ArrayList<String> order_data = new ArrayList<>();
            String cart_items[] = new String[c.size()];
            for (int j = 0; j < c.size(); j++) {
                cart_items[j] = c.get(j);
                order_data.add(cart_items[j]);
            }


            o.setOrder_detail(order_data);
            o.setOrder_status("Pending");
            o.setOrder_date(CONSTANTS.getDate());
            o.setOrder_time(CONSTANTS.getTime());
            o.setTotal(String.valueOf(TOtal));

            _cart = new cartAdapter(cart_items, yourCart.this);
            items.setLayoutManager(new LinearLayoutManager(yourCart.this, LinearLayoutManager.VERTICAL, false));
            items.setAdapter(_cart);


        } catch (Exception e) {
            p.message("E82 \n" + e.getMessage());
        }
    }

    private String getTotal(ArrayList<String> c) {
        int _total = 0;
        for (int i = 0; i < c.size(); i++) {
            String detail[] = c.get(i).split(CONSTANTS.DELIMITER_PROPERTIES);
            _total += Integer.valueOf(detail[1]);
        }

        String total = String.valueOf(_total) + " rs";
        return total;
    }

    public void Home(View v){

        p.progress(yourCart.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(yourCart.this, MainScreenForOptions.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }
    public void chat(View view) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, "Hi!");
            i.setType("text/plain");
            i.putExtra("jid", CONSTANTS.ADMIN_SMS_NUMBER + "@s.whatsapp.net"); //phone number without "+" prefix
            //i.setData(Uri.parse(url));
            i.setPackage("com.whatsapp");
            startActivity(i);
        }
        catch(Exception e){
            p.message(e.getMessage());
        }
    }

    public void profile(View v) {
        startActivity(new Intent(yourCart.this, user_profile.class));
    }

    public void BacktoHome(View v) {
        try {
            Intent i = new Intent(yourCart.this, MainScreenForOptions.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
            p.message(e.getMessage());
        }

    }

    public void toNotification(View view) {
        Intent i = new Intent(yourCart.this, notifications.class);
        startActivity(i);
        finish();
    }

    public void clearCart(View view) {
        try {

            reff = FirebaseDatabase.getInstance().getReference().child("orders");
            String id = reff.push().getKey();
            o.setOrder_id(id);
            reff.child(id).setValue(o);

            Intent i = new Intent(yourCart.this, success.class);
            i.putExtra("message", "Ordered Successfully");
            startActivity(i);
            finish();
        } catch (Exception e) {
            p.message(e.getMessage());
        }
    }
    public void cart(View view) {
        startActivity(new Intent(yourCart.this, yourCart.class));
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}