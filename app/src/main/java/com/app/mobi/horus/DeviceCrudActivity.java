package com.app.mobi.horus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DeviceCrudActivity extends AppCompatActivity {

    Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_crud);

        btnMapa =(Button) findViewById(R.id.btnOptMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DeviceCrudActivity.this, MapAct.class);
                startActivity(intent);
            }
        });

    }
}
