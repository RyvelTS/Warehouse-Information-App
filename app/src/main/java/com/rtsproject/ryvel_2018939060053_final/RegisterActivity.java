package com.rtsproject.ryvel_2018939060053_final;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private String name, student_id, password;
    private EditText nameInput,studentIDInput,passwordInput;
    private UserSQLiteHelper userDBhelper;
    private SQLiteDatabase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button login = findViewById(R.id.loginButton);

        Button register = findViewById(R.id.registerButton);
        nameInput = findViewById(R.id.editTextTextPersonName);
        studentIDInput = findViewById(R.id.editTextNumber);
        passwordInput = findViewById(R.id.editTextTextPassword);

        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBhelper=new UserSQLiteHelper(RegisterActivity.this, DATABASE,null, DB_VERSION);
        DB=userDBhelper.getWritableDatabase();
        DB=userDBhelper.getReadableDatabase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityLink();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString();
                student_id = studentIDInput.getText().toString();
                password = passwordInput.getText().toString();
                if(! name.isEmpty() || student_id.isEmpty() || password.isEmpty()){
                    ContentValues dataValues = new ContentValues();
                    dataValues.put("name",name.trim());
                    dataValues.put("student_id",student_id.trim());
                    dataValues.put("password",password.trim());
                    Log.e("CONTENT_VALUES",dataValues.toString());
                    long user_id=DB.insert(UserSQLiteHelper.TABLE_NAME,"id",dataValues);
                    Toast.makeText(RegisterActivity.this, "Account Created with id "+user_id+ ", Please Login",Toast.LENGTH_LONG).show();
                    activityLink();
                }else if(name.length()<2){
                    Toast.makeText(RegisterActivity.this, "Please Enter A Valid Name",Toast.LENGTH_LONG).show();
                }else if(password.length()<4){
                    Toast.makeText(RegisterActivity.this, "Password is too Short, Please Create Longer Password",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Please Fill All of the Blanks",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void activityLink(){
        Intent Activity= new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(Activity);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBhelper.close();
    }
}