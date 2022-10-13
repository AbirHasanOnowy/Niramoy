package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class EnterTestReportActivity extends AppCompatActivity {

    String[] tests = {"ECG","RA","Glucose level","Blood","etc"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_test_report);

        arrayAdapterItems = new ArrayAdapter<String>(this,R.layout.test_list,tests);
        autoCompleteTextView.setAdapter(arrayAdapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String test = parent.getItemAtPosition(position).toString();
            }
        });
    }
}