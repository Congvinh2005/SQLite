package com.example.sqlitedemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnViewUsers, btnAddUser, btnUpdateUser, btnDeleteUser, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        khoiTaoGiaoDien();

        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ViewUsersActivity.class);
                startActivity(intent);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });
        
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void khoiTaoGiaoDien() {
        btnViewUsers = findViewById(R.id.buttonViewUsers);
        btnAddUser = findViewById(R.id.buttonAddUser);
        btnUpdateUser = findViewById(R.id.buttonUpdateUser);
        btnDeleteUser = findViewById(R.id.buttonDeleteUser);
        btnBack = findViewById(R.id.buttonBack);
    }
}