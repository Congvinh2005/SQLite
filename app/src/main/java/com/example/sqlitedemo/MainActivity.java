package com.example.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.data.LoginDatabase;
import com.example.sqlitedemo.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Khởi tạo LoginDatabase và thêm dữ liệu mẫu nếu chưa tồn tại
        LoginDatabase databaseDangNhap = new LoginDatabase(this);
        databaseDangNhap.themDuLieuMauNeuChuaTonTai();
        
        // Chuyển hướng đến LoginActivity như điểm bắt đầu chính
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
