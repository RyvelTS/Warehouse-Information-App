package com.rtsproject.ryvel_2018939060053_final;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private String name,number,manufacture,price,location,item_id;
    private EditText nameInput,numberInput,manufactureInput,priceInput,locationInput;
    private UserSQLiteHelper userDBHelper;
    private SQLiteDatabase DB;
    public Cursor cursor;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameInput=findViewById(R.id.editTextTextCommodityName2);
        numberInput=findViewById(R.id.editTextNumber3);
        manufactureInput=findViewById(R.id.editTextTextManufacturer2);
        priceInput=findViewById(R.id.editTextNumberDecimal2);
        locationInput=findViewById(R.id.editTextTextLocation2);
        Button saveCommodity = findViewById(R.id.updateCommoditiyButton);

        item_id= getIntent().getStringExtra("itemid");

        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBHelper = new UserSQLiteHelper(this, DATABASE, null, DB_VERSION);
        DB= userDBHelper.getWritableDatabase();
        DB= userDBHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = { item_id };
        cursor = DB.query(UserSQLiteHelper.TABLE_NAME_2,null,selection,selectionArgs ,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
            name = cursor.getString(1);
            number = cursor.getString(3);
            manufacture = cursor.getString(4);
            price =cursor.getString(5);
            location = cursor.getString(6);
            cursor.moveToNext();
        }
        nameInput.setText(name);
        numberInput.setText(number);
        manufactureInput.setText(manufacture);
        priceInput.setText(price);
        locationInput.setText(location);

        saveCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString();
                number = numberInput.getText().toString();
                manufacture = manufactureInput.getText().toString();
                price =priceInput.getText().toString();
                location = locationInput.getText().toString();
                if (!(name.isEmpty()||number.isEmpty()||manufacture.isEmpty()||price.isEmpty()||location.isEmpty())){
                    String selection = "id = ?";
                    String[] selectionArgs = { item_id };
                    ContentValues dataValues = new ContentValues();
                    dataValues.put("name",name.trim());
                    dataValues.put("number",number.trim());
                    dataValues.put("manufacturer",manufacture.trim());
                    dataValues.put("price",price.trim());
                    dataValues.put("location",location.trim());
                    Log.e("CONTENT_VALUES",dataValues.toString());
                    DB.update(UserSQLiteHelper.TABLE_NAME_2,dataValues,selection,selectionArgs);
                    Toast.makeText(EditActivity.this, "Changes Saved Successfully",Toast.LENGTH_LONG).show();
                    Intent viewActivity = new Intent(EditActivity.this,CommodityActivity.class)
                            .putExtra("itemid",item_id )
                            .putExtra("itemname",name)
                            .putExtra("itemnumber",number)
                            .putExtra("itemmanufacturer",manufacture)
                            .putExtra("itemprice",price)
                            .putExtra("itemlocation",location);
                    startActivity(viewActivity);
                }else{
                    Toast.makeText(EditActivity.this, "Cannot Change Data with NULL Value, Please Fill in The Blank(s)",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBHelper.close();
    }
}