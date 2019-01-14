package com.example.kratirastogi.healthclubmembershipapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kratirastogi.healthclubmembershipapp.bean.Health;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.ArrayList;

public class Communicate extends Fragment
{    ListView listvw;
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Health> healthArrayList;
    Health health;
    String name,phno;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthManager=new HealthManager(getContext());
        sqLiteDatabase=healthManager.openDb();
        healthArrayList=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.communicate, container, false);
        listvw = view.findViewById(R.id.listvw);
        Cursor c = sqLiteDatabase.query(HealthConstants.DBCLERKTABLE, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String memname = c.getString(c.getColumnIndex(HealthConstants.COL_CLKNAME));
                String mph = c.getString(c.getColumnIndex(HealthConstants.COL_CLKPHNO));

                health = new Health(memname,mph);
                healthArrayList.add(health);

            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<Health> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, healthArrayList);
        listvw.setAdapter(arrayAdapter);
        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                health=healthArrayList.get(position);
                String nm=health.getMname();
                final String ph=health.getMphno();
                final String []value={nm};

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Start communication");
                builder.setPositiveButton("call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        int r=   sqLiteDatabase.delete(HealthConstants.DBCLERKTABLE,HealthConstants.COL_CLKNAME+"=?",value);
                        if(r>0)
                        {

                            Intent obj=new Intent(Intent.ACTION_CALL);
                            Uri uri=Uri.parse("tel:" +ph);
                            obj.setData(uri);
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(obj);

                        }
                    }
                });
                builder.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getContext(),SmsAct.class);
                        i.putExtra("key",ph);
                        startActivity(i);
                    }
                });
                AlertDialog b=builder.create();
                b.show();


            }
        });
        return view;
    }
}
