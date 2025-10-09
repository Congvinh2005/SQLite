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

public class UpdateUserActivity extends AppCompatActivity {

    private TextInputEditText editTextSearchUsername, editTextUsername, editTextPassword, editTextFullname, editTextEmail;
    private Button buttonSearch, buttonUpdate, buttonBack;
    private LoginDatabase databaseDangNhap;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        khoiTaoGiaoDien();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timNguoiDung();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhatNguoiDung();
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
        editTextSearchUsername = findViewById(R.id.editTextSearchUsername);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullname = findViewById(R.id.editTextFullname);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonBack = findViewById(R.id.buttonBack);
        databaseDangNhap = new LoginDatabase(this);
        
        // Disable các trường nhập liệu cho đến khi tìm thấy người dùng
        enableInputFields(false);
    }
//- Bật/tắt trường nhập liệu: Cho phép hoặc vô hiệu hóa các trường nhập liệu
    private void enableInputFields(boolean enable) {
        editTextUsername.setEnabled(enable);
        editTextPassword.setEnabled(enable);
        editTextFullname.setEnabled(enable);
        editTextEmail.setEnabled(enable);
        buttonUpdate.setEnabled(enable);
    }

    private void timNguoiDung() {
        String username = editTextSearchUsername.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextSearchUsername.setError("Vui lòng nhập tên người dùng để tìm");
            return;
        }

        Cursor cursor = databaseDangNhap.getReadableDatabase().rawQuery(
                "SELECT * FROM " + LoginDatabase.TB_USER + " WHERE " + LoginDatabase.COL_USERNAME + "=?", 
                new String[]{username});
        
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            currentUserId = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_ID));
            String usernameFound = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_PASSWORD));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_FULLNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_EMAIL));
            
            // Đổ dữ liệu vào các trường
            editTextUsername.setText(usernameFound);
            editTextPassword.setText(password);
            editTextFullname.setText(fullname);
            editTextEmail.setText(email);
            
            enableInputFields(true);
            
            Toast.makeText(UpdateUserActivity.this, "Tìm thấy người dùng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateUserActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            enableInputFields(false);
        }
        
        cursor.close();
    }
//- Cập nhật người dùng: Cập nhật thông tin người dùng trong cơ sở dữ liệu
    private void capNhatNguoiDung() {
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

        if (currentUserId != null) {
            boolean result = databaseDangNhap.updateUser(Integer.parseInt(currentUserId), username, password, fullname, email);
            if (result) {
                Toast.makeText(UpdateUserActivity.this, "Cập nhật người dùng thành công", Toast.LENGTH_SHORT).show();
                
                // Reset giao diện
                editTextSearchUsername.setText("");
                editTextUsername.setText("");
                editTextPassword.setText("");
                editTextFullname.setText("");
                editTextEmail.setText("");
                currentUserId = null;
                enableInputFields(false);
            } else {
                Toast.makeText(UpdateUserActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}