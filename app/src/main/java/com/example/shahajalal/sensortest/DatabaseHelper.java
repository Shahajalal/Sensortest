package com.example.shahajalal.sensortest;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Shahajalal on march 25.
 */
class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "sensorTest.sqlite";
    private static final String DBLOCATION = "data/data/com.example.shahajalal.sensortest/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    DatabaseHelper(Context context) {
        super(context, DBNAME, null, 3);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void OpenDatabase()
    {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen())
        {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);

    }
    public void CloseDatabase(){

        if(mDatabase != null)
        {
            mDatabase.close();
        }
    }
    public  void insertevents(String start,String end){

        OpenDatabase();
        mDatabase.execSQL("insert into events values(null,'"+start+"','"+end+"');");
        CloseDatabase();

    }

    public void inserevents_metaaccelerometer(int id,String sensor,String value,String time){

        OpenDatabase();
        mDatabase.execSQL("insert into events_meta values(null,'"+id+"','"+sensor+"','"+value+"','"+time+"');");
        CloseDatabase();
    }
    public void inserevents_metagyrometer(int id,String sensor,String value,String time){

        OpenDatabase();
        mDatabase.execSQL("insert into events_meta values(null,'"+id+"','"+sensor+"','"+value+"','"+time+"');");
        CloseDatabase();
    }
    public void inserevents_metagysture(int id,String sensor,String value,String time){

        OpenDatabase();
        mDatabase.execSQL("insert into events_meta values(null,'"+id+"','"+sensor+"','"+value+"','"+time+"');");
        CloseDatabase();
    }

    public int fatcheventsid(){
        mDatabase=this.getReadableDatabase();
        Cursor cursor=mDatabase.rawQuery("select id from events order by id desc;",null);
        cursor.moveToFirst();
         String Strid=cursor.getString(0);
         int id=Integer.parseInt(Strid);
         CloseDatabase();
        return id;
    }


    public boolean CopyDB(Context context)
    {

        try {
            InputStream IS = context.getAssets().open(DBNAME);
            String OF = DBLOCATION + DBNAME;
            File f = new File(OF);
            if(f.exists())
            {
                Log.d("DatabaseHelper","Database already exists in the "+OF+" directory");
                return true;
            }
            else
            {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            OutputStream OS = new FileOutputStream(OF,true);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = IS.read(buff))>0)
            {
                OS.write(buff,0,length);
            }
            OS.flush();
            OS.close();
            Log.d("DatabaseHelper","Database copied successfully");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("DatabaseHelper","Copy database failed with message: " + e.getMessage());
            return false;
        }
    }


}
