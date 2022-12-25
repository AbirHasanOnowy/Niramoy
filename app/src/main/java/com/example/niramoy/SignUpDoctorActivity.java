package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpDoctorActivity extends AppCompatActivity {

    String[] genderList = {"Male","Female","Others"};
    AutoCompleteTextView genderAutoCompleteTextView;
    ArrayAdapter<String> genderArrayAdapterItems;
    EditText eName,eEmail,ePassword,eDepartment,eEducation;
    TextInputLayout layoutUserName,layoutEmail,layoutPassword,layoutGender,layoutDepartment,layoutEducation;
    MaterialButton confirmButton,datePickerButton;
    String Gender = "",ShowDate="",Birthdate = "",name,position,hid,email,password,dept,education,verified="No",uid;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference dbReference,hospRef,adminRef;

    private static final String KEY_HID = "HID";
    private static final String KEY_POS = "Position";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASS = "Password";
    private static final String KEY_DEPT = "Dept";
    private static final String KEY_EDU = "Education";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_DOB = "DoB";
    private static final String KEY_VERIFY = "Verified";
    private static final String KEY_UID = "Uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);

        Intent intent = getIntent();
        hid = intent.getStringExtra("HID");
        position = intent.getStringExtra("Position");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        genderAutoCompleteTextView = findViewById(R.id.ddiGenderSelectDropDownBox);
        eName = findViewById(R.id.ddiUserNameInput);
        eEmail = findViewById(R.id.ddiEmailInput);
        ePassword = findViewById(R.id.ddiPasswordInput);
        eDepartment = findViewById(R.id.ddiDepartmentInput);
        eEducation = findViewById(R.id.ddiEducationInput);
        datePickerButton = findViewById(R.id.ddiPickBirthDateButton);
        layoutUserName = findViewById(R.id.ddiUserNameInputField);
        layoutEmail = findViewById(R.id.ddiEmailLayout);
        layoutPassword = findViewById(R.id.ddiPasswordLayout);
        layoutGender = findViewById(R.id.ddiGenderMenu);
        layoutDepartment = findViewById(R.id.ddiDepartmentLayout);
        layoutEducation = findViewById(R.id.ddiEducationLayout);
        confirmButton =findViewById(R.id.ddiConfirmButton);

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

                name = eName.getText().toString();
                email = eEmail.getText().toString();
                password = ePassword.getText().toString();
                dept = eDepartment.getText().toString();
                education = eEducation.getText().toString();

                if(name.isEmpty()) {
                    layoutUserName.setError("Enter Name");
                    layoutUserName.requestFocus();
                    return;
                }

                if(email.isEmpty()) {
                    layoutEmail.setError("Enter Name");
                    layoutEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    layoutEmail.setError("Please provide valid email");
                    layoutEmail.requestFocus();
                    return;
                }


                if(password.length() < 6) {
                    layoutPassword.setError("Minimum 6 digit");
                    layoutUserName.requestFocus();
                    return;
                }

                if(password.length() > 10) {
                    layoutPassword.setError("Max 10 digit");
                    layoutUserName.requestFocus();
                    return;
                }

                if(Objects.equals(Gender, "")) {
                    layoutGender.setError("Select Gender");
                    layoutGender.requestFocus();
                    return;
                }

                if(Objects.equals(Birthdate, "")) {
                    Toast.makeText(SignUpDoctorActivity.this,"Select Birthdate",Toast.LENGTH_LONG).show();
                    return;
                }

               fAuth.createUserWithEmailAndPassword(email,password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()) {
                                   uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                   dbReference = fStore.collection("UID").document(uid);

                                   Map<String,Object> val = new HashMap<>();
                                   val.put(KEY_HID,hid);
                                   val.put(KEY_POS,position);
                                   val.put(KEY_NAME,name);
                                   val.put(KEY_EMAIL,email);
                                   val.put(KEY_PASS,password);
                                   val.put(KEY_DEPT,dept);
                                   val.put(KEY_EDU,education);
                                   val.put(KEY_GENDER,Gender);
                                   val.put(KEY_DOB,Birthdate);
                                   val.put(KEY_VERIFY,verified);

                                   dbReference.set(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful())
                                           {
                                               if(Objects.equals(position, "Director")){
                                                   adminRef = fStore.collection("Admin").document(uid);

                                                   Map<String, Object> dir = new HashMap<>();
                                                   dir.put(KEY_NAME,name);
                                                   dir.put(KEY_UID, uid);
                                                   dir.put(KEY_HID, hid);

                                                   adminRef.set(dir).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(SignUpDoctorActivity.this, "SignUp complete. Data sent to Admin for validation", Toast.LENGTH_LONG).show();
                                                               startActivity(new Intent(SignUpDoctorActivity.this, SignInActivity.class));
                                                               finish();
                                                           } else {
                                                               Toast.makeText(SignUpDoctorActivity.this, "Failed to Complete Request", Toast.LENGTH_LONG).show();
                                                           }
                                                       }
                                                   });
                                               } else {
                                                   hospRef = fStore.collection("Hospital_ID").document(hid).collection(position).document(uid);

                                                   Map<String, Object> work = new HashMap<>();
                                                   work.put(KEY_UID, uid);
                                                   work.put(KEY_VERIFY, verified);

                                                   hospRef.set(work).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(SignUpDoctorActivity.this, "SignUp complete. Data sent for Director for validation", Toast.LENGTH_LONG).show();
                                                               startActivity(new Intent(SignUpDoctorActivity.this, SignInActivity.class));
                                                               finish();
                                                           } else {
                                                               Toast.makeText(SignUpDoctorActivity.this, "Failed to Complete Request", Toast.LENGTH_LONG).show();
                                                           }
                                                       }
                                                   });
                                               }

                                           } else {
                                               Toast.makeText(SignUpDoctorActivity.this,"Failed to Complete Request", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });
                               } else {
                                   Toast.makeText(SignUpDoctorActivity.this,"Unable to create id. Please try again",Toast.LENGTH_LONG).show();
                               }
                           }
                       });



//
//                if(eName.length() != 0) {
//                    if(eEmail.length() != 0) {
//                        if(ePassword.length() != 0) {
//                            if(!Objects.equals(Gender, "")) {
//                                if(eDepartment.length() != 0) {
//                                    startActivity(new Intent(SignUpDoctorActivity.this,SignInActivity.class));
//                                } else {
//                                    layoutDepartment.setError("Enter Department");
//                                }
//                            } else {
//                                layoutGender.setError("Select Gender");
//                            }
//                        } else {
//                            layoutPassword.setError("Enter Password");
//                        }
//                    } else {
//                        layoutEmail.setError("Enter Valid Email");
//                    }
//                } else {
//                    layoutUserName.setError("Enter User Name");
//                }
            }
        });
    }
}