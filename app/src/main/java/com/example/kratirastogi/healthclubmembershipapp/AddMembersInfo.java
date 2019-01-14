package com.example.kratirastogi.healthclubmembershipapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;



import com.example.kratirastogi.healthclubmembershipapp.bean.Spplan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMembersInfo extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener
{
    EditText txtmid,txtmnm,txtem,txtad,txtphn,txtdob,txtoc,txtdom,txtdoe;
    Spinner spinner;
    Button btnadd;
    RadioGroup rdgroup;
    RadioButton rdmale,rdfemale,rd;
    SQLiteDatabase sqLiteDatabase;
    HealthManager healthManager;
    Spplan spplan;
    ArrayList<Spplan> sparraylist;
    String item=null,expdt,gender;
    int day,month,year,id;
    long date ,date1,exp;
    int y;
Calendar mcurrentDate,cexp=Calendar.getInstance();
    java.sql.Date dated1=null,dated,dated2;
    boolean flag=false;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
        sparraylist=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.addmembersinfo,container,false);
        txtmid= view.findViewById(R.id.txtmid);
        txtmnm=view.findViewById(R.id.txtmnm);
        txtem=view.findViewById(R.id.txtem);
        rdgroup=view.findViewById(R.id.rdgroup);
        rdmale=view.findViewById(R.id.rdmale);
        rdfemale=view.findViewById(R.id.rdfemale);


        txtad=view.findViewById(R.id.txtad);
        txtphn=view.findViewById(R.id.txtphn);
        txtdob=view.findViewById(R.id.txtdob);
        txtoc=view.findViewById(R.id.txtoc);


        txtdom=view.findViewById(R.id.txtdom);
        txtdoe=view.findViewById(R.id.txtdoe);
        btnadd=view.findViewById(R.id.btnadd);
        spinner=view.findViewById(R.id.spinner);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String plnid = c.getString(c.getColumnIndex(HealthConstants.COL_PLANID));
                spplan = new Spplan(plnid);
                sparraylist.add(spplan);
            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Spplan> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, sparraylist);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        txtdom.setOnClickListener(this);
        txtdob.setOnClickListener(this);
        btnadd.setOnClickListener(this);



        return view;
    }
    public String getDate()
    {
        StringBuilder builder=new StringBuilder();
        builder.append(year+"/");
        builder.append((month+1)+"/");
        builder.append(day);
        return builder.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        healthManager.closeDb();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtdob) {
            final Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int yeard, int monthd, int dayOfMonth) {
                    day = dayOfMonth;
                    month = monthd;
                    year = yeard;
                    mcurrentDate.set(year, month, day);
                    date1 = mcurrentDate.getTimeInMillis();


                    txtdob.setText(getDate());
                    java.util.Date d = mcurrentDate.getTime();
                    dated = new java.sql.Date(d.getTime());
                }
            }, mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
            mDatePicker.setTitle("Select date");
            mDatePicker.show();
             y=mYear-year;



        }
        if (v.getId() == R.id.txtdom) {
            mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int yeard, int monthd, int dayOfMonth) {
                    day = dayOfMonth;
                    month = monthd;
                    year = yeard;
                    cexp.set(year, month, day);
                    mcurrentDate.set(year, month, day);
                    date = mcurrentDate.getTimeInMillis();
                    txtdom.setText(getDate());
                    java.util.Date d = mcurrentDate.getTime();
                    dated1 = new java.sql.Date(d.getTime());
                    checkexpiry(true);

                }
            }, mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
            mDatePicker.setTitle("Select date");
            mDatePicker.show();


        }
        if (v.getId() == R.id.btnadd) {
            if (rdmale.isChecked()) {
                gender = rdmale.getText().toString();
            } else if (rdfemale.isChecked())
                gender = rdfemale.getText().toString();
            else
                gender = null;

            String mid = txtmid.getText().toString();
            String mnm = txtmnm.getText().toString();
            String em = txtem.getText().toString();
            String ad = txtad.getText().toString();
            String phn = txtphn.getText().toString();
            String dob = txtdob.getText().toString();

            String oc = txtoc.getText().toString();

            String dom = dated1.toString();
            String doe = dated2.toString();
            //check empty
            if (TextUtils.isEmpty(mid) || TextUtils.isEmpty(mnm) || TextUtils.isEmpty(em) || TextUtils.isEmpty(ad) || TextUtils.isEmpty(phn) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(oc) || TextUtils.isEmpty(dom) || TextUtils.isEmpty(doe) || TextUtils.isEmpty(gender)) {
                Toast.makeText(getActivity(), "insert data", Toast.LENGTH_SHORT).show();
                txtmid.setText("");
                txtdoe.setText("");
                txtdom.setText("");
                txtoc.setText("");
                txtdob.setText("");
                txtad.setText("");
                txtphn.setText("");
                txtem.setText("");
                txtmnm.setText("");

            } else
                if(y<20)
                {
                    Toast.makeText(getContext(), "not eligible", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), ""+y, Toast.LENGTH_SHORT).show();
                    txtmid.setText("");
                    txtdoe.setText("");
                    txtdom.setText("");
                    txtoc.setText("");
                    txtdob.setText("");
                    txtad.setText("");
                    txtphn.setText("");
                    txtem.setText("");
                    txtmnm.setText("");
            }

            else {

                ContentValues contentValues = new ContentValues();
                contentValues.put(HealthConstants.COL_MEMID, mid);
                contentValues.put(HealthConstants.COL_MEMNAME, mnm);
                contentValues.put(HealthConstants.COL_EMAIL, em);
                contentValues.put(HealthConstants.COL_GENDER, gender);
                contentValues.put(HealthConstants.COL_ADD, ad);
                contentValues.put(HealthConstants.COL_PHNO, phn);
                contentValues.put(HealthConstants.COL_DOB, dated.toString());
                contentValues.put(HealthConstants.COL_OCC, oc);
                contentValues.put(HealthConstants.COL_PLANID, item);
                contentValues.put(HealthConstants.COL_DATEMEM, dom);
                contentValues.put(HealthConstants.COL_DATEEXP, doe);

                long rownum = sqLiteDatabase.insert(HealthConstants.DBMEMBER, null, contentValues);
                if (rownum < 0) {
                    Toast.makeText(getActivity(), "Insert data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_SHORT).show();
                    txtmid.setText("");
                    txtdoe.setText("");
                    txtdom.setText("");
                    txtoc.setText("");
                    txtdob.setText("");
                    txtad.setText("");
                    txtphn.setText("");
                    txtem.setText("");
                    txtmnm.setText("");
                    rdgroup.clearCheck();

                }


            }

        }
    }

    private void checkexpiry(boolean check) {
                String args[] = {HealthConstants.COL_DURATION};
                String value[] = {item};
                Cursor cursor = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, args, HealthConstants.COL_PLANID + "=?", value, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String dur = cursor.getString(cursor.getColumnIndex(HealthConstants.COL_DURATION));
                    int duration = Integer.parseInt(dur);
                    //long dateofexp=duration+date;

                    Log.d("duration", String.valueOf(duration));

                    cexp.add(Calendar.DATE, duration);//expiry date
                    Log.d("date", String.valueOf(cexp.get(Calendar.DATE)));
                    java.util.Date d = cexp.getTime();
                    dated2 = new java.sql.Date(d.getTime());
                    int yexp = cexp.get(Calendar.YEAR);
                    int mexp = cexp.get(Calendar.MONTH) + 1;
                    int dexp = cexp.get(Calendar.DAY_OF_MONTH);
                    expdt = yexp + "/" + mexp + "/" + dexp;


                }
                txtdoe.setText(expdt);

        }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item=spinner.getSelectedItem().toString();
        txtdom.setText("");
        txtdoe.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        item=null;
        txtdom.setText("");
        txtdoe.setText("");
    }




    }

