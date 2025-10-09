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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nhapHoTen, nhapEmail, nhapTaiKhoan, nhapMatKhau;
    private Button nutDangKy;
    private TextView nhanDangNhap;
    private LoginDatabase databaseDangNhap;

   // - Hàm khởi tạo: Thiết lập giao diện đăng ký và sự kiện click

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseDangNhap = new LoginDatabase(this);

        khoiTaoGiaoDien();

        nutDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKyNguoiDung();
            }
        });

        nhanDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//- Khởi tạo giao diện: Gán các thành phần giao diện với biến
    private void khoiTaoGiaoDien() {
        nhapHoTen = findViewById(R.id.editTextFullName);
        nhapEmail = findViewById(R.id.editTextEmail);
        nhapTaiKhoan = findViewById(R.id.editTextUsername);
        nhapMatKhau = findViewById(R.id.editTextPassword);
        nutDangKy = findViewById(R.id.buttonRegister);
        nhanDangNhap = findViewById(R.id.textViewLogin);
    }
//- Đăng ký người dùng: Thực hiện logic đăng ký người dùng mới

    private void dangKyNguoiDung() {
        String hoTen = nhapHoTen.getText().toString().trim();
        String email = nhapEmail.getText().toString().trim();
        String taiKhoan = nhapTaiKhoan.getText().toString().trim();
        String matKhau = nhapMatKhau.getText().toString().trim();

        if (TextUtils.isEmpty(hoTen)) {
            nhapHoTen.setError("Vui lòng nhập họ tên");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            nhapEmail.setError("Vui lòng nhập email");
            return;
        }

        if (TextUtils.isEmpty(taiKhoan)) {
            nhapTaiKhoan.setError("Vui lòng nhập tài khoản");
            return;
        }

        if (TextUtils.isEmpty(matKhau)) {
            nhapMatKhau.setError("Vui lòng nhập mật khẩu");
            return;
        }

        boolean duocChen = databaseDangNhap.insertUser(taiKhoan, matKhau, hoTen, email);

        if (duocChen) {
            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
}