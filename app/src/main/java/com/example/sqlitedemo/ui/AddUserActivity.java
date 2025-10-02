package com.example.sqlitedemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.LoginDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class AddUserActivity extends AppCompatActivity {

    private TextInputEditText editTextUsername, editTextPassword, editTextFullname, editTextEmail;
    private Button buttonAdd, buttonBack;
    private LoginDatabase databaseDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        khoiTaoGiaoDien();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void khoiTaoGiaoDien() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullname = findViewById(R.id.editTextFullname);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonBack = findViewById(R.id.buttonBack);
        databaseDangNhap = new LoginDatabase(this);
    }

    private void addUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Vui lòng nhập tài khoản");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }

        if (TextUtils.isEmpty(fullname)) {
            editTextFullname.setError("Vui lòng nhập họ tên");
            return;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Vui lòng nhập email hợp lệ");
            return;
        }

        boolean result = databaseDangNhap.insertUser(username, password, fullname, email);
        if (result) {
            Toast.makeText(AddUserActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
            editTextUsername.setText("");
            editTextPassword.setText("");
            editTextFullname.setText("");
            editTextEmail.setText("");
        } else {
            Toast.makeText(AddUserActivity.this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
}