package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.niramoy.adapters.PrescriptionAdapter;
import com.example.niramoy.adapters.TestsAdapter;
import com.example.niramoy.classes.DirectorAdminClass;
import com.example.niramoy.classes.PrescriptionClass;
import com.example.niramoy.classes.TestClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowTestsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TestClass> directorArrayLIst;
    TestsAdapter testAdapter;
    private LinearLayoutManager linearLayoutManager;
    TestClass testClass;
    String pid;

    private FirebaseFirestore fStore;

    private static final String KEY_PPID = "PID";
    private static final String KEY_PNAME = "Name";
    private static final String KEY_HID = "Hospital_ID";
    private static final String KEY_NAME = "Doctor_Name";
    private static final String KEY_FIELD = "Field";
    private static final String KEY_TEST = "Test_Name";
    private static final String KEY_PID = "Patient_ID";
    private static final String KEY_REPORT = "Test_Report";
    private static final String KEY_SUMMARY = "Test_Summery";
    private static final String KEY_DATE = "Date-Time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tests);

        fStore = FirebaseFirestore.getInstance();
        Intent data = getIntent();
        pid = data.getStringExtra(KEY_PPID);

        directorArrayLIst=new ArrayList<>();

        fStore.collection("Patients").document(pid).collection("Tests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                testClass=new TestClass(document.getString(KEY_TEST),document.getString(KEY_PID),document.getString(KEY_NAME),document.getString(KEY_FIELD),document.getString(KEY_HID),document.getString(KEY_REPORT),document.getString(KEY_SUMMARY),document.getString(KEY_DATE));
                                directorArrayLIst.add(testClass);
                            }
                            testAdapter.notifyDataSetChanged();
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(ShowTestsActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        recyclerView = findViewById(R.id.rvShowTests);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        testAdapter = new TestsAdapter(ShowTestsActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(testAdapter);
    }
}