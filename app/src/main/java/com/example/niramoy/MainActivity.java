package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton, addTestButton, addPatientButton;
    Boolean isVis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        addTestButton = findViewById(R.id.addTestButton);
        addPatientButton = findViewById(R.id.addPatientButton);

        isVis = false;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVis){
                    addPatientButton.show();
                    addTestButton.show();
                    isVis = true;
                }else {
                    addTestButton.hide();
                    addPatientButton.hide();
                    isVis = false;
                }
            }
        });
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EnterTestReportActivity.class));
            }
        });

    }
}