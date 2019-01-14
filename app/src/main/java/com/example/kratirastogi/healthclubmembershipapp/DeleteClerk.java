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
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class DeleteClerk extends Fragment {
    ListView lv;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Clerk> arrayList = new ArrayList<>();
    Clerk clerk;
    String cid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        arrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deleteclerk, container, false);
        lv = view.findViewById(R.id.lv);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBLOGINTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String clkid = c.getString(c.getColumnIndex(HealthConstants.COL_ID));

                clerk = new Clerk(clkid);
                arrayList.add(clerk);

            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Clerk> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                clerk=arrayList.get(position);
                String clid=clerk.getId();
                final String []value={clid};

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int r=   sqLiteDatabase.delete(HealthConstants.DBLOGINTABLE,HealthConstants.COL_ID+"=?",value);
                        if(r>0)
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        healthManager.closeDb();
    }
}
