package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends AppCompatActivity {

    TextView name, phone, email;
    CircleImageView dp;
popups p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        dp = (CircleImageView) findViewById(R.id.dp);
        p = new popups(user_profile.this);
        sessionManager userSession = new sessionManager(user_profile.this);
        HashMap<String, String> user = userSession.current_user();
        Glide.with(dp.getContext()).load(user.get(sessionManager.KEY_PIC)).into(dp);
        name.setText(user.get(sessionManager.KEY_FULLNAME));
        if(user.get(sessionManager.KEY_PHONE) != "" && user.get(sessionManager.KEY_PHONE) !=  null) {
            phone.setText(CONSTANTS.PHONE_ALIAS + user.get(sessionManager.KEY_PHONE));
        }
        email.setText(CONSTANTS.EMAIL_ALIAS+user.get(sessionManager.KEY_EMAIL));
    }
    public void EditProfile(View view){
        startActivity(new Intent(user_profile.this,editProfile.class));
    }

    public void Home(View v){

        p.progress(user_profile.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(user_profile.this, MainScreenForOptions.class);
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
        startActivity(new Intent(user_profile.this, user_profile.class));
    }
    public void cart(View view) {
        startActivity(new Intent(user_profile.this, yourCart.class));
        finish();
    }
    public void toNotification(View view) {
        Intent i = new Intent(user_profile.this, notifications.class);
        startActivity(i);
        finish();
    }
}