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

import com.example.kratirastogi.healthclubmembershipapp.bean.Clerk;
import com.example.kratirastogi.healthclubmembershipapp.bean.Member;
import com.example.kratirastogi.healthclubmembershipapp.bean.Plan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class DeleteMember extends Fragment
{   ListView listv;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Member> memArrayList = new ArrayList<>();
    Member member;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        memArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deletemember, container, false);
        listv = view.findViewById(R.id.listv);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String mid = c.getString(c.getColumnIndex(HealthConstants.COL_MEMID));
                String mnm = c.getString(c.getColumnIndex(HealthConstants.COL_MEMNAME));

                member = new Member(mid,mnm);
                memArrayList.add(member);

            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Member> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, memArrayList);
        listv.setAdapter(arrayAdapter);
        listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                member=memArrayList.get(position);
                String memid=member.getMemberid();
                final String []value={memid};

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int r=   sqLiteDatabase.delete(HealthConstants.DBMEMBER,HealthConstants.COL_MEMID+"=?",value);
                        int r1=   sqLiteDatabase.delete(HealthConstants.DBRENEW,HealthConstants.COL_MEMID+"=?",value);
                        if(r>0 && r1>0)
                        {
                            Toast.makeText(getContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                            arrayAdapter.remove(arrayAdapter.getItem(position));
                        }
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
}
