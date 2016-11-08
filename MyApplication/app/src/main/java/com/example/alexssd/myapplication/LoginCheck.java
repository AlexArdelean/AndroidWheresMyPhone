package com.example.alexssd.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if we can't authenticate
        Intent startNewActivity1 = new Intent(LoginCheck.this, MainActivity.class);
        startActivity(startNewActivity1);

        //if we can authenticate
        Intent startNewActivity2 = new Intent(LoginCheck.this, Send_Location.class);
        startActivity(startNewActivity2);

    }
}
