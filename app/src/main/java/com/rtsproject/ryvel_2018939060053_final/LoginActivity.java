package com.rtsproject.ryvel_2018939060053_final;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private String student_id, passwordIn,passwordDB,userName,userId;
    private TextView errorView;
    private EditText studentIDInput,passwordInput;
    private SQLiteDatabase DB;
    private UserSQLiteHelper userDBhelper;
    public Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        studentIDInput = findViewById(R.id.editTextNumber);
        passwordInput = findViewById(R.id.editTextTextPassword);
        errorView = findViewById(R.id.errorMessage);
        Button register = findViewById(R.id.registerButton);
        Button login = findViewById(R.id.loginButton);

        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBhelper = new UserSQLiteHelper(this, DATABASE, null, DB_VERSION);
        DB= userDBhelper.getWritableDatabase();
        DB= userDBhelper.getReadableDatabase();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityLink(RegisterActivity.class);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                student_id = studentIDInput.getText().toString();
                passwordIn = passwordInput.getText().toString();
                if(!(student_id.isEmpty()||passwordIn.isEmpty())){
                    String selection = "student_id = ?";
                    String[] selectionArgs = { student_id };
                    cursor = DB.query(UserSQLiteHelper.TABLE_NAME,null,selection,selectionArgs ,null,null,null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
                        userName = cursor.getString(1);
                        userId = cursor.getString(0);
                        passwordDB = cursor.getString(3);
                        cursor.moveToNext();
                    }
                    if(passwordIn.equals(passwordDB)){
                        User currentUser = ((User) getApplicationContext());
                        currentUser.setUserId(userId);
                        currentUser.setUsername(userName);
                        activityLink(MainActivity.class);
                    }else{
                        errorView.setText("Your Input Doesn't Match Our Records");
                    }
                }else{
                    errorView.setText("Please Fill All of the Blanks");
                }
            }
        });
    }
    private void activityLink(Class Destination){
        Intent Activity= new Intent(LoginActivity.this,Destination);
        startActivity(Activity);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBhelper.close();
    }
    }