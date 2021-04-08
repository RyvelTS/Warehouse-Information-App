package com.rtsproject.ryvel_2018939060053_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private String name,number,manufacture,price,location,user_id,id;
    private AdapterRecycler mAdapter;
    private ArrayList<commodity> commodityList;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserSQLiteHelper userDBHelper;
    private SQLiteDatabase DB;
    public Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Button goToMain= findViewById(R.id.AddBtn);
        User currentUser = ((User) getApplicationContext());
        user_id= currentUser.getUserId();
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        String DATABASE = "final.db";
        int DB_VERSION = 1;
        userDBHelper =new UserSQLiteHelper(ViewActivity.this, DATABASE,null, DB_VERSION);
        DB=userDBHelper.getWritableDatabase();
        DB=userDBHelper.getReadableDatabase();
        String selection = "user_id = ?";
        String[] selectionArgs = { user_id};
        commodityList = new ArrayList<>();
        cursor = DB.query(UserSQLiteHelper.TABLE_NAME_2,null,selection,selectionArgs ,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
            id= cursor.getString(0);
            name = cursor.getString(1);
            number = cursor.getString(3);
            manufacture = cursor.getString(4);
            price = cursor.getString(5);
            location = cursor.getString(6);
            cursor.moveToNext();
            commodityList.add(new commodity(id, name, number,  manufacture, price, location));
        }

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterRecycler(commodityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                commodityList.get(position);
                mAdapter.notifyItemChanged(position);
                Intent viewActivity = new Intent(ViewActivity.this,CommodityActivity.class)
                        .putExtra("itemid",commodityList.get(position).getId())
                        .putExtra("itemname",commodityList.get(position).getName())
                        .putExtra("itemnumber",commodityList.get(position).getNumber())
                        .putExtra("itemmanufacturer",commodityList.get(position).getManufacturer())
                        .putExtra("itemprice",commodityList.get(position).getPrice())
                        .putExtra("itemlocation",commodityList.get(position).getLocation());
                startActivity(viewActivity);
            }
        });
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewActivity = new Intent(ViewActivity.this,MainActivity.class);
                startActivity(viewActivity);
            }
        });

    }
}