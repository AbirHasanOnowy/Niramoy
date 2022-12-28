package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niramoy.adapters.AdminAdapter;
import com.example.niramoy.adapters.DirectorRvAdapter;
import com.example.niramoy.classes.DirectorAdminClass;
import com.example.niramoy.classes.DirectorMainClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class DirectorMainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu;
    TextView navNameTextView,navPositionTextView;
    String uid,position,name,hid,email,dept,education,gender,birthday,password;
    String euid,epos,ename,ehid,eMail,edept,eEdu,eGen,eBir;

    RecyclerView recyclerView;
    ArrayList<DirectorMainClass> directorArrayLIst;
    DirectorRvAdapter adminAdapter;
    private LinearLayoutManager linearLayoutManager;
    DirectorMainClass directorMainClass;
    LinearLayout layoutProfile;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference uidref,employeeRef;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_main);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        navDrawer();

        directorArrayLIst=new ArrayList<>();
        recyclerView = findViewById(R.id.directorRV);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adminAdapter = new DirectorRvAdapter(DirectorMainActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adminAdapter);



        adminAdapter.setCustomClickListener(new DirectorRvAdapter.CustomClickListener() {
            @Override
            public void customOnClick(int position, View v) {
                Intent intent = new Intent(DirectorMainActivity.this,DirectorShowDetailsActivity.class);
                DirectorMainClass directorMainClass1;
                directorMainClass1=directorArrayLIst.get(position);
                intent.putExtra(KEY_HID,directorMainClass1.getHospitalID());
                intent.putExtra(KEY_POS,directorMainClass1.getEmployeePosition());
                intent.putExtra(KEY_NAME,directorMainClass1.getEmployeeName());
                intent.putExtra(KEY_EMAIL,directorMainClass1.getEmployeeEmail());
                intent.putExtra(KEY_DEPT,directorMainClass1.getEmployeeDept());
                intent.putExtra(KEY_EDU,directorMainClass1.getEmployeeEdu());
                intent.putExtra(KEY_GENDER,directorMainClass1.getEmployeeGender());
                intent.putExtra(KEY_DOB,directorMainClass1.getEmployeeBirthdate());
                intent.putExtra(KEY_VERIFY,directorMainClass1.getIsVarified());
                intent.putExtra(KEY_UID,directorMainClass1.getEmployeeId());
                startActivity(intent);
            }

            @Override
            public void customOnLongClick(int position, View v) {

            }
        });

        uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        uidref = fStore.collection("UID").document(uid);
        uidref.addSnapshotListener(DirectorMainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                hid = value.getString(KEY_HID);
                position = value.getString(KEY_POS);
                name = value.getString(KEY_NAME);
                email = value.getString(KEY_EMAIL);
                password = value.getString(KEY_PASS);
                dept = value.getString(KEY_DEPT);
                education = value.getString(KEY_EDU);
                gender = value.getString(KEY_GENDER);
                birthday =  value.getString(KEY_DOB);
                fStore.collection("Hospitals").document(hid).collection("Doctor")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        euid = document.getString("Uid");

                                        employeeRef = fStore.collection("UID").document(euid);
                                        employeeRef.addSnapshotListener(DirectorMainActivity.this, new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                directorMainClass= new DirectorMainClass(value.getString(KEY_EMAIL),value.getId(),value.getString(KEY_NAME),value.getString(KEY_POS),value.getString(KEY_HID),value.getString(KEY_VERIFY),value.getString(KEY_DEPT),value.getString(KEY_DOB),value.getString(KEY_EDU),value.getString(KEY_GENDER));
                                                directorArrayLIst.add(directorMainClass);
                                                adminAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(DirectorMainActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                fStore.collection("Hospitals").document(hid).collection("Receptionist")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        euid = document.getString("Uid");

                                        employeeRef = fStore.collection("UID").document(euid);
                                        employeeRef.addSnapshotListener(DirectorMainActivity.this, new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                directorMainClass= new DirectorMainClass(value.getString(KEY_EMAIL),value.getId(),value.getString(KEY_NAME),value.getString(KEY_POS),value.getString(KEY_HID),value.getString(KEY_VERIFY),value.getString(KEY_DEPT),value.getString(KEY_DOB),value.getString(KEY_EDU),value.getString(KEY_GENDER));
                                                directorArrayLIst.add(directorMainClass);
                                                adminAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(DirectorMainActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                fStore.collection("Hospitals").document(hid).collection("Nurse")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        euid = document.getString("Uid");

                                        employeeRef = fStore.collection("UID").document(euid);
                                        employeeRef.addSnapshotListener(DirectorMainActivity.this, new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                directorMainClass= new DirectorMainClass(value.getString(KEY_EMAIL),value.getId(),value.getString(KEY_NAME),value.getString(KEY_POS),value.getString(KEY_HID),value.getString(KEY_VERIFY),value.getString(KEY_DEPT),value.getString(KEY_DOB),value.getString(KEY_EDU),value.getString(KEY_GENDER));
                                                directorArrayLIst.add(directorMainClass);
                                                adminAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(DirectorMainActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

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
                        fAuth.signOut();
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(DirectorMainActivity.this,SignInActivity.class));
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
                navNameTextView = findViewById(R.id.navName);
                navPositionTextView = findViewById(R.id.navPosition);
                navNameTextView.setText(name);
                navPositionTextView.setText(position);

                layoutProfile = findViewById(R.id.drawarHeader);
                layoutProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profile = new Intent(DirectorMainActivity.this,UserProfileActivity.class);
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
}