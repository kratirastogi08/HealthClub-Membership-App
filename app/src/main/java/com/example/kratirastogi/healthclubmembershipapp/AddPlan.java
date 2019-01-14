package com.example.kratirastogi.healthclubmembershipapp;

import android.content.ContentValues;
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

public class AddPlan extends Fragment {
    EditText txtplanid,txtplanname,txtfac,txtdur,txtcharge;
    Button btnadd;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.addplan,container,false);
        txtplanid=view.findViewById(R.id.txtplanid);
        txtplanname=view.findViewById(R.id.txtplanname);
        txtfac=view.findViewById(R.id.txtfac);
        txtcharge=view.findViewById(R.id.txtcharge);
        txtdur=view.findViewById(R.id.txtdur);
        btnadd=view.findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtplanid.getText().toString();
                String name = txtplanname.getText().toString();
                String fac = txtfac.getText().toString();
                String charge = txtcharge.getText().toString();
                String duration = txtdur.getText().toString();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(fac) || TextUtils.isEmpty(charge) || TextUtils.isEmpty(duration)) {
                    Toast.makeText(getActivity(), "Enter data", Toast.LENGTH_SHORT).show();
                } else {


                    ContentValues contentValues = new ContentValues();
                    contentValues.put(HealthConstants.COL_PLANID, id.toLowerCase());
                    contentValues.put(HealthConstants.COL_PLANAME, name);
                    contentValues.put(HealthConstants.COL_FAC, fac);
                    contentValues.put(HealthConstants.COL_CHARGE, charge);
                    contentValues.put(HealthConstants.COL_DURATION, duration);

                    long rownum = sqLiteDatabase.insert(HealthConstants.DBPLANTABLE, null, contentValues);
                    if (rownum > 0) {
                        Toast.makeText(getActivity(), "data inserted", Toast.LENGTH_SHORT).show();
                        txtplanid.setText("");
                        txtplanname.setText("");
                        txtfac.setText("");
                        txtcharge.setText("");
                        txtdur.setText("");
                    }
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
