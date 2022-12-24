package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DirectorMainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        navDrawer();
    }

    private void navDrawer() {
        toggle = new ActionBarDrawerToggle(DirectorMainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logOutButton:
                        Toast.makeText(DirectorMainActivity.this,"Log out", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.policyButton:
                        Toast.makeText(DirectorMainActivity.this, "Policy", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                }

                return false;
            }
        });
        // App Bar Click Event
        imageMenu = findViewById(R.id.imageMenu);
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}