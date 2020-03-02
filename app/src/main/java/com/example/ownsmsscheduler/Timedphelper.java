package com.example.ownsmsscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Timedphelper extends SQLiteOpenHelper {


    private SQLiteDatabase db;

    public static final String DataBaseName="SMSs.DB";
    public static final int DataBase_version=1;
    public static final String Create_query=
            "CREATE TABLE IF NOT EXISTS "+ Timecontractor.userInfo.TABLE+"("+
                    Timecontractor.userInfo.Id_+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    Timecontractor.userInfo.Msg+" TEXT,"+
                    Timecontractor.userInfo.Num+" TEXT,"+
                    Timecontractor.userInfo.Date_+" INTEGER,"+
                    //Time_contractor.userInfo.Time+" INTEGER,"+
                    Timecontractor.userInfo.Status+" TEXT);";

    public Timedphelper(Context context) {
        super(context, DataBaseName, null, DataBase_version);
    }

    public void create_table(SQLiteDatabase db)
    {
        db.execSQL(Create_query);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Create_query);
    }

    public void addvalues(String msg, String num, long date, String status, SQLiteDatabase db){

        ContentValues contentValues=new ContentValues();

        //contentValues.put(Time_contractor.userInfo.Id_,email);
        contentValues.put(Timecontractor.userInfo.Msg,msg);
        contentValues.put(Timecontractor.userInfo.Num,num);
        contentValues.put(Timecontractor.userInfo.Date_,date);
        contentValues.put(Timecontractor.userInfo.Status,status);

        db.insert(Timecontractor.userInfo.TABLE,null,contentValues);
    }

    public Cursor getInformation(SQLiteDatabase db){
        Cursor cursor;
        String[] projection= {
                Timecontractor.userInfo.Id_,
                Timecontractor.userInfo.Msg,
                Timecontractor.userInfo.Num,
                Timecontractor.userInfo.Date_,
                Timecontractor.userInfo.Status
        };

        cursor=db.query(Timecontractor.userInfo.TABLE,projection,null,null,null,null,null);
        return cursor;
    }

    public int updateValues(int id,String msg, String num, long date, String status, SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();


        contentValues.put(Timecontractor.userInfo.Msg,msg);
        contentValues.put(Timecontractor.userInfo.Num,num);
        contentValues.put(Timecontractor.userInfo.Date_,date);
        contentValues.put(Timecontractor.userInfo.Status,status);

        //contentValues.put(contractor_notice.userInfo.PASSWORD,password);
        /*String selection = Time_contractor.userInfo.Id_ + " LIKE?";
        int[] id_ = {id};
        int count=db.update(Time_contractor.userInfo.TABLE,contentValues,selection,id_);*/

        int count = db.update(Timecontractor.userInfo.TABLE,contentValues,
                Timecontractor.userInfo.Id_+"="+id, null); //"id="+id

        return count;
    }

    public int updateStatus(int id, String status, SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();

        contentValues.put(Timecontractor.userInfo.Status,status);

        int count = db.update(Timecontractor.userInfo.TABLE,contentValues,
                Timecontractor.userInfo.Id_+"="+id, null);

        return count;
    }

    public Cursor getId(SQLiteDatabase db) { //to get the largest id and compare with firebase database
        Cursor cursor;
        String[] projection = {Timecontractor.userInfo.Id_};
        cursor=db.query(Timecontractor.userInfo.TABLE,projection,null,null,null,null,null);
        return cursor;
    }

    public void deleteRow(int id, SQLiteDatabase db) {
        //String selection = Time_contractor.userInfo.Id_ + " LIKE?";
        //String[] temp_id = {str_id};
        db.delete(Timecontractor.userInfo.TABLE,Timecontractor.userInfo.Id_+"="+id,null);
        //selection, temp_id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Myclassitem> Alldata() {

        ArrayList<Myclassitem> data = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Timecontractor.userInfo.TABLE, null);

        Myclassitem myclassitem;

            if (c.moveToFirst()) {
                do{
                     myclassitem = new Myclassitem();

                myclassitem.setId(c.getInt(c.getColumnIndex(Timecontractor.userInfo.Id_)));
                myclassitem.setMessage(c.getString(c.getColumnIndex(Timecontractor.userInfo.Msg)));
                myclassitem.setNumber(c.getString(c.getColumnIndex(Timecontractor.userInfo.Num)));
                myclassitem.setDate(c.getLong(c.getColumnIndex(Timecontractor.userInfo.Date_)));
                myclassitem.setStatus(c.getString(c.getColumnIndex(Timecontractor.userInfo.Status)));
                    Log.i("----", "get_list: "+myclassitem.getMessage());
                data.add(myclassitem);

                } while (c.moveToNext());

            }

            c.close();
            return data;
        }

}

