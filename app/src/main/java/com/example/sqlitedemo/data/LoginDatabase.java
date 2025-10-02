package com.example.sqlitedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabase extends SQLiteOpenHelper {

    // Tên CSDL và version
    public static final String DATABASE_NAME = "UserDB";
    public static final int DATABASE_VERSION = 2;

    // Tên bảng và cột
    public static final String TB_USER = "User";

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_FULLNAME = "fullname";
    public static final String COL_EMAIL = "email";


    // Contrucstor của sqliteOpenhelper với context ngữ cảnh activity và fragmetn
    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tạo bảng
        String createUserTable = "CREATE TABLE " + TB_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_FULLNAME + " TEXT, " +
                COL_EMAIL + " TEXT)";
        db.execSQL(createUserTable);

        // Thêm dữ liệu mẫu
        ContentValues values = new ContentValues();

        // Admin user
        values.put(COL_USERNAME, "admin");
        values.put(COL_PASSWORD, "123456");
        values.put(COL_FULLNAME, "Admin User");
        values.put(COL_EMAIL, "admin@example.com");
        db.insert(TB_USER, null, values);

        // User 1
        values.clear();
        values.put(COL_USERNAME, "user1");
        values.put(COL_PASSWORD, "password1");
        values.put(COL_FULLNAME, "User One");
        values.put(COL_EMAIL, "user1@example.com");
        db.insert(TB_USER, null, values);

        // Test user
        values.clear();
        values.put(COL_USERNAME, "test");
        values.put(COL_PASSWORD, "123456");
        values.put(COL_FULLNAME, "Test User");
        values.put(COL_EMAIL, "test@example.com");
        db.insert(TB_USER, null, values);
    }
    // Theemfm nguuwoif
    public boolean insertUser(String username, String password, String fullname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra username đã tồn tại chưa
        String checkQuery = "SELECT * FROM " + TB_USER + " WHERE " + COL_USERNAME + "=?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_FULLNAME, fullname);
        values.put(COL_EMAIL, email);

        long result = db.insert(TB_USER, null, values);
        return result != -1;
    }
    //check
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TB_USER +
                " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    // Cập nhật
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        onCreate(db);
    }

    // Phương thức để lấy thông tin người dùng
    public Cursor layThongTinNguoiDung(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TB_USER +
                      " WHERE " + COL_USERNAME + "=?";
        return db.rawQuery(query, new String[]{username});
    }

    // Phương thức thêm dữ liệu mẫu nếu chưa tồn tại
    public void themDuLieuMauNeuChuaTonTai() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem người dùng admin đã tồn tại chưa
        Cursor cursor = db.query(TB_USER, null, COL_USERNAME + "=?",
                                new String[]{"admin"}, null, null, null);
        if (cursor.getCount() == 0) {
            // Người dùng admin chưa tồn tại, thêm dữ liệu mẫu
            ContentValues values = new ContentValues();

            // Người dùng admin
            values.put(COL_USERNAME, "admin");
            values.put(COL_PASSWORD, "123456");
            values.put(COL_FULLNAME, "Admin User");
            values.put(COL_EMAIL, "admin@example.com");
            db.insert(TB_USER, null, values);

            // Người dùng 1
            values.clear();
            values.put(COL_USERNAME, "user1");
            values.put(COL_PASSWORD, "password1");
            values.put(COL_FULLNAME, "User One");
            values.put(COL_EMAIL, "user1@example.com");
            db.insert(TB_USER, null, values);

            // Người dùng test
            values.clear();
            values.put(COL_USERNAME, "test");
            values.put(COL_PASSWORD, "123456");
            values.put(COL_FULLNAME, "Test User");
            values.put(COL_EMAIL, "test@example.com");
            db.insert(TB_USER, null, values);
        }
        cursor.close();
    }
    
    // Phương thức cập nhật người dùng
    public boolean updateUser(int userId, String username, String password, String fullname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_FULLNAME, fullname);
        values.put(COL_EMAIL, email);
        
        int rowsAffected = db.update(TB_USER, values, COL_ID + "=?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0;
    }
    
    // Phương thức xóa người dùng
    public boolean deleteUser(int userId) {
        // Không cho phép xóa người dùng admin (giả sử có id = 1)
        if (userId == 1) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TB_USER, COL_ID + "=?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0;
    }
    
    // Phương thức lấy tất cả người dùng
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TB_USER, null, null, null, null, null, null);
    }
    
    // Phương thức tìm kiếm người dùng theo tên
    public Cursor searchUsersByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_FULLNAME + " LIKE ? OR " + COL_USERNAME + " LIKE ?";
        String[] selectionArgs = {"%" + name + "%", "%" + name + "%"};
        return db.query(TB_USER, null, selection, selectionArgs, null, null, null);
    }
}