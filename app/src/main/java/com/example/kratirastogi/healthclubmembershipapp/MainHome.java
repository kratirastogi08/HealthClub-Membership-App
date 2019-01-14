package com.example.kratirastogi.healthclubmembershipapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

public class MainHome extends Fragment {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    TextView gold,silver,platinum,premium;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainhome, container, false);
        gold=view.findViewById(R.id.gold);
        silver=view.findViewById(R.id.silver);
        platinum=view.findViewById(R.id.platinum);
        premium=view.findViewById(R.id.premium);
        String p[]={"gold"};
        Cursor c121 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_PLANID+"=?", p, null, null, null);
        gold.setText("");
        int  t = 0;
        if (c121 != null && c121.moveToFirst()) {
            do {
                t++;
            } while (c121.moveToNext());
            gold.setText(String.valueOf(t));

        }
        else
        {
            gold.setText("0");
        }
        String p1[]={"silver"};
        Cursor c12 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_PLANID+"=?", p1, null, null, null);
        silver.setText("");
        int  t1 = 0;
        if (c12 != null && c12.moveToFirst()) {
            do {
                t1++;
            } while (c12.moveToNext());
            silver.setText(String.valueOf(t1));

        }
        else
        {
            silver.setText("0");
        }
        String p2[]={"platinum"};
        Cursor c1 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_PLANID+"=?", p2, null, null, null);
        platinum.setText("");
        int  t11 = 0;
        if (c1 != null && c1.moveToFirst()) {
            do {
                t11++;
            } while (c1.moveToNext());
            platinum.setText(String.valueOf(t11));

        }
        else
        {
            platinum.setText("0");
        }
        String p3[]={"premium"};
        Cursor c13 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_PLANID+"=?", p3, null, null, null);
        premium.setText("");
        int  t111 = 0;
        if (c13 != null && c13.moveToFirst()) {
            do {
                t111++;
            } while (c13.moveToNext());
            premium.setText(String.valueOf(t111));

        }
        else
        {
            premium.setText("0");
        }
        return view;

    }
}