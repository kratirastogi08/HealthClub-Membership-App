package com.example.kratirastogi.healthclubmembershipapp.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class HealthHelper extends SQLiteOpenHelper {
    Context context;
    public HealthHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HealthConstants.STQUERY);
        db.execSQL(HealthConstants.STQUERY2);
        db.execSQL(HealthConstants.STQUERY4);
        db.execSQL(HealthConstants.STQUERY1);
        db.execSQL(HealthConstants.STQUERY3);


        Toast.makeText(context, "table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
