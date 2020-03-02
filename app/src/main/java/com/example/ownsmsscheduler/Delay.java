package com.example.ownsmsscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Delay extends AppCompatActivity {

    String Mytag="MySMS";

    int hr,min;

    int j=0;
    static String numb;
    Button buttonstop;



    EditText editTextnum,editTextmsg;

     static int i=0;

    EditText Allnums;
    Handler handler;

    EditText mydelaytime;



    ListView mylist;

    Button buttonsend;

    Button buttonaddnum;

    ArrayList<String> Allnos;

    ArrayAdapter<String>arrayAdapter;

    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay);

        buttonsend=findViewById(R.id.send);


        buttonstop=findViewById(R.id.Stop);


//    buttonstart=findViewById(R.id.start);
  //  buttonstop=findViewById(R.id.stop);
        mylist=findViewById(R.id.list);
        Allnos=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(Delay.this,R.layout.support_simple_spinner_dropdown_item,Allnos);
        mylist.setAdapter(arrayAdapter);

        mydelaytime=findViewById(R.id.delaytime);

    handler=new Handler();

    editTextnum=findViewById(R.id.num);
    editTextmsg=findViewById(R.id.id_msg);

     //   Allnums=findViewById(R.id.id_nums);
        buttonaddnum=findViewById(R.id.addnumber);


//        runnable=new Runnable() {
  //          @Override
    //        public void run() {

      //          String d=mydelaytime.getText().toString();

        //        int delay=Integer.parseInt(d);


          //      final int del=delay*1000;

            //    if (i<Allnos.size()){

              //  Toast.makeText(Delay.this, "Let's Test"+Allnos.get(i), Toast.LENGTH_SHORT).show();

            //    handler.postDelayed(this,del);

              //  i++;}
         //   }
      //  };

        runnable=new Runnable() {
            @Override
            public void run() {

                String d=mydelaytime.getText().toString();

                       int delay=Integer.parseInt(d);

                String message=editTextmsg.getText().toString();

                      final int del=delay*1000;



            if (i<Allnos.size()){
                if (Allnos.get(i).isEmpty()||message.isEmpty()){




                    Toast.makeText(Delay.this, "Please fill Number and Message field and delay time", Toast.LENGTH_SHORT).show();
          }
          else {

              SmsManager smsManager=SmsManager.getDefault();
              smsManager.sendTextMessage(Allnos.get(i),null,message,null,null);

              Toast.makeText(Delay.this, ""+Allnos.get(i)+""+message, Toast.LENGTH_SHORT).show();

              Log.d(Mytag,"This is SMS and text "+Allnos.get(i)+""+message);
          }


          handler.postDelayed(this,del);
                i++;
            }



            }
        };


        buttonaddnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 numb=editTextnum.getText().toString();

                 Allnos.add(numb);
                 arrayAdapter.notifyDataSetChanged();
            }
        });

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permission();
                /*
                String d=mydelaytime.getText().toString();

                int delay=Integer.parseInt(d);


                final int del=delay*1000;


                runnable=new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(Delay.this, "Send ", Toast.LENGTH_SHORT).show();

                        handler.postDelayed(this,5000);
             //      i++;
                    }
                };
*/
            }
        });

        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   handler.removeCallbacks(runnable);


//            runnable.run();

            }
        });


/*    runnable=new Runnable() {
        @Override
        public void run() {

            Toast.makeText(Delay.this, "5000 miliseconds delay", Toast.LENGTH_SHORT).show();

        handler.postDelayed(this,5000);
        }
    };
*/

/*
    buttonstart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //           runnable.run();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    permission();
                }
            },5000);




//         permission();
        }
    });



    buttonstop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

   //         handler.removeCallbacks(runnable);

     //       permission();
        }
    });


*/



    }

    private void permission() {


        if(ContextCompat.checkSelfPermission(Delay.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

     //       Getdata();

            runnable.run();
        }
        else {

            ActivityCompat.requestPermissions(Delay.this,new String[]{Manifest.permission.SEND_SMS},1);

            Toast.makeText(Delay.this,"Permission not Granted",Toast.LENGTH_SHORT).show();

        }
    }

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
                                    ContactsContract.CommonDataKinds.Phone.TYPE },
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
    }

    private void showSelectedNumber(int type, String number) {

        editTextnum.setText(number.replaceAll("\\s",""));
    }

    /*
    private void Getdata() {


 String d=mydelaytime.getText().toString();

                int delay=Integer.parseInt(d);


                final int del=delay*1000;

//        String number=editTextnum.getText().toString();
        String message=editTextmsg.getText().toString();



        if(number.isEmpty()||message.isEmpty()){

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

        else {

            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message,null,null);

            Toast.makeText(this, ""+number+""+message , Toast.LENGTH_SHORT).show();

            Log.d(Mytag,"This is SMS and text "+number+""+message);
        }

    }
    */
}
