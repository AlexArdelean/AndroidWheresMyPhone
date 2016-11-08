package com.example.alexssd.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
//import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GPSLocationService extends Service {

    private LocationManager locationManager;
    private LocationListener listener;
    double lat = 0;
    double lng = 0;

    private class Network extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                Socket ss = new Socket("10.25.13.130", 3001);
                SharedPreferences prefs = getSharedPreferences("LOCATION", MODE_PRIVATE);
                int dev = 0;
                if (prefs.contains("deviceNum")) {
                    dev = prefs.getInt("deviceNum", -1);
                }
                DataOutputStream out = new DataOutputStream(ss.getOutputStream());
                out.writeBytes(dev + " " + strings[1] + " " + strings[2] + " " + strings[3] + "\n");
                DataInputStream sockIn = new DataInputStream(ss.getInputStream());
                int d = sockIn.readInt();
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("deviceNum", d);
                edit.commit();
                ss.close();
            } catch (Exception e) {
                Log.e("MYAPP", "EXCEPTION", e);
            }
            return null;
        }
    }

    //@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

        //////the location gps stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {
            Location l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            new GPSLocationService.Network().execute("14", fName, l.getLatitude()+"", l.getLongitude()+"");
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLatitude();
                new GPSLocationService.Network().execute("14", fName, location.getLongitude()+"", location.getLatitude()+"");
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
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        /////////////////end of gps stuff
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
