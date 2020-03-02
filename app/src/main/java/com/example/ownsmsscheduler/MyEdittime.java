package com.example.ownsmsscheduler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MyEdittime extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,View.OnClickListener
{

    Switch swt;
    EditText edt_num,edt_msg;
    TextView txt_date,txt_time,txt_delete,txt_save;

    String from; //getIntent
    String g_num,g_msg,g_status; //g=global variable

    int g_id;
    long g_date;

    //SharedPreferences sp;
    //SharedPreferences.Editor editor;

    private static MyEdittime ins;

    int yr,mon,day;
    int hr,min;

    SQLiteDatabase db;
    Timedphelper helperDb;
    Cursor cursor;

    Calendar calendar_main = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edittime);

        ins = this;

        swt=(Switch)findViewById(R.id.switchid);

        edt_num=(EditText)findViewById(R.id.num);
        txt_date=(TextView)findViewById(R.id.id_date);
        txt_time=(TextView)findViewById(R.id.id_time);
        edt_msg=(EditText)findViewById(R.id.id_msg);
        txt_delete=(TextView)findViewById(R.id.id_delete);
        txt_save=(TextView)findViewById(R.id.id_save);


        txt_date.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        swt.setOnClickListener(this);

        ins = this;


        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    if(from.equals("add")){
                        check_error(0);
                    }
                    else {
                        check_error(1);
                    }
                }
                else {
                    enable_disable_views(1);
                    
                    off_status();
                    cancelMessage();
                }
            }
        });


        from=getIntent().getStringExtra("schedule");

        if(from.equals("add"))
            initialize_create();
        else{
            g_id=getIntent().getIntExtra("id",0);
            g_msg=getIntent().getStringExtra("msg");
            g_num=getIntent().getStringExtra("num");
            g_date=getIntent().getLongExtra("date",0);
            g_status=getIntent().getStringExtra("status");
            
            initialize_edit();
        }


    }



    private void initialize_edit() {

        Date temp_date=new Date(g_date);
        calendar_main.setTime(temp_date); //set main calender date/time

        SimpleDateFormat only_time = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat only_date = new SimpleDateFormat("d/MM/yyyy");

        edt_num.setText(g_num);
        txt_date.setText(only_date.format(temp_date));
        txt_time.setText(only_time.format(temp_date));
        edt_msg.setText(g_msg);

        if(g_status.equals("ON"))
            swt.setChecked(true);
        else
            swt.setChecked(false);
    }

    private void initialize_create() {

        Date date_date=calendar_main.getTime();

        SimpleDateFormat only_time = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat only_date = new SimpleDateFormat("d/MM/yyyy");

        edt_num.setText("");
        txt_date.setText(only_date.format(date_date));
        txt_time.setText(only_time.format(date_date));
        edt_msg.setText("");
    }

    private void off_status() {

        g_status="OFF";

        helperDb=new Timedphelper(MyEdittime.this);
        db=helperDb.getWritableDatabase();
        helperDb.updateStatus(g_id,g_status,db);
        helperDb.close();
        db.close();
    }

    private void cancelMessage() {

        Intent intent = new Intent(this,AlarmBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, g_id, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        //Toast.makeText(this, ".", Toast.LENGTH_LONG).show();

    }

    private void enable_disable_views(int i) {

        if(i==1){
            edt_num.setEnabled(true);
            edt_msg.setEnabled(true);
            txt_time.setEnabled(true);
            txt_date.setEnabled(true);
            //.setEnabled(true);
        }
        else {
            //edt_num.setTextColor(Color.parseColor("#9C27B0"));
            edt_num.setEnabled(false);
            edt_msg.setEnabled(false);
            txt_time.setEnabled(false);
            txt_date.setEnabled(false);

        }
    }

    private void check_error(int i) {

        Calendar calendar_now=Calendar.getInstance();
        Date date_now=calendar_now.getTime();

        Date date_set=calendar_main.getTime();

        long temp_date=date_set.getTime();

        g_num=edt_num.getText().toString();
        g_num=g_num.trim();
        g_msg=edt_msg.getText().toString();
        g_date=temp_date;
        g_status="ON";

        if(g_num.length()==11){
            if(g_msg.length()>0){
                if(g_msg.length()<145){
                    if(date_set.after(date_now)){
                        if(i==0)
                            add_data();
                        else
                           
                            edit_data();
                    }
                    else {
                        swt.setChecked(false);
                        Toast.makeText(MyEdittime.this, "Check date and time.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    swt.setChecked(false);
                    Toast.makeText(MyEdittime.this,"Message too long (145 max.)",Toast.LENGTH_LONG).show();
                }

            }
            else {
                swt.setChecked(false);
                Toast.makeText(MyEdittime.this,"Enter message.",Toast.LENGTH_LONG).show();
            }
        }
        else {
            swt.setChecked(false);
            Toast.makeText(MyEdittime.this,"Invalid phone number..",Toast.LENGTH_SHORT).show();
        }
    }

    private void edit_data() {

        helperDb=new Timedphelper(MyEdittime.this);
        db=helperDb.getWritableDatabase();
        helperDb.updateValues(g_id,g_msg,g_num,g_date,g_status,db);

        helperDb.close();
        db.close();

        sendMessage();

        enable_disable_views(0);
    }

    private void add_data() {

        ArrayList<Integer> arrayList=new ArrayList<>();

        helperDb=new Timedphelper(MyEdittime.this);
        db=helperDb.getWritableDatabase();
        helperDb.create_table(db);
        helperDb.addvalues(g_msg,g_num,g_date,g_status,db);

        cursor=helperDb.getId(db);
        if(cursor.moveToFirst()){
            do{
                int id;
                id=cursor.getInt(0);

                arrayList.add(id);

            }while(cursor.moveToNext());
        }

        g_id= Collections.max(arrayList);

        helperDb.close();
        db.close();

        sendMessage();

        finish();
    }

    private void sendMessage() {

        Intent intentBoot = new Intent(MyEdittime.this, AlarmBroadcast.class);
        intentBoot.putExtra("id",g_id);
        intentBoot.putExtra("msg",g_msg);
        intentBoot.putExtra("num",g_num);

        intentBoot.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,calendar_main.getTimeInMillis(),
                PendingIntent.getBroadcast(MyEdittime.this,g_id, intentBoot, PendingIntent.FLAG_UPDATE_CURRENT));

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
        edt_num.setText(number.replaceAll("\\s",""));
    }

    public void picknumber(View view) {

        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        pick_intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pick_intent, 1);
    }

    public static MyEdittime getInstance(){

        return ins;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


        calendar_main.set(Calendar.YEAR,i);
        calendar_main.set(Calendar.MONTH,i1);
        calendar_main.set(Calendar.DAY_OF_MONTH,i2);

        Date temp_date=calendar_main.getTime();

        SimpleDateFormat temp_formate=new SimpleDateFormat("d/MM/yyyy");

        txt_date.setText(temp_formate.format(temp_date));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edittime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        View view =getCurrentFocus();

        if(view!=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

        switch (item.getItemId()) {
            case R.id.action_delete_id:
                if(from.equals("add")){
                    finish();
                }
                else {
                    delete_row();
                }
                break;
        }
        return true;

    }

    private void delete_row() {

        if(g_status.equals("OFF")){
            helperDb=new Timedphelper(MyEdittime.this);
            db=helperDb.getWritableDatabase();
            helperDb.deleteRow(g_id,db);
            Toast.makeText(MyEdittime.this, "Deleted.", Toast.LENGTH_SHORT).show();
            helperDb.close();
            db.close();
            finish();
        }
        else
            Toast.makeText(MyEdittime.this, "Disable the schedule first.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        calendar_main.set(Calendar.HOUR_OF_DAY,i);
        calendar_main.set(Calendar.MINUTE,i1);
        SimpleDateFormat formatConvert = new SimpleDateFormat("hh:mm a");
        Date dateConvert=calendar_main.getTime();

        txt_time.setText(formatConvert.format(dateConvert));
    }

    @Override
    public void onClick(View view) {

        if(view==txt_date){
            Calendar calendar_set=Calendar.getInstance();
            yr=calendar_set.get(Calendar.YEAR);
            mon=calendar_set.get(Calendar.MONTH);
            day=calendar_set.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker=new DatePickerDialog(MyEdittime.this,this,yr,mon,day);

            datePicker.show();
        }
        else if(view==txt_time){

            Calendar calendar_set=Calendar.getInstance();
            hr=calendar_set.get(Calendar.HOUR_OF_DAY);
            min=calendar_set.get(Calendar.MINUTE);
            TimePickerDialog timePicker=new TimePickerDialog(MyEdittime.this,this,hr,min,
                    DateFormat.is24HourFormat(MyEdittime.this));
            timePicker.show();

        }

    }
}
