package joandev.sensorexample;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class GPSActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    List<Address> addressList;
    LocationManager locationManager;
    LocationListener locationListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressList = null;
        setContentView(R.layout.main);
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                Geocoder gc = new Geocoder(getApplicationContext());
                try {
                    //5 mxresults
                    addressList = gc.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < addressList.size(); ++i) {
                    TextView t = (TextView) findViewById(R.id.textView);
                    if (i == 0) t.setText("");
                    t.setText(t.getText() + "\n" + addressList.get(i).getAddressLine(0));
                }
            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
            return;
        }
        //provider, tiempo, distancia, listener
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
//        locationManager.removeGpsStatusListener(locationListener);

        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
}