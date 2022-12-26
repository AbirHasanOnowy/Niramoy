package com.example.niramoy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    MaterialButton nextButton;
    String[] positionList = {"Director","Doctor","Receptionist","Nurse"};
    TextInputLayout layoutHID,layoutPosition;
    EditText eHID;
    AutoCompleteTextView positionAutoCompleteTextView;
    ArrayAdapter<String> positionArrayAdapterItems;
    String Position="",HID;

    private FirebaseFirestore fstore;
    private DocumentReference dbreference;
    private static final String KEY_ASSIGN = "Assigned";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fstore = FirebaseFirestore.getInstance();

        eHID = findViewById(R.id.hidHospitalIdInput);
        layoutHID = findViewById(R.id.hidInputField);
        layoutPosition = findViewById(R.id.hidPositionMenu);

        positionAutoCompleteTextView = findViewById(R.id.hidPositionSelectDropDownBox);
        nextButton=findViewById(R.id.hidNextButton);
        positionArrayAdapterItems = new ArrayAdapter<String>(this,R.layout.gender_list,positionList);
        positionAutoCompleteTextView.setAdapter(positionArrayAdapterItems);

        positionAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Position = parent.getItemAtPosition(position).toString();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HID = eHID.getText().toString().trim();

                if(HID.isEmpty())
                {
                    layoutHID.setError("Hospital ID is required");
                    layoutHID.requestFocus();
                    return;
                }

                if(Objects.equals(Position, ""))
                {
                    layoutPosition.setError("Email is required");
                    layoutPosition.requestFocus();
                    return;
                }

                if(Objects.equals(Position, "Director"))
                {
                    Intent intent = new Intent(SignUpActivity.this,SignUpDoctorActivity.class);
                    intent.putExtra("HID",HID);
                    intent.putExtra("Position",Position);
                    startActivity(intent);
                    return;
                }

                dbreference = fstore.collection("Hospitals").document(HID);
                dbreference.addSnapshotListener(SignUpActivity.this,new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String val = value.getString(KEY_ASSIGN);

                        if(Objects.equals(val, "Yes")) {
                            Intent intent = new Intent(SignUpActivity.this,SignUpDoctorActivity.class);
                            intent.putExtra("HID",HID);
                            intent.putExtra("Position",Position);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this,"Invalid Hospital ID",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}