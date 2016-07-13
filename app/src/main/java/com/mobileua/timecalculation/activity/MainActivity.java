package com.mobileua.timecalculation.activity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.mobileua.timecalculation.R;
import com.mobileua.timecalculation.utils.TimeUtil;

import java.util.Date;

public class MainActivity extends Activity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{

    private GoogleApiClient googleApiClient=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        final TextView textView=(TextView)findViewById(R.id.textView);
        final  TextView textArea=(TextView)findViewById(R.id.textView3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeUtil util = TimeUtil.getInstance();
                util.saveLoginTime("ntp.time.in.ua");
                textView.setText(util.getValidTime().toString());
            }
        });

        final TextView textView1=(TextView)findViewById(R.id.textView2);
        Button button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeUtil util = TimeUtil.getInstance();
                textView1.setText(util.getValidTime().toString());
                if (googleApiClient.isConnected()){
                    try {
                        Location location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        textArea.append("Time "+util.getValidTime().toString()+"Latitude "+Double.toString(location.getLatitude())+
                                " Longitude "+Double.toString(location.getLongitude())+"/n");
                    }catch (SecurityException e){

                    }
                }
            }
        });
        if (googleApiClient==null){
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (location != null) {
                Log.e("Location","");
            }
        }catch (SecurityException e){

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }
}
