package com.sergiogomeza.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FacebookInfo extends AppCompatActivity {
    Button bLogout;
    TextView tUsername,tEmail,tPhone;
    String EName,EEmail,EPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_info);
        tUsername = (TextView) findViewById(R.id.tUsername);
        tEmail = (TextView) findViewById(R.id.tEmail);
        tPhone = (TextView) findViewById(R.id.tPhone);
        bLogout = (Button) findViewById(R.id.bLogout);
        Intent intent = getIntent();
        EName = intent.getStringExtra("Username");
        EEmail = intent.getStringExtra("email");
        EPhone = intent.getStringExtra("phone");
    }

    @Override
    public void onStart() {
        super.onStart();
        tUsername.setText(EName);
        tEmail.setText(EEmail);
        tPhone.setText(EPhone);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
