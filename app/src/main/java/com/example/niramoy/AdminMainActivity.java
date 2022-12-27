package com.example.niramoy;

import androidx.annotation.NonNull;
import com.example.niramoy.adapters.AdminAdapter;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.niramoy.classes.DirectorAdminClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminMainActivity extends AppCompatActivity {
    FloatingActionButton addHospitalButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView navNameTextView,navPositionTextView;

    RecyclerView recyclerView;
    ArrayList<DirectorAdminClass> directorArrayLIst;
    AdminAdapter adminAdapter;
    private LinearLayoutManager linearLayoutManager;
    DirectorAdminClass directorAdminClass;

    ImageView imageMenu;
    String uid,position,name,hid,email,dept,education,gender,birthday,password;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference uidref,dirUidRef;
    private static final String KEY_POS = "Position";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASS = "Password";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_DOB = "DoB";
    private static final String KEY_VERIFY = "Verified";
    private static final String KEY_UID = "Uid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        addHospitalButton=findViewById(R.id.addHospitalButton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        navDrawer();

        directorArrayLIst=new ArrayList<>();

        fStore.collection("Admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                directorAdminClass=new DirectorAdminClass(document.getString("Email"),document.getString("Uid"),document.getString("Name"),document.getString("HID"));
                                directorArrayLIst.add(directorAdminClass);
                            }
                            adminAdapter.notifyDataSetChanged();
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(AdminMainActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                        }
                    }
                });



        recyclerView = findViewById(R.id.adminRV);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adminAdapter = new AdminAdapter(AdminMainActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adminAdapter);
        adminAdapter.setCustomClickListener(new AdminAdapter.CustomClickListener() {
            @Override
            public void customOnClick(int position, View v) {

            }

            @Override
            public void customOnLongClick(int position, View v) {

            }

            @Override
            public void onAcceptClick(int position) {
                DirectorAdminClass data = directorArrayLIst.get(position);

                dirUidRef = fStore.collection("UID").document(data.getDirectorId());
                Map<String, Object> ver = new HashMap<>();
                ver.put(KEY_VERIFY,"Yes");
                dirUidRef.update(ver).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AdminMainActivity.this,"Director Successfully Assigned",Toast.LENGTH_LONG).show();
                    }
                });

                dirUidRef = fStore.collection("Hospitals")
                        .document(data.getHospitalID()).collection("Director").document(data.getDirectorId());
                ver.put(KEY_UID,data.getDirectorId());
                dirUidRef.set(ver);
                fStore.collection("Admin").document(data.getDirectorId()).delete();
                adminAdapter.notifyItemRemoved(position);
                adminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDeleteClick(int position) {
                DirectorAdminClass data = directorArrayLIst.get(position);
                fStore.collection("Admin").document(data.getDirectorId()).delete();
                adminAdapter.notifyItemRemoved(position);
                adminAdapter.notifyDataSetChanged();
                fStore.collection("UID").document(data.getDirectorId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AdminMainActivity.this,"Director Successfully Deleted",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        uidref = fStore.collection("UID").document(uid);
        uidref.addSnapshotListener(AdminMainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                position = value.getString(KEY_POS);
                name = value.getString(KEY_NAME);
                email = value.getString(KEY_EMAIL);
                password = value.getString(KEY_PASS);
                gender = value.getString(KEY_GENDER);
                birthday =  value.getString(KEY_DOB);

            }
        });

        addHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddHospitalActivityAdmin.class));
            }
        });

    }

    private void navDrawer() {
        toggle = new ActionBarDrawerToggle(AdminMainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logOutButton:
                        Toast.makeText(AdminMainActivity.this,"Log out", Toast.LENGTH_SHORT).show();
                        fAuth.signOut();
                        startActivity(new Intent(AdminMainActivity.this,SignInActivity.class));
                        drawerLayout.closeDrawers();
                        finish();
                        break;

                    case R.id.policyButton:
                        Toast.makeText(AdminMainActivity.this, "Policy", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.directorListButton:
                        //Toast.makeText(AdminMainActivity.this, "Director List", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminMainActivity.this,AdminDirectorListActivity.class));
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
            }
        });
    }
}