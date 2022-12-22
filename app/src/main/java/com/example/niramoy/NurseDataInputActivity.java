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

public class NurseDataInputActivity extends AppCompatActivity {

    String[] genderList = {"Male","Female","Others"};
    AutoCompleteTextView genderAutoCompleteTextView;
    ArrayAdapter<String> genderArrayAdapterItems;
    EditText eName,eEmail,ePassword,eDepartment,eEducation;
    TextInputLayout layoutUserName,layoutEmail,layoutPassword,layoutGender,layoutDepartment,layoutEducation;
    MaterialButton confirmButton,datePickerButton;
    String Gender = "",ShowDate="",Birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_data_input);

        genderAutoCompleteTextView = findViewById(R.id.ndiGenderSelectDropDownBox);
        eName = findViewById(R.id.ndiUserNameInput);
        eEmail = findViewById(R.id.ndiEmailInput);
        ePassword = findViewById(R.id.ndiPasswordInput);
        eDepartment = findViewById(R.id.ndiDepartmentInput);
        eEducation = findViewById(R.id.ndiEducationInput);
        datePickerButton = findViewById(R.id.ndiPickBirthDateButton);
        layoutUserName = findViewById(R.id.ndiUserNameInputField);
        layoutEmail = findViewById(R.id.ndiEmailLayout);
        layoutPassword = findViewById(R.id.ndiPasswordLayout);
        layoutGender = findViewById(R.id.ndiGenderMenu);
        layoutDepartment = findViewById(R.id.ndiDepartmentLayout);
        layoutEducation = findViewById(R.id.ndiEducationLayout);
        confirmButton =findViewById(R.id.ndiConfirmButton);

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
                                if(eDepartment.length() != 0) {
                                    startActivity(new Intent(NurseDataInputActivity.this,SignInActivity.class));
                                } else {
                                    layoutDepartment.setError("Enter Department");
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