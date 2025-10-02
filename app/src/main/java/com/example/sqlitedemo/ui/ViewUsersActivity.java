package com.example.sqlitedemo.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.LoginDatabase;

import java.util.ArrayList;

public class ViewUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private Button buttonBack;
    private Button buttonSearch;
    private EditText editTextSearch;
    private LoginDatabase databaseDangNhap;
    private ArrayList<String> userList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        listViewUsers = findViewById(R.id.listViewUsers);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSearch = findViewById(R.id.buttonSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
        databaseDangNhap = new LoginDatabase(this);
        
        userList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewUsers.setAdapter(adapter);
        
        loadUsers();
        
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to AdminDashboardActivity
            }
        });
        
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = editTextSearch.getText().toString().trim();
                if (searchQuery.isEmpty()) {
                    loadUsers(); // Load all users if search query is empty
                } else {
                    searchUsers(searchQuery);
                }
            }
        });
        
        // Also allow searching when pressing Enter in the search field
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String searchQuery = editTextSearch.getText().toString().trim();
                if (searchQuery.isEmpty()) {
                    loadUsers(); // Load all users if search query is empty
                } else {
                    searchUsers(searchQuery);
                }
                return true;
            }
        });
    }
    
    private void loadUsers() {
        userList.clear();
        
        Cursor cursor = databaseDangNhap.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_USERNAME));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_EMAIL));

                String userInfo = "ID: " + id + "\n"
                        + "Username: " + username + "\n"
                        + "Fullname: " + fullname + "\n"
                        + "Email: " + email;

                userList.add(userInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
    
    private void searchUsers(String query) {
        userList.clear();
        
        Cursor cursor = databaseDangNhap.searchUsersByName(query);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_USERNAME));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(LoginDatabase.COL_EMAIL));

                String userInfo = "ID: " + id + "\n"
                        + "Username: " + username + "\n"
                        + "Fullname: " + fullname + "\n"
                        + "Email: " + email;

                userList.add(userInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}