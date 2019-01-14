package com.example.kratirastogi.healthclubmembershipapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

public class UpdateClerk extends Fragment {
    EditText clkid, clkpass,clkph,clkmail,clkaddress;
    Button showinfo, btnupdate;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updateclerk, container, false);

        clkid = view.findViewById(R.id.clkid);
        clkpass = view.findViewById(R.id.clkpass);
        clkph = view.findViewById(R.id.clkph);
        clkmail = view.findViewById(R.id.clkmail);
        clkaddress = view.findViewById(R.id.clkaddress);

        showinfo = view.findViewById(R.id.showinfo);
        btnupdate=view.findViewById(R.id.btnupdate);
        showinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = clkid.getText().toString();
                String[] args = {cid};

                String[] colnames = {HealthConstants.COL_PASS};
                Cursor c = sqLiteDatabase.query(HealthConstants.DBLOGINTABLE, colnames, HealthConstants.COL_ID + "=?", args, null, null, null);
                if (c != null && c.moveToFirst()) {
                    String id = c.getString(c.getColumnIndex(HealthConstants.COL_PASS));
                    clkpass.setText(id);

                    String[] colname = {HealthConstants.COL_CLKPHNO,HealthConstants.COL_CLKMAIL,HealthConstants.COL_CLKADD};
                    Cursor c1 = sqLiteDatabase.query(HealthConstants.DBCLERKTABLE, colname, HealthConstants.COL_ID + "=?", args, null, null, null);
                    if (c1 != null && c1.moveToFirst()) {
                        String ph = c1.getString(c1.getColumnIndex(HealthConstants.COL_CLKPHNO));
                        clkph.setText(ph);
                        String mail = c1.getString(c1.getColumnIndex(HealthConstants.COL_CLKMAIL));
                        String add = c1.getString(c1.getColumnIndex(HealthConstants.COL_CLKADD));

                        clkmail.setText(mail);
                        clkaddress.setText(add);


                    }
                    c1.close();
                }
                else{
                    Toast.makeText(getActivity(), "no such id exist", Toast.LENGTH_SHORT).show();
                }
                c.close();

            }

        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clerkid=clkid.getText().toString();
                String clerkpass=clkpass.getText().toString();
                String clerkph=clkph.getText().toString();
                String clerkm=clkmail.getText().toString();
                String clerka=clkaddress.getText().toString();

                ContentValues cv=new ContentValues();

                cv.put(HealthConstants.COL_PASS,clerkpass);
                ContentValues cv1=new ContentValues();
                cv1.put(HealthConstants.COL_CLKPHNO,clerkph);
                cv1.put(HealthConstants.COL_CLKMAIL,clerkm);
                cv1.put(HealthConstants.COL_CLKADD,clerka);
                String[]values={clerkid};
                int r= sqLiteDatabase.update(HealthConstants.DBLOGINTABLE,cv,HealthConstants.COL_ID+"=?",values);
                int r1= sqLiteDatabase.update(HealthConstants.DBCLERKTABLE,cv1,HealthConstants.COL_ID+"=?",values);
                if(r>0 && r1>0)
                {
                    Toast.makeText(getContext(), "data updated", Toast.LENGTH_SHORT).show();
                    clkid.setText("");
                    clkpass.setText("");
                    clkph.setText("");
                    clkmail.setText("");
                    clkaddress.setText("");
                }

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

