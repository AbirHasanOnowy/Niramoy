package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PreciseDataConnectionState;
import android.widget.Toast;

import com.example.niramoy.adapters.DirectorRvAdapter;
import com.example.niramoy.adapters.PrescriptionAdapter;
import com.example.niramoy.classes.DirectorMainClass;
import com.example.niramoy.classes.PrescriptionClass;
import com.example.niramoy.classes.TestClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowPrescriptionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PrescriptionClass> directorArrayLIst;
    PrescriptionAdapter prescriptionAdapter;
    private LinearLayoutManager linearLayoutManager;
    PrescriptionClass prescriptionClass;
    String pid;

    private static final String KEY_PPID = "PID";
    private static final String KEY_PNAME = "Name";
    private static final String KEY_HID = "Hospital_ID";
    private static final String KEY_NAME = "Doctor_Name";
    private static final String KEY_FIELD = "Field";
    private static final String KEY_PROB = "Problems";
    private static final String KEY_PID = "Patient_ID";
    private static final String KEY_AGE = "Age";
    private static final String KEY_MED = "Medicines";
    private static final String KEY_REC = "Tests_and_Recommendations";
    private static final String KEY_DATE = "Date-Time";

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prescriptions);

        fStore = FirebaseFirestore.getInstance();
        Intent data = getIntent();
        pid = data.getStringExtra(KEY_PPID);

        Toast.makeText(ShowPrescriptionsActivity.this,"Unable"+pid,Toast.LENGTH_LONG).show();


        directorArrayLIst=new ArrayList<>();

        fStore.collection("Patients").document(pid).collection("Prescriptions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                prescriptionClass=new PrescriptionClass(document.getString(KEY_PID),document.getString(KEY_NAME),document.getString(KEY_FIELD),document.getString(KEY_AGE),document.getString(KEY_HID),document.getString(KEY_MED),document.getString(KEY_PROB),document.getString(KEY_REC),document.getString(KEY_DATE));
                                directorArrayLIst.add(prescriptionClass);
                            }
                            prescriptionAdapter.notifyDataSetChanged();
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(ShowPrescriptionsActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        recyclerView = findViewById(R.id.rvShowPres);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        prescriptionAdapter = new PrescriptionAdapter(ShowPrescriptionsActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(prescriptionAdapter);

    }
}