package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.Login;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreenForOptions extends AppCompatActivity {

    popups p;
    private FirebaseAuth mAuth;

    public void Logout(View v) {
        try {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                    .Callback() {

                @Override
                public void onCompleted(GraphResponse graphResponse) {

                    LoginManager.getInstance().logOut();

                }
            }).executeAsync();
            sessionManager user = new sessionManager(this);
            user.logoutSession();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
            p.message(e.getMessage());
        }

    }

    public void toNotification(View view) {
        Intent i = new Intent(MainScreenForOptions.this, notifications.class);
        startActivity(i);
        finish();
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

    public void CustomPC(View v) {
        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(MainScreenForOptions.this, builtbt.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }
    public void to_acceso(View v) {
        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(MainScreenForOptions.this, accessories.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }

    public void Home(View v){

        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(MainScreenForOptions.this, MainScreenForOptions.class);
                startActivity(i);
                finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }

    public void BuyComponent(View v) {
        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainScreenForOptions.this, Main_Market.class));
                // finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }

    public void cart(View view) {
        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainScreenForOptions.this, yourCart.class));
                // finish();
            }
        }, CONSTANTS.DELAY_NORMAL);
    }

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

        setContentView(R.layout.activity_main_screen_for_options);
        p = new popups(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        sessionManager userSession = new sessionManager(MainScreenForOptions.this);

        HashMap<String, String> user = userSession.current_user();
        CircleImageView ib = (CircleImageView) findViewById(R.id.dp);

        Glide.with(ib.getContext()).load(user.get(sessionManager.KEY_PIC)).into(ib);
    }

    public void profile(View v) {
        p.progress(MainScreenForOptions.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainScreenForOptions.this, user_profile.class));
                // finish();
            }
        }, CONSTANTS.DELAY_NORMAL);

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