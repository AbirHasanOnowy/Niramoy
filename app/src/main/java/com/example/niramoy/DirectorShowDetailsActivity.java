package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DirectorShowDetailsActivity extends AppCompatActivity {

    TextView epos,ename,eMail,edept,eEdu,egen,ebir,ever;
    String pos,name,email,dept,edu,gen,bir,ver,hid,uid;
    MaterialButton accButton,rejButton;

    private FirebaseFirestore fstore;
    private DocumentReference dbreference;

    private static final String KEY_HID = "HID";
    private static final String KEY_POS = "Position";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_DEPT = "Dept";
    private static final String KEY_EDU = "Education";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_DOB = "DoB";
    private static final String KEY_VERIFY = "Verified";
    private static final String KEY_UID = "Uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_show_details);

        fstore = FirebaseFirestore.getInstance();


        Intent data = getIntent();
        pos = data.getStringExtra(KEY_POS);
        name = data.getStringExtra(KEY_NAME);
        email = data.getStringExtra(KEY_EMAIL);
        dept = data.getStringExtra(KEY_DEPT);
        edu = data.getStringExtra(KEY_EDU);
        gen = data.getStringExtra(KEY_GENDER);
        bir = data.getStringExtra(KEY_DOB);
        ver = data.getStringExtra(KEY_VERIFY);
        hid = data.getStringExtra(KEY_HID);
        uid = data.getStringExtra(KEY_UID);

        epos = findViewById(R.id.employeePosition);
        ename = findViewById(R.id.employeeName);
        eMail = findViewById(R.id.employeeEmail);
        edept = findViewById(R.id.employeeDepartment);
        eEdu = findViewById(R.id.employeeEducation);
        egen = findViewById(R.id.employeeGender);
        ebir = findViewById(R.id.employeeBirthday);
        ever = findViewById(R.id.employeeVerification);
        accButton = findViewById(R.id.acceptButton);
        rejButton = findViewById(R.id.rejectButton);

        epos.setText(pos);
        ename.setText(name);
        eMail.setText(email);
        edept.setText(dept);
        eEdu.setText(edu);
        egen.setText(gen);
        ebir.setText(bir);
        ever.setText(ver);

        if(Objects.equals(ver, "Yes")) {
            accButton.setVisibility(View.GONE);
            rejButton.setVisibility(View.GONE);
        } else {
            accButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbreference = fstore.collection("Hospitals").document(hid).collection(pos).document(uid);
                    Map<String, Object> work = new HashMap<>();
                    work.put(KEY_VERIFY, "Yes");
                    dbreference.update(work);
                    dbreference = fstore.collection("UID").document(uid);
                    dbreference.update(work);
                    startActivity(new Intent(DirectorShowDetailsActivity.this,DirectorMainActivity.class));
                    finish();
                }
            });

            rejButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fstore.collection("Hospitals").document(hid).collection(pos).document(uid).delete();
                    fstore.collection("UID").document(uid).delete();
                    startActivity(new Intent(DirectorShowDetailsActivity.this,DirectorMainActivity.class));
                    finish();
                }
            });
        }
    }
}