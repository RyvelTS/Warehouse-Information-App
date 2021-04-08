package com.rtsproject.ryvel_2018939060053_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CommodityActivity extends AppCompatActivity {
    private String  item_id;
    private UserSQLiteHelper userDBhelper;
    private SQLiteDatabase DB;
    private UserSQLiteHelper userDBHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        Button delete = findViewById(R.id.DeleteBtn);
        Button backToView= findViewById(R.id.ViewBtn);
        Button Edit = findViewById(R.id.EditBtn);

        TextView Name = findViewById(R.id.CommodityTitleTV);
        TextView ID = findViewById(R.id.CommodityIDTV);
        TextView Number = findViewById(R.id.NumberTV);
        TextView Manufacture = findViewById(R.id.ManufactureTV);
        TextView Price = findViewById(R.id.PriceTV);
        TextView Location = findViewById(R.id.LocationTV);

        item_id = getIntent().getExtras().getString("itemid");
        Name.setText(getIntent().getExtras().getString("itemname"));
        ID.setText("ID: "+ item_id);
        Number.setText("Number: "+getIntent().getExtras().getString("itemnumber"));
        Manufacture.setText("Manufacturer/Factory: "+getIntent().getExtras().getString("itemmanufacturer"));
        Price.setText("Price: "+getIntent().getExtras().getString("itemprice"));
        Location.setText("Storage Location: "+getIntent().getExtras().getString("itemlocation"));

        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBHelper = new UserSQLiteHelper(this, DATABASE, null, DB_VERSION);
        DB= userDBHelper.getWritableDatabase();
        DB= userDBHelper.getReadableDatabase();

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewActivity = new Intent(CommodityActivity.this,EditActivity.class)
                        .putExtra("itemid",item_id);
                startActivity(viewActivity);

            }
        });
        backToView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewActivity = new Intent(CommodityActivity.this,ViewActivity.class);
                startActivity(viewActivity);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = "id = ?";
                String[] selectionArgs = { item_id };
                if (DB.delete(UserSQLiteHelper.TABLE_NAME_2,selection,selectionArgs) == 1){
                    Toast.makeText(CommodityActivity.this, "Deleted Successfully",Toast.LENGTH_LONG).show();
                    Intent viewActivity = new Intent(CommodityActivity.this,ViewActivity.class);
                    startActivity(viewActivity);
                }
            }
        });
    }
}