package com.example.niramoy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    String hid,position,name,workplace,location,email,password,gender,education,department,birthday;
    TextView tName,tPosition,tWorkplace,tLocation,tEmail,tPassword,tGender,tEducation,tDepartment,tBitrhday;

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fStore = FirebaseFirestore.getInstance();

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
        workplace = "Management";
        location = "Control Office";

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
        tWorkplace.setText(workplace);
        tLocation.setText(location);

        if(!Objects.equals(position, "Admin")) {

            fStore.collection("Hospitals").document(hid).addSnapshotListener(UserProfileActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tWorkplace.setText(value.getString("Name"));
                    tLocation.setText(value.getString("Address"));
                }
            });
        }

    }
}