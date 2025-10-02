package com.example.sqlitedemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.LoginDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText nhapTaiKhoan, nhapMatKhau;
    private Button nutDangNhap;
    private TextView nhanDangKy, nhanQuenMatKhau;
    private LoginDatabase databaseDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseDangNhap = new LoginDatabase(this);

        khoiTaoGiaoDien();

        nutDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhapNguoiDung();
            }
        });

        nhanDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nhanQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Chức năng quên mật khẩu chưa được triển khai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void khoiTaoGiaoDien() {
        nhapTaiKhoan = findViewById(R.id.editTextUsername);
        nhapMatKhau = findViewById(R.id.editTextPassword);
        nutDangNhap = findViewById(R.id.buttonLogin);
        nhanDangKy = findViewById(R.id.textViewRegister);
        nhanQuenMatKhau = findViewById(R.id.textViewForgotPassword);
    }

    private void dangNhapNguoiDung() {
        String username = nhapTaiKhoan.getText().toString().trim();
        String password = nhapMatKhau.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            nhapTaiKhoan.setError("Vui lòng nhập tài khoản");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            nhapMatKhau.setError("Vui lòng nhập mật khẩu");
            return;
        }

        boolean laNguoiDungHopLe = databaseDangNhap.checkUser(username, password);

        if (laNguoiDungHopLe) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            
            // Kiểm tra nếu là người dùng admin thì chuyển đến giao diện admin, nếu không thì chuyển đến giao diện người dùng thông thường
            if (username.equals("admin")) {
                // Điều hướng đến màn hình admin
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            } else {
                // Điều hướng đến màn hình chính cho người dùng thường
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}