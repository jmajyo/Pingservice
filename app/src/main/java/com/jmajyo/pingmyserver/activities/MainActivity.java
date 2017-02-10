package com.jmajyo.pingmyserver.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jmajyo.pingmyserver.R;
import com.jmajyo.pingmyserver.services.PingService;

public class MainActivity extends AppCompatActivity {
    private Button launchServiceButton;
    private EditText editText;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchServiceButton = (Button) findViewById(R.id.activity_main___launch_button);
        editText = (EditText) findViewById(R.id.activity_main___text);
        stopButton = (Button) findViewById(R.id.activity_main___stop_button);

        launchServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PingService.startPingService(view.getContext(), editText.getText().toString(), 5*1000);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PingService.stopPingService(view.getContext());
            }
        });

    }
}
