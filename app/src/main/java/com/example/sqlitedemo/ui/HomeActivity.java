package com.example.sqlitedemo.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.LoginDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView nhanChao, nhanThongTinNguoiDung;
    private Button nutDangXuat;
    private LoginDatabase databaseDangNhap;
    private String username;
//- Hàm khởi tạo: Thiết lập giao diện trang chủ cho người dùng thường
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseDangNhap = new LoginDatabase(this);
        username = getIntent().getStringExtra("USERNAME");

        khoiTaoGiaoDien();
        hienThiThongTinNguoiDung();

        nutDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void khoiTaoGiaoDien() {
        nhanChao = findViewById(R.id.textViewWelcome);
        nhanThongTinNguoiDung = findViewById(R.id.textViewUserInfo);
        nutDangXuat = findViewById(R.id.buttonLogout);
    }
//- Hiển thị thông tin người dùng: Hiển thị thông tin người dùng hiện tại
    private void hienThiThongTinNguoiDung() {
        if (username != null) {
            nhanChao.setText("Chào mừng, " + username + "!");

            // Lấy thông tin người dùng từ database
            Cursor cursor = layThongTinNguoiDung(username);
            if (cursor != null && cursor.moveToFirst()) {
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_EMAIL));
                
                nhanThongTinNguoiDung.setText("Họ tên: " + hoTen + "\nEmail: " + email);
                cursor.close();
            }
        }
    }
// - Lấy thông tin người dùng: Gọi phương thức từ database để lấy thông tin người dùng
    private Cursor layThongTinNguoiDung(String username) {
        return databaseDangNhap.layThongTinNguoiDung(username);
    }
}