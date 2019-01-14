package com.example.kratirastogi.healthclubmembershipapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;


public class UpdateMembersInfos extends Fragment
{
    EditText txtmid, txtmnm, txtem, txtad, txtphn;
    Button btnupdate, btnsearch;
    SQLiteDatabase sqLiteDatabase;
    HealthManager healthManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.updatememberinfo,container,false);
        txtmid = view.findViewById(R.id.txtmid);

        txtem = view.findViewById(R.id.txtem);

        txtad = view.findViewById(R.id.txtad);
        txtphn = view.findViewById(R.id.txtphn);

        btnsearch = view.findViewById(R.id.btnsearch);
        btnupdate = view.findViewById(R.id.btnupdate);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mid=txtmid.getText().toString();
                String[]args={mid};
                String[]colnames={HealthConstants.COL_MEMNAME, HealthConstants.COL_EMAIL, HealthConstants.COL_GENDER, HealthConstants.COL_ADD, HealthConstants.COL_PHNO, HealthConstants.COL_DOB, HealthConstants.COL_OCC, HealthConstants.COL_PLANID, HealthConstants.COL_DATEMEM, HealthConstants.COL_DATEEXP};
                Cursor c=sqLiteDatabase.query(HealthConstants.DBMEMBER,colnames,HealthConstants.COL_MEMID+"=?",args,null,null,null);
                if(c!=null && c.moveToFirst())
                {


                    String em = c.getString(c.getColumnIndex(HealthConstants.COL_EMAIL));
                    txtem.setText(em);


                    String ad = c.getString(c.getColumnIndex(HealthConstants.COL_ADD));
                    txtad.setText(ad);
                    String phn = c.getString(c.getColumnIndex(HealthConstants.COL_PHNO));
                    txtphn.setText(phn);


                }
                else
                {
                    Toast.makeText(getActivity(), "no such id exist", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=txtmid.getText().toString();
                String em=txtem.getText().toString();
                String ph=txtphn.getText().toString();

                String add=txtad.getText().toString();

                ContentValues cv=new ContentValues();



                cv.put(HealthConstants.COL_EMAIL,em);
                cv.put(HealthConstants.COL_PHNO,ph);
                cv.put(HealthConstants.COL_ADD,add);
                String[]values={id};
                int r= sqLiteDatabase.update(HealthConstants.DBMEMBER,cv,HealthConstants.COL_MEMID+"=?",values);

                if(r>0)
                {
                    Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();
                    txtmid.setText("");
                    txtem.setText("");
                    txtphn.setText("");
                    txtad.setText("");

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
