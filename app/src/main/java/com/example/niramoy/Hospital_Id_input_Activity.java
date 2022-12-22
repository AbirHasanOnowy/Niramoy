package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class Hospital_Id_input_Activity extends AppCompatActivity {

    MaterialButton nextButton;
    String[] positionList = {"Director","Doctor","Receptionist","Nurse"};
    AutoCompleteTextView positionAutoCompleteTextView;
    ArrayAdapter<String> positionArrayAdapterItems;
    String Position="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_id_input);

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
                if(Objects.equals(Position, "Director")){
                    startActivity(new Intent(Hospital_Id_input_Activity.this, DirectorDataInputActivity.class));
                } else if(Objects.equals(Position, "Doctor")){
                    startActivity(new Intent(Hospital_Id_input_Activity.this, DoctorDataInputActivity.class));
                }else if(Objects.equals(Position, "Receptionist")){
                    startActivity(new Intent(Hospital_Id_input_Activity.this, ReceptionistDataInputActivity.class));
                } else if(Objects.equals(Position, "Nurse")){
                    startActivity(new Intent(Hospital_Id_input_Activity.this, NurseDataInputActivity.class));
                }
            }
        });
    }
}