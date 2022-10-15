package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EnterTestReportActivity extends AppCompatActivity {

    String[] tests = {"ECG","RA","Glucose level","Blood","etc"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapterItems;
    TextInputEditText patientID,testReport,reportSummery;
    TextInputLayout layoutPatientID,layoutTestReport,layoutTestMenu;
    String ID,Report,Summery,Test = "";
    boolean checkInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_test_report);

        autoCompleteTextView = findViewById(R.id.testSelectDropDownBox);



        patientID = findViewById(R.id.testPatientIdInput);
        testReport = findViewById(R.id.testPatientTestResultInput);
        reportSummery = findViewById(R.id.testPatientTestSummeryInput);
        layoutPatientID = findViewById(R.id.testPatientIdInputField);
        layoutTestReport = findViewById(R.id.testPatientTestResultInputField);
        layoutTestMenu = findViewById(R.id.testMenu);

        /*if(ID==""){ checkInput = false;}
        if(Report==""){ checkInput = false;}
        if(Summery==""){ checkInput = false;}*/

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

                if(Test == "") {
                    layoutTestMenu.setError("Select Test");

                } else {
                    if(patientID.getText()!= null) {
                        ID = patientID.getText().toString();

                        if(testReport.getText()!= null) {
                            Report = testReport.getText().toString();

                            if(reportSummery.getText()!=null) {
                                Summery = reportSummery.getText().toString();
                            } else { Summery = "";}

                            startActivity(new Intent(EnterTestReportActivity.this, MainActivity.class));
                        } else {
                            layoutTestReport.setError("Enter Test Result");
                        }
                    } else {
                        layoutPatientID.setError("Enter Valid Patient ID");
                    }
                }
            }
        });
    }
}