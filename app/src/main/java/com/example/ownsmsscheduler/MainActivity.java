package com.example.ownsmsscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static MainActivity obj;

    ArrayList<Myclassitem> arrayList;
    TextView txt_add;

    SQLiteDatabase db;
    Timedphelper helperDb;
    Cursor cursor;

    RecyclerView recyclerView2;
    RecyclerView.Adapter adapter2;
    RecyclerView.LayoutManager layoutManager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView2 =(RecyclerView)findViewById(R.id.recyclerviewmain);
        txt_add=(TextView)findViewById(R.id.add);

        layoutManager2 = new LinearLayoutManager(this);

        obj=this;


        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(MainActivity.this,MyEdittime.class);
                intent.putExtra("schedule","add");
                startActivity(intent);
            }
        });

    }
    public static MainActivity getInstance(){

        return obj;
    }
    public void refresh(){

  //      get_list();

        GetmyData();
    }


    private void get_list() {


   //   db.close();

    //    if(arrayList.size()==0) {

            arrayList = new ArrayList<>();

        Collections.shuffle(arrayList);
            helperDb = new Timedphelper(this);
            db = helperDb.getReadableDatabase();
            cursor = helperDb.getInformation(db);

            if (cursor.moveToFirst()) {
                do {
                    String msg, status;
                    int id;
                    String num;
                    long date;

                    id = cursor.getInt(0);
                    msg = cursor.getString(1);
                    num = cursor.getString(2);
                    date = cursor.getLong(3);
                    status = cursor.getString(4);

                    arrayList.add(new Myclassitem(id, msg, status,num,date));

                    Log.i("----", "get_list: "+num);
                }



                while (cursor.moveToNext());


            //    Collections.shuffle(arrayList);
            }
                helperDb.close();

                txt_add.setVisibility(View.VISIBLE);

      //  }
     //   else txt_add.setVisibility(View.GONE);

        adapter2=new Myrecycleradapter(arrayList,MainActivity.this);

        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter2);



    }


    @Override
    protected void onResume() {
        super.onResume();


        try {

       //     get_list();

            GetmyData();
        }
        catch (Exception e){

            e.printStackTrace();

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        View view=getCurrentFocus();

        if(view!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }


        switch (item.getItemId()) {
            case R.id.action_add_id:
                Intent intent=new Intent(MainActivity.this,MyEdittime.class);
                intent.putExtra("schedule","add");
                startActivity(intent);
                break;
        }
        return true;
    }

    public  void  GetmyData(){

//        if(arrayList.size()==0) {
            //    arrayList = new ArrayList<>();



            helperDb = new Timedphelper(this);
            db = helperDb.getReadableDatabase();

            arrayList = helperDb.Alldata();

            //Collections.shuffle(arrayList);

            helperDb.close();

            txt_add.setVisibility(View.VISIBLE);
  //      }




            adapter2 = new Myrecycleradapter(arrayList, MainActivity.this);

            //           recyclerView2.setHasFixedSize(true);
            recyclerView2.setLayoutManager(layoutManager2);
            recyclerView2.setAdapter(adapter2);





    }

}