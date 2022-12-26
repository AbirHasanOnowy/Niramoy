package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnterPrescriptionActivity extends AppCompatActivity {
    String hid,doctorname,docEdu,pid,disease,age,medicines,testandrec,date=new Date().toString();
    EditText ePID,eProblems,eAge,eMedicines,eRecommendations;
    TextInputLayout layoutID,layoutProb,layoutAge,layoutMedi,layoutRec;
    MaterialButton saveButton;

    private FirebaseFirestore fStore;
    private DocumentReference patientRef;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_prescription);

        Intent intent = getIntent();
        hid = intent.getStringExtra("HID");
        doctorname = intent.getStringExtra("DNAME");
        docEdu = intent.getStringExtra("EDU");

        fStore = FirebaseFirestore.getInstance();

        ePID = findViewById(R.id.presPatientIdET);
        eProblems = findViewById(R.id.patientProblemsET);
        eAge = findViewById(R.id.patientAgeET);
        eMedicines = findViewById(R.id.medicinesET);
        eRecommendations = findViewById(R.id.testAndRecET);
        layoutID = findViewById(R.id.presPatientId);
        layoutProb = findViewById(R.id.patientProblems);
        layoutAge = findViewById(R.id.patientAge);
        layoutMedi = findViewById(R.id.medicines);
        layoutRec = findViewById(R.id.testAndRec);
        saveButton = findViewById(R.id.prescriptionSaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid = ePID.getText().toString();
                disease = eProblems.getText().toString();
                age = eAge.getText().toString();
                medicines = eMedicines.getText().toString();
                testandrec = eRecommendations.getText().toString();

                if(pid.length() != 9) {
                    layoutID.setError("ID must contain 9 digits");
                    layoutID.requestFocus();
                    return;
                }

                if(disease.isEmpty()) {
                    layoutProb.setError("Enter Patient Problems/Disease");
                    layoutProb.requestFocus();
                    return;
                }

                if(age.isEmpty()) {
                    layoutAge.setError("Enter Patient Age");
                    layoutAge.requestFocus();
                    return;
                }

                if(medicines.isEmpty()) {
                    layoutMedi.setError("Enter Medicines");
                    layoutMedi.requestFocus();
                    return;
                }

                if(testandrec.isEmpty()) {
                    layoutRec.setError("Enter test and recommendations");
                    layoutRec.requestFocus();
                    return;
                }

                patientRef = fStore.collection("Patients").document(pid);
                patientRef.addSnapshotListener(EnterPrescriptionActivity.this,new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String pname = value.getString(KEY_PNAME);

                        if(pname != null) {
                            Map<String,Object> val = new HashMap<>();
                            val.put(KEY_HID,hid);
                            val.put(KEY_NAME,doctorname);
                            val.put(KEY_FIELD,docEdu);
                            val.put(KEY_PROB,disease);
                            val.put(KEY_PID,pid);
                            val.put(KEY_AGE,age);
                            val.put(KEY_MED,medicines);
                            val.put(KEY_REC,testandrec);
                            val.put(KEY_DATE,date);

                            patientRef.collection("Prescriptions").add(val).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(EnterPrescriptionActivity.this,"Prescription Data Successfully Uploaded",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(EnterPrescriptionActivity.this,MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(EnterPrescriptionActivity.this,"Unable to upload prescription data",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(EnterPrescriptionActivity.this,"Invalid Patient ID",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}