package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.niramoy.adapters.PrescriptionAdapter;
import com.example.niramoy.adapters.TestsAdapter;
import com.example.niramoy.classes.PrescriptionClass;
import com.example.niramoy.classes.TestClass;

import java.util.ArrayList;

public class ShowTestsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TestClass> directorArrayLIst;
    TestsAdapter adminAdapter;
    private LinearLayoutManager linearLayoutManager;
    TestClass directorMainClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tests);
        directorArrayLIst=new ArrayList<>();
        directorMainClass=new TestClass("pat1212","Dr John","MBBS, FCPS","26","hosss123","Deslor","Drink hot water","12/12/12 23:23");
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        directorArrayLIst.add(directorMainClass);
        recyclerView = findViewById(R.id.rvShowTests);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adminAdapter = new TestsAdapter(ShowTestsActivity.this, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.teal_200), directorArrayLIst);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adminAdapter);
    }
}