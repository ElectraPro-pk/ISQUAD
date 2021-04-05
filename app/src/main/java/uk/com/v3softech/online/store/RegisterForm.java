package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterForm extends AppCompatActivity {
    popups p;
    validations validate;
     private EditText name,phoneNumber,pass,pass2;

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
        setContentView(R.layout.activity_register_form);
        p = new popups(getBaseContext());
        validate = new validations();

        name = (EditText) findViewById(R.id.fullname);
        phoneNumber = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);

    }
    public void validate(View v){
        String full_name = name.getText().toString();
        String Ph_Num = phoneNumber.getText().toString();
        String Pass = pass.getText().toString();
        String RePass = pass2.getText().toString();

        if(validate.validate(full_name) && validate.validate(Ph_Num) && validate.validate(Pass) && validate.validate(RePass)){
            if(Pass.equals(RePass)){
              if(pass.length() >= 6) {
                  Intent i = new Intent(this, CodeVerification.class);
                  i.putExtra("Name", full_name);
                  i.putExtra("Phone", Ph_Num);
                  i.putExtra("Pass", Pass);
                  startActivity(i);
              }
              else{
                  pass.setError("Password Must be Atleast 6 letters");
                  pass.requestFocus();
              }

            }
            else{
                pass2.setError("Password Does not Match");
                pass2.requestFocus();
            }
        }else{
            p.message("Please Fill Proper Details");
        }
    }
    public void login(View v)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}