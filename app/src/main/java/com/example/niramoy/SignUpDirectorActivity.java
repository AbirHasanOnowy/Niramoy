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

public class SignUpDirectorActivity extends AppCompatActivity {

    String[] genderList = {"Male","Female","Others"};
    AutoCompleteTextView genderAutoCompleteTextView;
    ArrayAdapter<String> genderArrayAdapterItems;
    EditText eUserName,eEmail,ePassword;
    TextInputLayout layoutUserName,layoutEmail,layoutPassword,layoutGender;
    MaterialButton confirmButton,datePickerButton;
    String Gender = "",ShowDate="",Birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_director);

        genderAutoCompleteTextView = findViewById(R.id.diGenderSelectDropDownBox);
        eUserName = findViewById(R.id.diUserNameInput);
        eEmail = findViewById(R.id.diEmailInput);
        ePassword = findViewById(R.id.diPasswordInput);
        datePickerButton = findViewById(R.id.diPickBirthDateButton);
        layoutUserName = findViewById(R.id.diUserNameInputField);
        layoutEmail = findViewById(R.id.diEmailLayout);
        layoutPassword = findViewById(R.id.diPasswordLayout);
        layoutGender = findViewById(R.id.diGenderMenu);
        confirmButton =findViewById(R.id.diConfirmButton);

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
                if(eUserName.length() != 0) {
                    if(eEmail.length() != 0) {
                        if(ePassword.length() != 0) {
                            if(!Objects.equals(Gender, "")) {
                                startActivity(new Intent(SignUpDirectorActivity.this,SignInActivity.class));
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