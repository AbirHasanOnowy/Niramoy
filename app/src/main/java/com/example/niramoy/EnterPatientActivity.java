package com.example.niramoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.NullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnterPatientActivity extends AppCompatActivity {
    String[] genders = {"Male","Female","Others"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    String patientGender,imageStorageKey = "image/",patientname,patientId,birthday,imagelink;
    EditText nameET,pidET,birthdayET;
    TextInputLayout nameTIL,pidTIL,genderTIL,birthdayTIL;
    ImageView image,uploadButton;
    Uri patientImage;
    MaterialButton saveButton;

    private static final String KEY_PID = "PID";
    private static final String KEY_PNAME = "Name";
    private static final String KEY_PGENDER = "Gender";
    private static final String KEY_PDOB = "DoB";
    private static final String KEY_URL = "ImageUrl";

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore fStore;
    private DocumentReference dataRef;
    private StorageReference imageStorageReference;
    private final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_patient);

        firebaseStorage = FirebaseStorage.getInstance();
        imageStorageReference = firebaseStorage.getReference();
        fStore = FirebaseFirestore.getInstance();

        autoCompleteTextView = findViewById(R.id.patientGenderET);
        arrayAdapter=new ArrayAdapter<>(this,R.layout.gender_list,genders);
        autoCompleteTextView.setAdapter(arrayAdapter);
        nameET = findViewById(R.id.patientNameET);
        pidET = findViewById(R.id.PatientIdET);
        birthdayET = findViewById(R.id.patientDOBET);
        nameTIL = findViewById(R.id.patientName);
        pidTIL = findViewById(R.id.patientId);
        genderTIL = findViewById(R.id.patientGender);
        birthdayTIL = findViewById(R.id.patientDOB);
        image = findViewById(R.id.enterPatientImageIV);
        uploadButton = findViewById(R.id.uploadPPButton);
        saveButton = findViewById(R.id.patientSaveButton);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientGender = parent.getItemAtPosition(position).toString();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pick = new Intent(Intent.ACTION_PICK);
                pick.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pick,REQ_CODE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patientname = nameET.getText().toString();
                patientId = pidET.getText().toString();
                birthday = birthdayET.getText().toString();

                if (patientId.length() != 9){
                    pidTIL.setError("ID must contain 9 digits");
                    pidTIL.requestFocus();
                    return;
                } else{
                    pidTIL.setError("");
                }


                if(patientname.isEmpty()) {
                    nameTIL.setError("Enter Patient Name");
                    nameTIL.requestFocus();
                    return;
                }

                if(birthday.isEmpty()) {
                    birthdayTIL.setError("Enter Patient Birthdate");
                    birthdayTIL.requestFocus();
                    return;
                }

                String formatDate = "^(0[1-9]|[12][0-9]|3[01]|[1-9])\\/(0[1-9]|1[0-2]|[1-9])\\/([12][0-9]{3})$";
                Matcher matcherObj = Pattern.compile(formatDate).matcher(birthday);
                if (!matcherObj.matches()){
                    birthdayTIL.setError("Please input in 'dd/mm/yyyy' format");
                    birthdayTIL.requestFocus();
                    return;
                } else {
                    birthdayTIL.setError("");
                }

                if(patientGender == null) {
                    genderTIL.setError("Please Select gender");
                    genderTIL.requestFocus();
                    return;
                }

                if(patientImage == null) {
                    Snackbar.make(findViewById(android.R.id.content),"Please Give Patient Image",Snackbar.LENGTH_SHORT).show();
                    return;
                }


                StorageReference store = imageStorageReference.child(imageStorageKey+patientId);

                store.putFile(patientImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imagelink = uri.toString();

                                dataRef = fStore.collection("Patients").document(patientId);
                                Map<String,Object> val = new HashMap<>();
                                val.put(KEY_PID,patientId);
                                val.put(KEY_PNAME,patientname);
                                val.put(KEY_PDOB,birthday);
                                val.put(KEY_PGENDER,patientGender);
                                val.put(KEY_URL,imagelink);
                                dataRef.set(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(EnterPatientActivity.this,"Successfully Registered Patient",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(EnterPatientActivity.this,MainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EnterPatientActivity.this,"Failed to upload image",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            if(requestCode==REQ_CODE) {
                patientImage = data.getData();
                image.setImageURI(patientImage);
            }
        }
    }
}