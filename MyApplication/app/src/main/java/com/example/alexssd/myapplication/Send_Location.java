package com.example.alexssd.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
//import com.hmkcode.android.vo.Person;

//import org.apache.http.impl.client.HttpClients;

public class Send_Location extends AppCompatActivity {

    private TextView gps;
    int id = 14;
    private TextView macaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout sendgpslayout = new RelativeLayout(this);

        macaddress = new TextView(this);
        gps = new TextView(this);

        Button signoutB = new Button(this);
        signoutB = new Button(this);
        signoutB.setBackgroundColor(Color.CYAN);
        signoutB.setText("Sign Out");
        Intent intent = getIntent();
        final String fName = intent.getStringExtra("name");
       // signoutB.setText(fName);

        signoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(Send_Location.this, MainActivity.class);
                startActivity(startNewActivity);
            }
        });

        RelativeLayout.LayoutParams signoutBDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        signoutBDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        signoutBDeets.addRule((RelativeLayout.CENTER_VERTICAL));


        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();
        macaddress.append(lat + " " + lng);
        //macaddress.setVisibility(View.VISIBLE);

        startService(new Intent(getBaseContext(), MyServices.class));

        //sendgpslayout.addView(gps);
        //sendgpslayout.addView(macaddress);
        sendgpslayout.addView(signoutB, signoutBDeets);
        setContentView(sendgpslayout);
    }
}


