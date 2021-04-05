package uk.com.v3softech.online.store;

import android.content.Context;
import android.content.Intent;
import android.widget.Adapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class DB {

    private DatabaseReference reff;
    private FirebaseAuth mAuth;

    public DB(String collection){
        reff = FirebaseDatabase.getInstance().getReference().child(collection);
        mAuth = FirebaseAuth.getInstance();
    }
    public void Insert(user data_object, final popups popUp, final String successMessage, final String failMessage){
        reff.child(data_object.getPhone()).setValue(data_object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    popUp.message(successMessage);
                }
                else{
                    popUp.message(failMessage);
                }
            }
        });
    }
    public void signInFacebook(){

    }
    public void signInGoogle(){

    }
    public ArrayList<String> getProducts(String path, final popups p){
        ArrayList<String> data = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    data.add(dataSnapshot.getValue().toString());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                p.message(error.getMessage());
            }
        });
        return data;
    }
    public void RegisterByEmail(String email,String Password,final popups popUp,final String successMessage,final String failMessage){
        mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    popUp.message(successMessage);
                } else {
                    popUp.message(task.getException().getMessage());
                }
            }
        });
    }
    public void LoginByCredentials(){

    }
    public void RegisterByCredentials(){

    }
    public void LoginByEmail(String email,String Password,final popups popUp,final String successMessage,final String failMessage){
        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    popUp.message(successMessage);
                } else {
                    popUp.message(failMessage);
                }
            }
        });
    }
    public void Delete(Object obj){ }
    public void Update(Object obj){ }
}
