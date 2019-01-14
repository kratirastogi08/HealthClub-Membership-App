package com.example.kratirastogi.healthclubmembershipapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.bean.Datew;
import com.example.kratirastogi.healthclubmembershipapp.bean.Lvplan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Datewise extends Fragment {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
String date,date1,a,b;
    ListView lisv;
    Datew datew;
    ArrayList<Datew> datewArrayList;
    ArrayList<Datew> renewArrayList;
    Calendar ca,calendar;
    Date d,d1;
    java.sql.Date dated,dat;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        datewArrayList=new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datewise, container, false);

        Calendar curr=Calendar.getInstance();
        Date dat=curr.getTime();
        java.sql.Date dated1=new java.sql.Date(dat.getTime());
        lisv = view.findViewById(R.id.lisv);
        String args[]={dated1.toString()};



        Calendar curr1=Calendar.getInstance();
        Date dat1=curr.getTime();
        java.sql.Date dated4=new java.sql.Date(dat1.getTime());

        String arg[]={dated4.toString()};

        Cursor c2 = sqLiteDatabase.query(HealthConstants.DBMEMBER  , null, HealthConstants.COL_DATEEXP+">?", arg, null, null, null);
        if (c2 != null && c2.moveToFirst()) {
            do {
                String mid = c2.getString(c2.getColumnIndex(HealthConstants.COL_MEMID));
                String dom= c2.getString(c2.getColumnIndex(HealthConstants.COL_DATEMEM));
                String doe = c2.getString(c2.getColumnIndex(HealthConstants.COL_DATEEXP));
                datew = new Datew(mid, dom, doe);
                datewArrayList.add(datew);
                Log.d("msg",doe);


            } while (c2.moveToNext());
        }


        final ArrayAdapter<Datew> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, datewArrayList);
        lisv.setAdapter(arrayAdapter);



        return view;
    }

    private boolean checkexpiry(int eyea, int em, int ed, int cyea, int cm, int cd) {
        Toast.makeText(getContext(), "eyea"+eyea+"  em"+em+" ed "+ed+" cyea"+cyea+" cm"+cm+" cd"+cd, Toast.LENGTH_SHORT).show();
        Log.d("booleancheck","eyea"+eyea+"  em"+em+" ed "+ed+" cyea"+cyea+" cm"+cm+" cd"+cd);
        boolean flag=true;
        if(eyea<=cyea)
            if(em<=cm)
                if(ed<cd)
                    flag=false;
        Log.d("booleancheck",String.valueOf(flag));
        return flag;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthManager.closeDb();
    }
}
