package com.example.niramoy;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton, addTestButton, addPatientButton,addPrescriptionButton;
    Boolean isVis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        addTestButton = findViewById(R.id.addTestButton);
        addPatientButton = findViewById(R.id.addPatientButton);
        addPrescriptionButton=findViewById(R.id.addPrescriptionButton);
        addPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EnterPrescriptionActivity.class));
            }
        });

        isVis = false;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVis){
                    showButtons();
                }else {
                    hideButtons();
                }
            }
        });
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EnterTestReportActivity.class));
            }
        });
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EnterPatientActivity.class));
            }
        });

    }

    private void hideButtons() {
        addTestButton.hide();
        addPatientButton.hide();
        addPrescriptionButton.hide();
        Drawable drawable = addButton.getBackground();
        drawable=DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,ContextCompat.getColor(this,R.color.colorPrimary));
        addButton.setBackground(drawable);
        addButton.setImageResource(R.drawable.ic_baseline_add_24);
        isVis = false;
    }

    private void showButtons() {
        addPatientButton.show();
        addTestButton.show();
        addPrescriptionButton.show();
        Drawable drawable = addButton.getBackground();
        drawable=DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,ContextCompat.getColor(this,R.color.black));
        addButton.setBackground(drawable);
        addButton.setImageResource(R.drawable.ic_baseline_cancel_24);
       // addButton.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

        isVis = true;
    }
}