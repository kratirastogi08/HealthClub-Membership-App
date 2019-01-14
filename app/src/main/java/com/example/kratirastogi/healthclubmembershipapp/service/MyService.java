package com.example.kratirastogi.healthclubmembershipapp.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.R;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthConstants;
import com.example.kratirastogi.healthclubmembershipapp.dbutil.HealthManager;

import java.util.Calendar;

public class MyService extends Service {
    HealthManager healthManager;
    SQLiteDatabase sqLiteDatabase;
    String date;

    @Nullable

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        healthManager=new HealthManager(this);
        sqLiteDatabase=healthManager.openDb();

    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        *//*Toast.makeText(getApplicationContext(), "servicestart", Toast.LENGTH_SHORT).show();
        Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);


       *//**//* if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);*//**//*


        if(hour==20||hour==21) {

       }*//*
        service(getApplicationContext());
        return START_STICKY;
    }*/
public void service(Context mContext)
{
    AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(mContext, Notification.class);
    PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);

    // reset previous pending intent
    alarmManager.cancel(pendingIntent);

    // Set the alarm to start at approximately 08:00 morning.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 22);
    calendar.set(Calendar.MINUTE, 48);
    calendar.set(Calendar.SECOND, 0);

    // if the scheduler date is passed, move scheduler time to tomorrow
    if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1);
    }
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, pendingIntent);










    Calendar calendar1=Calendar.getInstance();
    int curryear=calendar1.get(Calendar.YEAR);
    int currmonth=calendar1.get(Calendar.MONTH);
    int currday=calendar1.get(Calendar.DAY_OF_MONTH);
    calendar1.set(curryear,currmonth,currday);
    java.util.Date d= calendar.getTime();
    java.sql.Date dated=new java.sql.Date(d.getTime());
    date=dated.toString();

   // Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
    String value[]={date};
    Cursor cursor=sqLiteDatabase.query(HealthConstants.DBMEMBER,null,HealthConstants.COL_DATEEXP+"=?",value,null,null,null);

   // Log.d("cursorcheck",cursor.toString());
    if(cursor!=null && cursor.moveToFirst())
    {      do {
            String mid=cursor.getString(cursor.getColumnIndex(HealthConstants.COL_MEMID));
        String phno = cursor.getString(cursor.getColumnIndex(HealthConstants.COL_PHNO));


        SmsManager sms=SmsManager.getDefault();
        PendingIntent pi=PendingIntent.getActivity(this,1,null,PendingIntent.FLAG_ONE_SHOT);//making ine time pending intent(flagoneshot),requestcode is any positive no
        sms.sendTextMessage(phno,null,"your plan has been expired today",pi,null);
        android.support.v4.app.NotificationCompat.Builder nb=new android.support.v4.app.NotificationCompat.Builder(this);
        nb.setSmallIcon(R.mipmap.ic_launcher);

        nb.setContentText(mid+" plan has been expired today");
        nb.setAutoCancel(true);
        Notification notification=nb.build();//class that build notification
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1,notification);


    }while (cursor.moveToNext());
    }
    else
        Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();

}
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
