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

public class UpdatePlan extends Fragment {
    EditText planid,planname,charge,duration,fac;
    Button btninfo,update;
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
        View view = inflater.inflate(R.layout.updateplan, container, false);

        planid = view.findViewById(R.id.planid);
        planname = view.findViewById(R.id.planname);
        fac = view.findViewById(R.id.fac);
        charge=view.findViewById(R.id.charge);
        duration=view.findViewById(R.id.duration);
        btninfo=view.findViewById(R.id.btninfo);
        update=view.findViewById(R.id.update);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = planid.getText().toString();
                if(TextUtils.isEmpty(pid))
                {
                    Toast.makeText(getActivity(), " enter planid", Toast.LENGTH_SHORT).show();
                }
                String[] args = {pid};

                String[] colnames = {HealthConstants.COL_PLANAME,HealthConstants.COL_FAC,HealthConstants.COL_CHARGE,HealthConstants.COL_DURATION};
                Cursor c = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, colnames, HealthConstants.COL_PLANID + "=?", args, null, null, null);
                if (c != null && c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndex(HealthConstants.COL_PLANAME));
                    planname.setText(name);
                    String facilities = c.getString(c.getColumnIndex(HealthConstants.COL_FAC));
                    fac.setText(facilities);
                    String ch = c.getString(c.getColumnIndex(HealthConstants.COL_CHARGE));
                    charge.setText(ch);
                    String dur = c.getString(c.getColumnIndex(HealthConstants.COL_DURATION));
                    duration.setText(dur);

                }
                else {
                    Toast.makeText(getActivity(), "no such id exists", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }

        });

       update .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plnid=planid.getText().toString();
                String plnm=planname.getText().toString();
                String faclty=fac.getText().toString();
                String c=charge.getText().toString();
                String drtn=duration.getText().toString();
           if(TextUtils.isEmpty(plnid))
           {
               Toast.makeText(getActivity(), " enter planid", Toast.LENGTH_SHORT).show();
           }
           else {
               ContentValues cv = new ContentValues();

               cv.put(HealthConstants.COL_PLANAME, plnm);
               cv.put(HealthConstants.COL_FAC, faclty);
               cv.put(HealthConstants.COL_CHARGE, c);
               cv.put(HealthConstants.COL_DURATION, drtn);
               String[] values = {plnid};
               int r = sqLiteDatabase.update(HealthConstants.DBPLANTABLE, cv, HealthConstants.COL_PLANID + "=?", values);
               if (r > 0) {
                   Toast.makeText(getContext(), "data updated", Toast.LENGTH_SHORT).show();
                   planid.setText("");
                   planname.setText("");
                   fac.setText("");
                   charge.setText("");
                   duration.setText("");
               }
           }
            }

        });
        return view;
    }
}
