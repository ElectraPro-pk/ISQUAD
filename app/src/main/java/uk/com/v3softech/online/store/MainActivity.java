package uk.com.v3softech.online.store;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;



public class MainActivity extends AppCompatActivity {


    private EditText email,pass;
    private DatabaseReference reff;
    user u;
    private ProgressBar pb;
    popups p;
    LoginButton fb_login;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 9001,FB_SIGN_IN = 1005;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
validations validate;
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
        setContentView(R.layout.activity_main);

        int permission_All = 1;

        //START OF ONESINGAL
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(CONSTANTS.ONE_SIGNAL_APP_ID);
        //END



        mAuth = FirebaseAuth.getInstance();
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION};
        if(!hasPermission(this,permission))
        {
            ActivityCompat.requestPermissions(this,permission,permission_All);
        }
        email = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        pb = (ProgressBar) findViewById(R.id.pr);
        p = new popups(getBaseContext());
        validate = new validations();
       if(mAuth.getCurrentUser() != null){
            Intent i = new Intent(this,MainScreenForOptions.class);
            startActivity(i);
            finish();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
       mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        fb_login = findViewById(R.id.login_button);
        AccessToken current  = AccessToken.getCurrentAccessToken();
        if(current != null){
            Intent i = new Intent(this,MainScreenForOptions.class);
            startActivity(i);
            finish();
        }

        fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                p.message("Success");
                sessionManager userSession = new sessionManager(MainActivity.this);
                String dp_url = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Profile profile = Profile.getCurrentProfile();

                userSession.createLoginSession(profile.getName(),"","","",profile.getId(),"","","user",dp_url);

                Intent i = new Intent(MainActivity.this,MainScreenForOptions.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onCancel() {
                p.alert(MainActivity.this,"CANCEL","");
            }

            @Override
            public void onError(FacebookException error) {
                p.alert(MainActivity.this,"E124",error.getMessage());
            }
        });

    }

    public static boolean hasPermission(Context context, String... permissions)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
        context!=null && permissions!=null)
        {
            for(String permission : permissions)
            {
                if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void GoogleLogin(View v){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken(),account);
            } catch (Exception e) {
              Log.d("E191",e.getMessage());
                p.message("E191:"+e.getMessage());
            }
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            pb.setVisibility(View.VISIBLE);
        }
    }

    private void firebaseAuthWithGoogle(String idToken,GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            sessionManager userSession = new sessionManager(MainActivity.this);
                            userSession.createLoginSession(account.getDisplayName(),"","","",account.getEmail(),"","","user",account.getPhotoUrl().toString());

                            Intent i = new Intent(MainActivity.this,MainScreenForOptions.class);
                            startActivity(i);
                            finish();
                            p.message("Success");
                        } else {
                           p.message("Authentication Failed.");
                        }
                    }
                });
    }

    public void login(View v){
        try {
            String EMAIL = email.getText().toString().trim();
            String PASS = pass.getText().toString().trim();
            if (validate.validate(EMAIL) && validate.validate(PASS)) {
                DB db = new DB("Users");
                db.LoginByEmail(EMAIL,PASS, p, "Successfully Logged In", "Login Failed");
                Intent i = new Intent(this,MainScreenForOptions.class);
                startActivity(i);
                finish();

            } else {
                p.alert(MainActivity.this, "Error", "Please Fill Proper Details");
            }
        }
        catch(Exception e){
            p.alert(MainActivity.this,"Error",e.getMessage());
        }
    }
    public void Register(View v){
            Intent i = new Intent(this,RegisterForm.class);
            startActivity(i);
            finish();
    }



}