package com.example.niramoy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu;
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


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mHome:
                        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.mShare:
                        Toast.makeText(MainActivity.this, "Facebook", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                }

                return false;
            }
        });
        //------------------------------
        // ------------------------
        // App Bar Click Event
        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



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
                startActivity(new Intent(MainActivity.this, EnterPatientActivity.class));
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
        DrawableCompat.setTint(drawable,ContextCompat.getColor(this,R.color.gray));
        addButton.setBackground(drawable);
        addButton.setImageResource(R.drawable.ic_baseline_cancel_24);
       // addButton.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

        isVis = true;
    }
}