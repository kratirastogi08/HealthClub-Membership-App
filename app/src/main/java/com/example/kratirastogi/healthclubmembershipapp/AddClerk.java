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

public class AddClerk extends Fragment {
    EditText txtpass,txtid,txtname,txtphno,txtmail,txtadd;
    Button btncreate;
    SQLiteDatabase sqLiteDatabase;
    HealthManager healthManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.addclerk,container,false);
        txtid= view.findViewById(R.id.txtid);
        txtpass=view.findViewById(R.id.txtpass);
        txtname=view.findViewById(R.id.txtname);
        txtphno=view.findViewById(R.id.txtphno);
        txtmail=view.findViewById(R.id.txtmail);
        txtadd=view.findViewById(R.id.txtadd);

        btncreate=view.findViewById(R.id.btncreate);
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=txtid.getText().toString();
                String pass=txtpass.getText().toString();
                int len=pass.length();

                String nm=txtname.getText().toString();
                String ph=txtphno.getText().toString();
                String mail=txtmail.getText().toString();
                String address=txtadd.getText().toString();
                if(TextUtils.isEmpty(id)||TextUtils.isEmpty(pass)|| TextUtils.isEmpty(nm)||TextUtils.isEmpty(ph)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(address))
                {
                    Toast.makeText(getContext(), "Enter data", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (len < 8) {
                        Toast.makeText(getActivity(), "password should be greater than 8 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(HealthConstants.COL_ID, id);
                        contentValues.put(HealthConstants.COL_PASS, pass);

                        ContentValues contentValues1 = new ContentValues();
                        contentValues1.put(HealthConstants.COL_ID, id);
                        contentValues1.put(HealthConstants.COL_CLKNAME, nm);
                        contentValues1.put(HealthConstants.COL_CLKPHNO, ph);
                        contentValues1.put(HealthConstants.COL_CLKMAIL, mail);
                        contentValues1.put(HealthConstants.COL_CLKADD, address);

                        long rownum = sqLiteDatabase.insert(HealthConstants.DBLOGINTABLE, null, contentValues);
                        long rownum1 = sqLiteDatabase.insert(HealthConstants.DBCLERKTABLE, null, contentValues1);
                        if (rownum > 0 && rownum1 > 0) {
                            Toast.makeText(getContext(), "data inserted", Toast.LENGTH_SHORT).show();
                            txtid.setText("");
                            txtpass.setText("");
                            txtname.setText("");
                            txtphno.setText("");
                            txtmail.setText("");
                            txtadd.setText("");
                        }
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
