package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class CreateAccountActivity extends AppCompatActivity {

    String[] genderList = {"Male","Female","Other"};
    String[] doctorList = {"Tonmoy","Foysal","Choyon"};
    AutoCompleteTextView genderAutoCompleteTextView,doctorAutoCompleteTextView;
    ArrayAdapter<String> genderArrayAdapterItems,doctorArrayAdapterItems;
    EditText eUserName,eEmail,ePassword;
    TextInputLayout layoutUserName,layoutEmail,layoutPassword,layoutGender,layoutDoctor;
    MaterialButton confirmButton;
    String name,email,password,Gender = "",Doctor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        genderAutoCompleteTextView = findViewById(R.id.genderSelectDropDownBox);
        doctorAutoCompleteTextView = findViewById(R.id.doctorSelectDropDownBox);
        eUserName = findViewById(R.id.caUserNameInput);
        eEmail = findViewById(R.id.caEmailInput);
        ePassword = findViewById(R.id.caPasswordInput);
        layoutUserName = findViewById(R.id.caUserNameInputField);
        layoutEmail = findViewById(R.id.caEmailLayout);
        layoutPassword = findViewById(R.id.caPasswordLayout);
        layoutGender = findViewById(R.id.genderMenu);
        layoutDoctor = findViewById(R.id.doctorMenu);
        confirmButton =findViewById(R.id.confirmButton);

        genderArrayAdapterItems = new ArrayAdapter<String>(this,R.layout.gender_list,genderList);
        genderAutoCompleteTextView.setAdapter(genderArrayAdapterItems);

        genderAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gender = parent.getItemAtPosition(position).toString();
            }
        });

        doctorArrayAdapterItems = new ArrayAdapter<String>(this,R.layout.doctor_list,doctorList);
        doctorAutoCompleteTextView.setAdapter(doctorArrayAdapterItems);

        doctorAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctor = parent.getItemAtPosition(position).toString();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eUserName.length() != 0) {
                    if(eEmail.length() != 0) {
                        if(ePassword.length() != 0) {
                            if(Gender != "") {
                                if(Doctor != "") {
                                    startActivity(new Intent(CreateAccountActivity.this,SignInActivity.class));
                                } else {
                                    layoutDoctor.setError("Select Doctor");
                                }
                            } else {
                                layoutGender.setError("Select Gender");
                            }
                        } else {
                            layoutPassword.setError("Enter Password");
                        }
                    } else {
                        layoutEmail.setError("Enter Valid Email");
                    }
                } else {
                    layoutUserName.setError("Enter User Name");
                }
            }
        });
    }
}