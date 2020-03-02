package com.example.ownsmsscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Mytry extends AppCompatActivity {

    EditText editText1, editText2, editText3;
    Switch aSwitch;


    private Handler handler;

    Button buttonstart, buttonstop;

    //  String[] Allno=new String[100];

    // Boolean imagebol1,imagebol2,imagebol3;

    Handler myhandler = new Handler();
    EditText Message;

    // ImageButton imageButton1,imageButton2,imageButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytry);

        editText1 = findViewById(R.id.num1);
        editText2 = findViewById(R.id.num2);
        // editText3=findViewById(R.id.num3);

        Message = findViewById(R.id.id_msg1);
        aSwitch = findViewById(R.id.switchid);

        buttonstart = findViewById(R.id.start);
        buttonstop = findViewById(R.id.stop);

        handler = new Handler();

        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(Mytry.this, "Code has been started", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }
        });

        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
    /*
    public void picknumber(View view) {

        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        pick_intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pick_intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        int type = c.getInt(1);
                        showSelectedNumber(type, number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
*/

/*
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(b){

                    String no1=editText1.getText().toString();
                    String no2=editText2.getText().toString();
                    String no3=editText3.getText().toString();

                    final String tex=Message.getText().toString();

                    Allno[0]=no1;
                    Allno[1]=no2;
                    Allno[2]=no3;



                    for (int i=0;i<Allno.length;i++){

                        if(Allno[i].length()>0&&tex.length()>0){


                            final int finalI = i;
                            myhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(Mytry.this, ""+Allno[finalI]+""+tex, Toast.LENGTH_SHORT).show();
                                }
                            },(i+1)*20000);
                        }
                         else{

                            Toast.makeText(Mytry.this, "error here", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });



  */

/*
    }

        private void showSelectedNumber ( int type, String number){

            //  edt_num.setText(number.replaceAll("\\s",""));


            editText1.setText(number.replaceAll("\\s", ""));
            //      editText2.setText(number.replaceAll("\\s",""));


   /* else if (editText2.equals("")){

        editText2.setText(number.replaceAll("\\s",""));
    }
    else if (editText3.equals("")){

        editText3.setText(number.replaceAll("\\s",""));
    }
    */




