package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class singleProduct extends AppCompatActivity {

    popups p;
    Button add, rem;

    String TITLE, DESCRIPTION, PRICE, IMAGE, TYPE;
    cartManager cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        p = new popups(singleProduct.this);

        TextView title = (TextView) findViewById(R.id.pr_name);
        TextView description = (TextView) findViewById(R.id.description);
        TextView price = (TextView) findViewById(R.id.pr_price);
        ImageView image = (ImageView) findViewById(R.id.image);
        add = (Button) findViewById(R.id.add);
        rem = (Button) findViewById(R.id.remove);
        cart = new cartManager(singleProduct.this);
        try {
            TITLE = getIntent().getExtras().getString("title");
            DESCRIPTION = getIntent().getExtras().getString("desc");
            PRICE = getIntent().getExtras().getString("price");
            IMAGE = getIntent().getExtras().getString("image");
            TYPE = getIntent().getExtras().getString("type");


            Glide.with(image.getContext()).load(IMAGE).into(image);
            title.setText(TITLE);
            price.setText(PRICE + " rs");
            description.setText(DESCRIPTION);
        } catch (Exception e) {
            p.message(e.getMessage());
        }
    }

    public void addToCart(View view) {
        p.progress(singleProduct.this);
        if (cart.InsertItem(TITLE, PRICE, "1")) {
            p.alert(singleProduct.this, "", "Successfully Added");
            add.setVisibility(View.GONE);
            rem.setVisibility(View.VISIBLE);
        }
    }

    public void Home(View v){

        p.progress(singleProduct.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(singleProduct.this, MainScreenForOptions.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }
    public void profile(View v){
        p.progress(singleProduct.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(singleProduct.this,user_profile.class));
                // finish();
            }
        }, CONSTANTS.DELAY_NORMAL);

    }
    public void chat(View view){
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
    public void cart(View view){
        p.progress(singleProduct.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(singleProduct.this,yourCart.class));
                // finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }
    public void cancel(View view) {
        startActivity(new Intent(singleProduct.this, Main_Market.class));
        finish();
    }

    public void clear() {
        if(cart.empty()){
            p.message("Cart is Already Empty");
        }
        else{
            cart.Clear();
            p.message("Cart Cleared Successfully");
        }
    }

    public void toNotification(View view) {
        Intent i = new Intent(singleProduct.this, notifications.class);
        startActivity(i);
        finish();
    }
    public void removeFromCart(View view) {
        p.progress(singleProduct.this);
        if (cart.RemoveItem(TITLE)) {
            p.alert(singleProduct.this, "", "Successfully Removed");
            add.setVisibility(View.VISIBLE);
            rem.setVisibility(View.GONE);
        }
    }
}