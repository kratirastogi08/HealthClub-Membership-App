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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.kratirastogi.healthclubmembershipapp.bean.Lvplan;

import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class Planwise extends Fragment {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;

    ListView lsv;
Lvplan lvplan;
    ArrayList<Lvplan> lvplanArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        lvplanArrayList=new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.planwise, container, false);

        lsv = view.findViewById(R.id.lsv);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String mid = c.getString(c.getColumnIndex(HealthConstants.COL_MEMID));
                String mnm = c.getString(c.getColumnIndex(HealthConstants.COL_MEMNAME));
                String pid = c.getString(c.getColumnIndex(HealthConstants.COL_PLANID));
                lvplan = new Lvplan(mid,mnm,pid);
                lvplanArrayList.add(lvplan);

            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Lvplan> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lvplanArrayList);
        lsv.setAdapter(arrayAdapter);


        return view;

    }
}