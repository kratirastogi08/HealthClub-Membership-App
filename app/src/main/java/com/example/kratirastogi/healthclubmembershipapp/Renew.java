package com.example.kratirastogi.healthclubmembershipapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.bean.Health;
import com.example.kratirastogi.healthclubmembershipapp.bean.Spplan;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Renew extends Fragment implements View.OnClickListener {
    EditText txtmemid, txtdateom, txtdateoe;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    Spinner spplnid;
    ArrayList<Spplan> list;
    Spplan spplan;
    String item, expdt;
    Button btnrenew;
    Calendar mcurrentDate, cexp = Calendar.getInstance();
    int day, month, year;
    java.sql.Date dated1, dated2,dated12;
    String date2,date1,mid;
    Date d3;
long date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager = new HealthManager(getContext());
        sqLiteDatabase = healthManager.openDb();
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.renew, container, false);
        txtmemid = view.findViewById(R.id.txtmemid);
        txtdateom = view.findViewById(R.id.txtdateom);
        txtdateoe = view.findViewById(R.id.txtdateoe);
        btnrenew = view.findViewById(R.id.btnrenew);
        spplnid = view.findViewById(R.id.spplnid);
        txtdateom.setOnClickListener(this);
        txtdateoe.setOnClickListener(this);
        txtdateom.setOnClickListener(this);
        btnrenew.setOnClickListener(this);


        Cursor c = sqLiteDatabase.query(HealthConstants.DBPLANTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String plnid = c.getString(c.getColumnIndex(HealthConstants.COL_PLANID));
                spplan = new Spplan(plnid);
                list.add(spplan);
            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Spplan> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        spplnid.setAdapter(arrayAdapter);
        spplnid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = spplnid.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public String getDate() {
        StringBuilder builder = new StringBuilder();
        builder.append(year + "/");
        builder.append((month + 1) + "/");
        builder.append(day);
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtdateom) {
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

                    mcurrentDate.set(year, month, day);
                    date = mcurrentDate.getTimeInMillis();
                    cexp.set(year, month, day);
                    txtdateom.setText(getDate());
                    java.util.Date d = mcurrentDate.getTime();
                    dated1 = new java.sql.Date(d.getTime());
                    Log.d("ff",dated1.toString());
                    checkexpiry(true);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
            mDatePicker.setTitle("Select date");
            mDatePicker.show();

        }

        if (v.getId() == R.id.btnrenew) {


             mid = txtmemid.getText().toString();


            String dom = dated1.toString();
            String doe = dated2.toString();

            //check empty
            if (TextUtils.isEmpty(mid) || TextUtils.isEmpty(mid) || TextUtils.isEmpty(dom)) {
                Toast.makeText(getActivity(), "insert data", Toast.LENGTH_SHORT).show();
            } else {
                Calendar curr=Calendar.getInstance();
                Date dat=curr.getTime();
                java.sql.Date dated11=new java.sql.Date(dat.getTime());
                date1=dated11.toString();
                String value[] = {date1,mid};
                String args[] = {HealthConstants.COL_DATEEXP};
                Cursor c1 = sqLiteDatabase.query(HealthConstants.DBMEMBER, null, HealthConstants.COL_DATEEXP + "<? and"+HealthConstants.COL_MEMID+"=?", value, null, null, null);
                if (c1 != null && c1.moveToFirst()) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(HealthConstants.COL_MEMID, mid);

                        contentValues.put(HealthConstants.COL_PLANID, item);
                        contentValues.put(HealthConstants.COL_DATEMEM, dom);
                        contentValues.put(HealthConstants.COL_DATEEXP, doe);


                        long rownum = sqLiteDatabase.insert(HealthConstants.DBRENEW, null, contentValues);

                        if (rownum < 0) {
                            Toast.makeText(getActivity(), "Insert data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Plan renewd", Toast.LENGTH_SHORT).show();
                            txtmemid.setText("");
                            txtdateoe.setText("");
                            txtdateom.setText("");
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "plan not expired yet", Toast.LENGTH_SHORT).show();
                        txtmemid.setText("");
                        txtdateoe.setText("");
                        txtdateom.setText("");
                    }
                    c1.close();
                } /*else {
                    Toast.makeText(getActivity(), "no such id exist", Toast.LENGTH_SHORT).show();
                    txtmemid.setText("");
                    txtdateoe.setText("");
                    txtdateom.setText("");
                }*/
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
            java.util.Date d8 = cexp.getTime();
            dated2 = new java.sql.Date(d8.getTime());
            int yexp = cexp.get(Calendar.YEAR);
            int mexp = cexp.get(Calendar.MONTH) + 1;
            int dexp = cexp.get(Calendar.DAY_OF_MONTH);
            expdt = yexp + "/" + mexp + "/" + dexp;


        }
        txtdateoe.setText(expdt);
    }
}