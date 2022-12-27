package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnterTestReportActivity extends AppCompatActivity {

    String[] tests = {"CBC","CXR","RBS","FBS","ECG","Echocardiogram","S Creatinine","Lipid Profile","Iron Profile","S Electrolyte","S Parathyroid","MRI","CT Scan"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapterItems;
    EditText patientID,testReport,reportSummery;
    TextInputLayout layoutPatientID,layoutTestReport,layoutTestMenu,layoutTestSummery;
    String ID,Report,Summary,Test = "",hid,doctorname,docEdu;
    String date = new Date().toString();

    private FirebaseFirestore fStore;
    private DocumentReference patientRef;


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
        setContentView(R.layout.activity_enter_test_report);

        Intent intent = getIntent();
        hid = intent.getStringExtra("HID");
        doctorname = intent.getStringExtra("DNAME");
        docEdu = intent.getStringExtra("EDU");

        autoCompleteTextView = findViewById(R.id.testSelectDropDownBox);

        fStore = FirebaseFirestore.getInstance();

        patientID = findViewById(R.id.testPatientIdInput);
        testReport = findViewById(R.id.testPatientTestResultInput);
        reportSummery = findViewById(R.id.testPatientTestSummeryInput);
        layoutPatientID = findViewById(R.id.testPatientIdInputField);
        layoutTestReport = findViewById(R.id.testPatientTestResultInputField);
        layoutTestMenu = findViewById(R.id.testMenu);
        layoutTestSummery = findViewById(R.id.testSummeryInputField);

        arrayAdapterItems = new ArrayAdapter<String>(this,R.layout.test_list,tests);
        autoCompleteTextView.setAdapter(arrayAdapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Test = parent.getItemAtPosition(position).toString();
            }
        });

        Button saveButton;
        saveButton = findViewById(R.id.testSaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = patientID.getText().toString();
                Report = testReport.getText().toString();
                Summary = reportSummery.getText().toString();

                if(Objects.equals(Test, "")) {
                    layoutTestMenu.setError("Select Test");
                    layoutTestMenu.requestFocus();
                    return;
                }

                if(ID.length() != 9) {
                    layoutPatientID.setError("Invalid Patient ID");
                    layoutPatientID.requestFocus();
                    return;
                }

                if(Report.isEmpty()) {
                    layoutTestReport.setError("Enter Test Report");
                    layoutTestReport.requestFocus();
                    return;
                }

                if(Summary.isEmpty()) {
                    layoutTestSummery.setError("Enter Test Summery");
                    layoutTestSummery.requestFocus();
                    return;
                }

                patientRef = fStore.collection("Patients").document(ID);
                patientRef.addSnapshotListener(EnterTestReportActivity.this,new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String pname = value.getString(KEY_PNAME);

                        if(pname != null) {
                            Map<String,Object> val = new HashMap<>();
                            val.put(KEY_HID,hid);
                            val.put(KEY_NAME,doctorname);
                            val.put(KEY_FIELD,docEdu);
                            val.put(KEY_TEST,Test);
                            val.put(KEY_PID,ID);
                            val.put(KEY_REPORT,Report);
                            val.put(KEY_SUMMARY,Summary);
                            val.put(KEY_DATE,date);

                            patientRef.collection("Tests").add(val).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(EnterTestReportActivity.this,"Test Data Successfully Inserted",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(EnterTestReportActivity.this,MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(EnterTestReportActivity.this,"Unable to upload test data",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(EnterTestReportActivity.this,"Invalid Patient ID",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}