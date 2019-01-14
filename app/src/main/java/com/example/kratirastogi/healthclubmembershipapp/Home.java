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

public class Home extends Fragment {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    TextView all,male,female;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cardview, container, false);
        all=view.findViewById(R.id.all);
        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        String p[]={"Male"};
        Cursor c121 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_GENDER+"=?", p, null, null, null);
        male.setText("");
        int  t = 0;
        if (c121 != null && c121.moveToFirst()) {
            do {
               t++;
               } while (c121.moveToNext());
            male.setText(String.valueOf(t));

        }
        else
        {
            male.setText("0");
        }
        String p1[]={"Female"};
        Cursor c12 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_GENDER+"=?", p1, null, null, null);
       female.setText("");
        int  t1 = 0;
        if (c12 != null && c12.moveToFirst()) {
            do {
                t1++;
            } while (c12.moveToNext());
            female.setText(String.valueOf(t1));

        }
        else
        {
            female.setText("0");
        }

        Cursor c1 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, null, null, null, null, null);
        all.setText("");
        int  t11 = 0;
        if (c1 != null && c1.moveToFirst()) {
            do {
                t11++;
            } while (c1.moveToNext());
            all.setText(String.valueOf(t11));

        }
        else
        {
            all.setText("0");
        }
        return view;

    }
}
