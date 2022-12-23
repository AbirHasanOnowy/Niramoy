package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class EnterPrescriptionActivity extends AppCompatActivity {
    String[] genders = {"Male","Female","Others"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    String patientGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_prescription);
        autoCompleteTextView = findViewById(R.id.patientGenderET);
        arrayAdapter=new ArrayAdapter<>(this,R.layout.gender_list,genders);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientGender=parent.getItemAtPosition(position).toString();
            }
        });

    }
}