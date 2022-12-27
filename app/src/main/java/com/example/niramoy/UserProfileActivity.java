package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    String hid,position,name,workplace,location,email,password,gender,education,department,birthday;
    TextView tName,tPosition,tWorkplace,tLocation,tEmail,tPassword,tGender,tEducation,tDepartment,tBitrhday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent userdata = getIntent();
        hid = userdata.getStringExtra("HID");
        position = userdata.getStringExtra("Position");
        name = userdata.getStringExtra("Name");
        email = userdata.getStringExtra("Email");
        department = userdata.getStringExtra("Dept");
        password = userdata.getStringExtra("Pass");
        education = userdata.getStringExtra("Edu");
        gender = userdata.getStringExtra("Gender");
        birthday = userdata.getStringExtra("Birthday");


        tName = findViewById(R.id.profileName);
        tPosition = findViewById(R.id.profileDetailsHeading);
        tWorkplace = findViewById(R.id.profileWorkplace);
        tLocation = findViewById(R.id.profileWorkLocation);
        tEmail = findViewById(R.id.profileEmail);
        tPassword = findViewById(R.id.profilePassword);
        tGender = findViewById(R.id.profileGender);
        tEducation = findViewById(R.id.profileEducation);
        tDepartment = findViewById(R.id.profileDepartment);
        tBitrhday = findViewById(R.id.profileDOB);

        tPosition.setText(position+" Details :");
        tName.setText(name);
        tEmail.setText(email);
        tPassword.setText(password);
        tGender.setText(gender);
        tEducation.setText(education);
        tDepartment.setText(department);
        tBitrhday.setText(birthday);

    }
}