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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpReceptionistActivity extends AppCompatActivity {

    String[] genderList = {"Male","Female","Others"};
    AutoCompleteTextView genderAutoCompleteTextView;
    ArrayAdapter<String> genderArrayAdapterItems;
    EditText eName,eEmail,ePassword;
    TextInputLayout layoutUserName,layoutEmail,layoutPassword,layoutGender;
    MaterialButton confirmButton,datePickerButton;
    String Gender = "",ShowDate="",Birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_receptionist);

        genderAutoCompleteTextView = findViewById(R.id.rdiGenderSelectDropDownBox);
        eName = findViewById(R.id.rdiUserNameInput);
        eEmail = findViewById(R.id.rdiEmailInput);
        ePassword = findViewById(R.id.rdiPasswordInput);
        datePickerButton = findViewById(R.id.rdiPickBirthDateButton);
        layoutUserName = findViewById(R.id.rdiUserNameInputField);
        layoutEmail = findViewById(R.id.rdiEmailLayout);
        layoutPassword = findViewById(R.id.rdiPasswordLayout);
        layoutGender = findViewById(R.id.rdiGenderMenu);
        confirmButton =findViewById(R.id.rdiConfirmButton);

        genderArrayAdapterItems = new ArrayAdapter<String>(this,R.layout.gender_list,genderList);
        genderAutoCompleteTextView.setAdapter(genderArrayAdapterItems);

        genderAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gender = parent.getItemAtPosition(position).toString();
            }
        });

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Birth Date");
        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();

        datePickerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    Birthdate = materialDatePicker.getHeaderText();
                    ShowDate = "  "+Birthdate;
                    datePickerButton.setText(ShowDate);
                });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eName.length() != 0) {
                    if(eEmail.length() != 0) {
                        if(ePassword.length() != 0) {
                            if(!Objects.equals(Gender, "")) {
                                startActivity(new Intent(SignUpReceptionistActivity.this,SignInActivity.class));
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