package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        TextView textView = findViewById(R.id.textView);

        //MyLocationListener.SetUpLocationListener(this);

        //Double s = MyLocationListener.imHere.getLatitude();

        //textView.setText(s.toString());

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();

        //Проверяем есть ли у приложения разрешение на получение координат
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Нет разрешения на использование текущего положения.", Toast.LENGTH_SHORT).show();
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            StringBuilder sb = new StringBuilder();
            sb.append("Longitude: " + loc.getLongitude() + System.lineSeparator());
            sb.append("Latitude: " + loc.getLatitude() + System.lineSeparator());
            sb.append("Provider: " + loc.getProvider() + System.lineSeparator());
            sb.append("Accuracy: " + loc.getAccuracy() + System.lineSeparator());
            sb.append("Altitude: " + loc.getAltitude() + System.lineSeparator());
            sb.append("Bearing: " + loc.getBearing() + System.lineSeparator());
            sb.append("Speed: " + loc.getSpeed() + System.lineSeparator());
            sb.append("Time: " + loc.getTime() + System.lineSeparator());

            TextView textView = findViewById(R.id.textView);
            textView.setText(sb.toString());
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}