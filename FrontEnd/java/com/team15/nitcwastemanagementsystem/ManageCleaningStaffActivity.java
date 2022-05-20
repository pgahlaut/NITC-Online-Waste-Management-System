package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageCleaningStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cleaning_staff);
        Button addStaffBtn = findViewById(R.id.add_staff_btn);
        addStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddCleaningStaffActivity.class);
                startActivity(i);
            }
        });
        Button viewStaffBtn = findViewById(R.id.view_staff_btn);
        viewStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewCleaningStaffActivity.class);
                startActivity(i);
            }
        });
    }
}