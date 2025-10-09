package com.example.sqlitedemo.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.LoginDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class DeleteUserActivity extends AppCompatActivity {

    private TextInputEditText editTextUsername;
    private Button buttonSearch, buttonDelete, buttonBack;
    private LoginDatabase databaseDangNhap;
    private String userIdToDelete;
// - Hàm khởi tạo: Thiết lập giao diện xóa người dùng
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        khoiTaoGiaoDien();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timNguoiDung();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaNguoiDung();
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
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBack = findViewById(R.id.buttonBack);
        databaseDangNhap = new LoginDatabase(this);
        
        buttonDelete.setEnabled(false);
    }

    private void timNguoiDung() {
        String username = editTextUsername.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Vui lòng nhập tên người dùng để tìm");
            return;
        }

        Cursor cursor = databaseDangNhap.getReadableDatabase().rawQuery(
                "SELECT * FROM " + LoginDatabase.TB_USER + " WHERE " + LoginDatabase.COL_USERNAME + "=?", 
                new String[]{username});
        
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userIdToDelete = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_ID));
            
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_FULLNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_EMAIL));
            
            Toast.makeText(DeleteUserActivity.this, 
                "Tìm thấy người dùng: " + fullname + " (" + email + ")", 
                Toast.LENGTH_LONG).show();
            
            buttonDelete.setEnabled(true);
        } else {
            Toast.makeText(DeleteUserActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            buttonDelete.setEnabled(false);
            userIdToDelete = null;
        }
        
        cursor.close();
    }

    private void xoaNguoiDung() {
        if (userIdToDelete != null) {
            boolean result = databaseDangNhap.deleteUser(Integer.parseInt(userIdToDelete));
            if (result) {
                Toast.makeText(DeleteUserActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();

                // Reset giao diện
                editTextUsername.setText("");
                userIdToDelete = null;
                buttonDelete.setEnabled(false);
            } else {
                if (userIdToDelete.equals("1")) {
                    Toast.makeText(DeleteUserActivity.this, "Không thể xóa tài khoản admin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeleteUserActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}