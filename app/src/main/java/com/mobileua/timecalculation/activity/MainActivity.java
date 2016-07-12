package com.mobileua.timecalculation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobileua.timecalculation.R;
import com.mobileua.timecalculation.utils.TimeUtil;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeUtil util = TimeUtil.getInstance();
                util.saveLoginTime("ntp.time.in.ua");
                Toast.makeText(getApplicationContext(), "User login: " + util.getValidTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeUtil util = TimeUtil.getInstance();
                Date validTime = util.getValidTime();
                Toast.makeText(getApplicationContext(), "Make some operation: " + validTime.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
