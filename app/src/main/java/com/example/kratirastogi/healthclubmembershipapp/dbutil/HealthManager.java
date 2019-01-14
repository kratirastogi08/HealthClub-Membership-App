package com.example.kratirastogi.healthclubmembershipapp.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class HealthManager {
    Context context;
    HealthHelper healthHelper;
    SQLiteDatabase sqLiteDatabase;
    public  HealthManager(Context context)
    {
        this.context=context;
        healthHelper=new HealthHelper(context,HealthConstants.DBNM,null,HealthConstants.DBVERSION);

    }
    public SQLiteDatabase openDb()
    {
        sqLiteDatabase= healthHelper.getWritableDatabase();
        return sqLiteDatabase;
    }
    public void closeDb()
    {
        healthHelper.close();
    }
}
