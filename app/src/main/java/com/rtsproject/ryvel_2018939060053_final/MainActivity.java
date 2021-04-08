package com.rtsproject.ryvel_2018939060053_final;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String user_id, username;
    private String name,number,manufacture,price,location;
    private EditText nameInput,numberInput,manufactureInput,priceInput,locationInput;
    private UserSQLiteHelper userDBHelper;
    private SQLiteDatabase DB;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput=findViewById(R.id.editTextTextCommodityName);
        numberInput=findViewById(R.id.editTextNumber2);
        manufactureInput=findViewById(R.id.editTextTextManufacturer);
        priceInput=findViewById(R.id.editTextNumberDecimal);
        locationInput=findViewById(R.id.editTextTextLocation);

        Button addCommodity = findViewById(R.id.addCommoditiyButton);
        Button viewCommodity = findViewById(R.id.viewCommoditiesButton);
        TextView greetingText = findViewById(R.id.greetingtextTV);

        User currentUser = ((User) getApplicationContext());

        username= currentUser.getUsername();
        greetingText.setText("Welcome back, "+username+" !");
        user_id= currentUser.getUserId();

        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBHelper =new UserSQLiteHelper(MainActivity.this, DATABASE,null, DB_VERSION);
        DB=userDBHelper.getWritableDatabase();
        DB=userDBHelper.getReadableDatabase();

        addCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameInput.getText().toString();
                number=numberInput.getText().toString();
                manufacture=manufactureInput.getText().toString();
                price = priceInput.getText().toString();
                location=locationInput.getText().toString();
                if(! name.isEmpty()||number.isEmpty()||manufacture.isEmpty()||price.isEmpty()||location.isEmpty() ){
                    ContentValues dataValues = new ContentValues();
                    dataValues.put("user_id",user_id.trim());
                    dataValues.put("name",name.trim());
                    dataValues.put("number",number.trim());
                    dataValues.put("manufacturer",manufacture.trim());
                    dataValues.put("price",price.trim());
                    dataValues.put("location",location.trim());
                    Log.e("CONTENT_VALUES",dataValues.toString());
                    long commodity_id=DB.insert(UserSQLiteHelper.TABLE_NAME_2,"id",dataValues);
                    Toast.makeText(MainActivity.this, "Commodity Added with id= "+commodity_id,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Please Fill All of The Blanks",Toast.LENGTH_LONG).show();
                }
            }
        });
        viewCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewActivity = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(viewActivity);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBHelper.close();
    }
}