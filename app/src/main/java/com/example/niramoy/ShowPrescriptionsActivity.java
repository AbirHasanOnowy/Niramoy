package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telephony.PreciseDataConnectionState;

import com.example.niramoy.adapters.DirectorRvAdapter;
import com.example.niramoy.adapters.PrescriptionAdapter;
import com.example.niramoy.classes.DirectorMainClass;
import com.example.niramoy.classes.PrescriptionClass;

import java.util.ArrayList;

public class ShowPrescriptionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PrescriptionClass> directorArrayLIst;
    PrescriptionAdapter adminAdapter;
    private LinearLayoutManager linearLayoutManager;
    PrescriptionClass directorMainClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prescriptions);

        directorArrayLIst=new ArrayList<>();
        directorMainClass=new PrescriptionClass("pat1212","Dr John","MBBS, FCPS","26","hosss123","Deslor","Caugh","Drink hot water","12/12/12 23:23");
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        recyclerView = findViewById(R.id.rvShowPres);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adminAdapter = new PrescriptionAdapter(ShowPrescriptionsActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adminAdapter);

    }
}