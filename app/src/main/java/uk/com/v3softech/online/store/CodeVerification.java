package uk.com.v3softech.online.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransitionImpl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CodeVerification extends AppCompatActivity {
    //EditText OTPCODE;
    PinView OTPCODE;
    ProgressBar progressBar;
    String verificationOTPSystem;
    FirebaseAuth mAuth;
    popups p;
    validations validations;
    String name,Phone,Pass;
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
        setContentView(R.layout.activity_code_verification);
        p = new popups(getApplicationContext()) ;
        validations = new validations();
        OTPCODE = (PinView) findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        name = getIntent().getStringExtra("Name");
        Phone = ProcessPhoneNumber(getIntent().getStringExtra("Phone"));
        //Phone = getIntent().getStringExtra("Phone");
        //p.message(Phone);
        Pass = getIntent().getStringExtra("Pass");
        try{
            SystemOTPVerification(name,Phone,Pass);
        }catch (Exception e){
            p.alert(this,"LINE 64",e.getMessage());
        }
    }
    public void ManualVerify(View v){
        try {
            String code = OTPCODE.getText().toString();
            if (!validations.validate(code) || code.length() < 6) {
                OTPCODE.setError("Wrong OTP....");
                OTPCODE.requestFocus();
            }
            progressBar.setVisibility(v.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    verifyCode(code);
                }
            }, CONSTANTS.DELAY_SHORT);

        }
        catch(Exception e){
            p.alert(this,"LINE 63",e.getMessage());
        }
    }
    private String ProcessPhoneNumber(String phone) {
        String processed_phone_number = "";
            if(phone.length() == 11)
            {
                processed_phone_number = "+92" + phone.substring(1,phone.length());
            }
            else if(phone.length() == 10){
                processed_phone_number = "+92" + phone;
            }

        return processed_phone_number;
    }

    private void SystemOTPVerification(String name, String phone, String pass) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
     private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
         @Override
         public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
             super.onCodeSent(s, forceResendingToken);
             verificationOTPSystem = s;
             //p.message(s);
         }

         @Override
         public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
             String code = phoneAuthCredential.getSmsCode();

             p.message(code);
             if(code != null){
                 OTPCODE.setText(code);
                 progressBar.setVisibility(View.VISIBLE);
                 Handler handler = new Handler();
                 handler.postDelayed(new Runnable() {
                     public void run() {
                         verifyCode(code);
                     }
                 }, 700);   //5 seconds
             }
         }

         @Override
         public void onVerificationFailed(@NonNull FirebaseException e) {
               p.alert(CodeVerification.this,"Error ",e.getMessage());
             //Log.d("ERROR : ", e.getMessage());
         }
     };

    public void verifyCode(String otp){
        PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(verificationOTPSystem,otp);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(CodeVerification.this,new  OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user u = new user();
                    String email = Phone+"@isquad.com";
                    u.setEmail(email);
                    u.setPhone(Phone);
                    u.setPassword(Pass);
                    u.setName(name);


                    DB db = new DB("users");
                    db.Insert(u,p,"Successfully Registered","Registration Failed");
                    db.RegisterByEmail(email,Pass,p,"Successfully Registered","Registration Failed");
                    sessionManager userSession = new sessionManager(CodeVerification.this);
                    userSession.createLoginSession(name,"","",Phone,email,"","","user","");

                    Intent i = new Intent(getApplicationContext(),MainScreenForOptions.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
                else{
                    p.alert(CodeVerification.this,"LINE 128 ",task.getException().getMessage());
                }
            }
        });
    }
}