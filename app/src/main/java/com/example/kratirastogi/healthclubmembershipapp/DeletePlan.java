package com.example.kratirastogi.healthclubmembershipapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;


import com.example.kratirastogi.healthclubmembershipapp.bean.Plan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class DeletePlan extends Fragment {
    ListView listView;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Plan> planArrayList = new ArrayList<>();
    Plan plan;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        planArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deleteplan, container, false);
        listView = view.findViewById(R.id.listview);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String plnid = c.getString(c.getColumnIndex(HealthConstants.COL_PLANID));
                String plnme = c.getString(c.getColumnIndex(HealthConstants.COL_PLANAME));
                plan = new Plan(plnid,plnme);
                planArrayList.add(plan);

            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Plan> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, planArrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                plan=planArrayList.get(position);
                String i=plan.getPlanid();
                final String []value={i};

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int r=   sqLiteDatabase.delete(HealthConstants.DBPLANTABLE,HealthConstants.COL_PLANID+"=?",value);
                        if(r>0)
                        {
                            Toast.makeText(getContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                            arrayAdapter.remove(arrayAdapter.getItem(position));
                        }
                        int r1=   sqLiteDatabase.delete(HealthConstants.DBMEMBER,HealthConstants.COL_PLANID+"=?",value);
                        if(r1>0)
                        {}
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog b=builder.create();
                b.show();
                return false;

            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthManager.closeDb();
    }
}
