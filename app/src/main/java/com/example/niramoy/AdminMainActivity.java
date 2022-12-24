package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminMainActivity extends AppCompatActivity {
    FloatingActionButton addHospitalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        addHospitalButton=findViewById(R.id.addHospitalButton);
        addHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddHospitalActivityAdmin.class));
            }
        });

    }
}