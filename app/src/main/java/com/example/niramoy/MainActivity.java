package com.example.niramoy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu;
    TextView navNameTextView,navPositionTextView;
    FloatingActionButton addButton, addTestButton, addPatientButton,addPrescriptionButton;
    Boolean isVis;
    String uid,position,name,hid,email,dept,education,gender,birthday,password,pname,pid,pgen,pbir,pimglink;
    LinearLayout layoutProfile;
    TextView seeTest, seePrescriptions,tPid,tPgen,tPbir,tPname;
    TextInputLayout layoutsearch;
    EditText ePid;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference uidref,patientRef;

    private static final String KEY_HID = "HID";
    private static final String KEY_POS = "Position";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASS = "Password";
    private static final String KEY_DEPT = "Dept";
    private static final String KEY_EDU = "Education";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_DOB = "DoB";
    private static final String KEY_VERIFY = "Verified";
    private static final String KEY_UID = "Uid";

    private static final String KEY_PID = "PID";
    private static final String KEY_PNAME = "Name";
    private static final String KEY_PGENDER = "Gender";
    private static final String KEY_PDOB = "DoB";
    private static final String KEY_URL = "ImageUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        addTestButton = findViewById(R.id.addTestButton);
        addPatientButton = findViewById(R.id.addPatientButton);
        addPrescriptionButton=findViewById(R.id.addPrescriptionButton);
        seeTest=findViewById(R.id.seeTestsTV);
        seePrescriptions=findViewById(R.id.seePrescriptionsTV);
        tPid = findViewById(R.id.patientIdTV);
        tPgen = findViewById(R.id.patientGenderTV);
        tPbir = findViewById(R.id.patientBirthdateTV);
        tPname = findViewById(R.id.patientNameTV);
        layoutsearch = findViewById(R.id.searchETLayout);
        ePid = findViewById(R.id.patientSearchById);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        navDrawer();

        uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        uidref = fStore.collection("UID").document(uid);
        uidref.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                hid = value.getString(KEY_HID);
                position = value.getString(KEY_POS);
                name = value.getString(KEY_NAME);
                email = value.getString(KEY_EMAIL);
                dept = value.getString(KEY_DEPT);
                password = value.getString(KEY_PASS);
                education = value.getString(KEY_EDU);
                gender = value.getString(KEY_GENDER);
                birthday =  value.getString(KEY_DOB);
            }
        });

        layoutsearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid = ePid.getText().toString();
                if(pid == null) {
                    Toast.makeText(MainActivity.this,"Enter PAtient Id to search patient",Toast.LENGTH_SHORT).show();
                    return;
                }
                patientRef = fStore.collection("Patients").document(pid);
                patientRef.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        pname = value.getString(KEY_PNAME);
                        if(pname == null) {
                            Toast.makeText(MainActivity.this,"Invalid Patient Id",Toast.LENGTH_SHORT).show();

                        } else {
                            pbir = value.getString(KEY_PDOB);
                            pgen = value.getString(KEY_PGENDER);
                            pimglink = value.getString(KEY_URL);
                            tPid.setText(pid);
                            tPname.setText(pname);
                            tPgen.setText(pgen);
                            tPbir.setText(pbir);
                        }
                    }
                });
            }
        });
        seeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pname == null) {
                    Toast.makeText(MainActivity.this,"Enter Patient Id and Search patient first",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this,ShowTestsActivity.class);
                intent.putExtra(KEY_PID,pid);
                startActivity(intent);
            }
        });
        seePrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pname == null) {
                    Toast.makeText(MainActivity.this,"Enter Patient Id and Search patient first",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this,ShowPrescriptionsActivity.class);
                intent.putExtra(KEY_PID,pid);
                startActivity(intent);
            }
        });

        addPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prescribe = new Intent(MainActivity.this, EnterPrescriptionActivity.class);
                prescribe.putExtra("HID",hid);
                prescribe.putExtra("DNAME",name);
                prescribe.putExtra("EDU",education);
                startActivity(prescribe);
            }
        });

        isVis = false;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(position, "Receptionist"))
                {
                    startActivity(new Intent(MainActivity.this, EnterPatientActivity.class));
                }
                else if(!isVis && Objects.equals(position, "Doctor")){
                    showButtons();
                }else {
                    hideButtons();
                }
            }
        });
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainActivity.this,EnterTestReportActivity.class);
                test.putExtra("HID",hid);
                test.putExtra("DNAME",name);
                test.putExtra("EDU",education);
                startActivity(test);
            }
        });
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EnterPatientActivity.class));
            }
        });

    }

//    private void receptionist() {
//        if(Objects.equals(position, "Receptionist"))
//        {
//            Drawable drawable = addButton.getBackground();
//            drawable=DrawableCompat.wrap(drawable);
//            DrawableCompat.setTint(drawable,ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));
//            addButton.setBackground(drawable);
//            addButton.setImageResource(R.drawable.ic_baseline_person_add_alt_1_24);
//            Toast.makeText(MainActivity.this,"position "+position,Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this,"position "+position,Toast.LENGTH_SHORT).show();
//        }
//    }


    private void navDrawer() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logOutButton:
                        Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        fAuth.signOut();
                        startActivity(new Intent(MainActivity.this,SignInActivity.class));
                        finish();
                        break;

                    case R.id.policyButton:
                        Toast.makeText(MainActivity.this, "Policy", Toast.LENGTH_SHORT).show();
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

                drawerLayout.openDrawer(GravityCompat.START);
                navNameTextView = findViewById(R.id.navName);
                navPositionTextView = findViewById(R.id.navPosition);
                navNameTextView.setText(name);
                navPositionTextView.setText(position);

                layoutProfile = findViewById(R.id.drawarHeader);
                layoutProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profile = new Intent(MainActivity.this,UserProfileActivity.class);
                        profile.putExtra("HID",hid);
                        profile.putExtra("Position",position);
                        profile.putExtra("Name",name);
                        profile.putExtra("Email",email);
                        profile.putExtra("Dept",dept);
                        profile.putExtra("Pass",password);
                        profile.putExtra("Edu",education);
                        profile.putExtra("Gender",gender);
                        profile.putExtra("Birthday",birthday);
                        startActivity(profile);

                    }
                });
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
        addTestButton.show();
        addPrescriptionButton.show();
//        if(Objects.equals(position, "Receptionist")) {
//            addTestButton.setVisibility(View.GONE);
//            addPrescriptionButton.setVisibility(View.GONE);
//        }
        addPatientButton.show();

        Drawable drawable = addButton.getBackground();
        drawable=DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,ContextCompat.getColor(this,R.color.black));
        addButton.setBackground(drawable);
        addButton.setImageResource(R.drawable.ic_baseline_cancel_24);
       // addButton.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

        isVis = true;
    }
}