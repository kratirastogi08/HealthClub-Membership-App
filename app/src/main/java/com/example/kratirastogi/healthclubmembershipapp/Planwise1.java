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
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.bean.Lvplan;
import com.example.kratirastogi.healthclubmembershipapp.bean.Member;
import com.example.kratirastogi.healthclubmembershipapp.bean.Spplan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class Planwise1 extends Fragment {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    Spinner sp;
    Spplan spplan;
    ListView lvw;
    Member member;
    ArrayList<Member> memArrayList;
    ArrayList<Spplan> spplans;
    String item;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        memArrayList=new ArrayList<>();
        spplans=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.plan,container,false);
        sp=view.findViewById(R.id.sp);
        lvw=view.findViewById(R.id.lvw);

        Cursor c = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String plnid = c.getString(c.getColumnIndex(HealthConstants.COL_PLANID));
                spplan = new Spplan(plnid);
                spplans.add(spplan);
            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Spplan> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, spplans);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=parent.getItemAtPosition(position).toString();

                String value[]={item};

                Cursor c1 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_PLANID+"=?", value, null, null, null);
                if (c1 != null && c1.moveToFirst()) {
                    lvw.setAdapter(null);
                    memArrayList=new ArrayList<>();
                    do {
                        String mid = c1.getString(c1.getColumnIndex(HealthConstants.COL_MEMID));
                        String mnm = c1.getString(c1.getColumnIndex(HealthConstants.COL_MEMNAME));

                        member = new Member(mid,mnm);
                        memArrayList.add(member);

                    } while (c1.moveToNext());

                    final ArrayAdapter<Member> arrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, memArrayList);

                    //  Toast.makeText(getActivity(), "abc", Toast.LENGTH_SHORT).show();
                    lvw.setAdapter(arrayAdapter1);
                    memArrayList=null;
                }

                c1.close();



              //  arrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }



}
