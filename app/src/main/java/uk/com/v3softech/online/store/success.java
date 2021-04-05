package uk.com.v3softech.online.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class success extends AppCompatActivity {
popups p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        p = new popups(success.this);
        TextView message = (TextView) findViewById(R.id.message);
        message.setText(getIntent().getExtras().getString("message"));
    }

    public void backtohome(View view){
        p.progress(success.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(success.this,MainScreenForOptions.class));
                finish();
            }
        },CONSTANTS.DELAY_SHORT);
    }
}