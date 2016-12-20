package com.example.ibrahimbakhsh.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private TextView textInfo;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the output text
        textInfo = (TextView) findViewById(R.id.textGPS);
        textInfo.setText("Location : ");


        //finding the button and linking it to the button
        final Button btn = (Button) findViewById(R.id.btnGetGPS);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //textInfo.setText("Location : Clicked" + System.currentTimeMillis());

                //Request Permission from the user:
                ActivityCompat.requestPermissions(MainActivity.this,LOCATION_PERMS,
                        LOCATION_REQUEST);


                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        String text = "My Current Location is:\nLatitude = "
                                + location.getLatitude() + "\nLongitude = "
                                + location.getLongitude();
                        textInfo.setText(text);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };


                //Checking if the application has permission or not
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    System.err.println("No permission! make sure permission is set correctly in manifest & user granted the permission.");
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission Granted!");
        } else {
            System.err.println("Permission denied");
        }
    }

}
