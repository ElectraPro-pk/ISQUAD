package uk.com.v3softech.online.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends AppCompatActivity {

    sessionManager User;
    HashMap<String, String> user_data;
    EditText nam, email, phone, address, pass;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    popups p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        User = new sessionManager(editProfile.this);
        user_data = User.current_user();
        nam = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.password);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();

        nam.setText(user_data.get(sessionManager.KEY_FULLNAME));
        email.setText(user_data.get(sessionManager.KEY_EMAIL));
        p = new popups(editProfile.this);
        p.message(user_data.get(sessionManager.KEY_PHONE));
    }

    public void saveProfile(View view) {
        try {
            user u = new user();
            if (nam.getText().toString().trim().length() == 0) {
                nam.setError("Please Enter Name");
            } else if (email.getText().toString().trim().length() == 0) {
                email.setError("Please Enter Email");
            } else if (address.getText().toString().trim().length() == 0) {
                address.setError("Please Enter Address");
            } else if (pass.getText().toString().trim().length() == 0) {
                pass.setError("Please Enter Password");
            } else {
                User = new sessionManager(editProfile.this);

                user_data = User.current_user();
                u.setPassword(pass.getText().toString());
                u.setEmail(email.getText().toString());
                u.setName(nam.getText().toString());

                Map<String,Object> userData = new HashMap<>();
                userData.put("email",u.getEmail());
                userData.put("name",u.getName());
                userData.put("password",u.getPassword());
                userData.put("phone",u.getPhone());

                u.setPhone(user_data.get(sessionManager.KEY_PHONE));
                reff = FirebaseDatabase.getInstance().getReference().child("users");
                reff.child(user_data.get(sessionManager.KEY_PHONE)).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            startActivity(new Intent(editProfile.this, editProfile.class));
                            finish();
                        }
                        else{
                            p.message("Update Failed");
                        }
                    }
                });

            }
        } catch (Exception e) {
            Log.d("E63", e.getMessage());
        }
    }
    private boolean usernameExists(String username) {
        DatabaseReference fdbRefer = FirebaseDatabase.getInstance().getReference("users/"+username);
        return (fdbRefer != null);
    }
}