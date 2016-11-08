package com.example.alexssd.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {
    //For location services
    private LocationManager locationManager;
    private LocationListener listener;
    private Button signinButton;
    //private EditText gps;
    private TextView gps;
    private TextView macaddress;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources r = getResources();
        int px;

        //Layout
        RelativeLayout loginLayout = new RelativeLayout(this);

        //Middle button used to relatively layout
        Button middle = new Button(this);
        middle.setVisibility(View.INVISIBLE);

        /////mac address used later
        macaddress = new TextView(this);
        //macaddress.setVisibility(View.INVISIBLE);

        //t for gps location
        gps = new TextView(this);
        //gps = new EditText(this);

        //Username input
        username = new EditText(this);
        username.setHint("Account Email");
        username.setSingleLine();

        //password input
        EditText password = new EditText(this);
        password.setHint("Password");
        password.setSingleLine();
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        //Sign in button
        signinButton = new Button(this);
        signinButton.setBackgroundColor(Color.CYAN);
        signinButton.setText("Sign In");
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do on click
            }
        });

        //set ids
        signinButton.setId(1);
        username.setId(2);
        password.setId(3);
        middle.setId(4);
        gps.setId(5);

        RelativeLayout.LayoutParams middleDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams signinButtonDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams usernameDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams passwordDeets = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );


        //middle button deets
        middleDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        middleDeets.addRule(RelativeLayout.CENTER_VERTICAL);

        //signin button deets
        signinButtonDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        signinButtonDeets.addRule(RelativeLayout.BELOW, middle.getId());
        signinButtonDeets.setMargins(0, 400, 0, 0);

        //position password
        passwordDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        passwordDeets.addRule(RelativeLayout.BELOW, middle.getId());
        passwordDeets.setMargins(0, 0, 0, 0);

        //position username
        usernameDeets.addRule(RelativeLayout.ABOVE, middle.getId());
        usernameDeets.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usernameDeets.setMargins(0, 0, 0, 0);


        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, r.getDisplayMetrics());
        username.setWidth(px);
        password.setWidth(px);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 225, r.getDisplayMetrics());
        signinButton.setWidth(px);
        //gps.setWidth(px);


        //////the location gps stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gps.append("\n " + location.getLongitude() + " " + location.getLatitude());
                //gps.setHint(location.getLongitude() + " " + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button(); //used for gps
        //loginLayout.addView(gps);
        //loginLayout.addView(macaddress);
        loginLayout.addView(middle, middleDeets);
        //loginLayout.addView(password, passwordDeets);
        loginLayout.addView(username, usernameDeets);
        loginLayout.addView(signinButton, signinButtonDeets);
        setContentView(loginLayout);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                Intent startNewActivity = new Intent(MainActivity.this, Send_Location.class);
                startNewActivity.putExtra("name", username.getText().toString()+"");
                startActivity(startNewActivity);

            }
        });
    }
}

