package com.example.kratirastogi.healthclubmembershipapp;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kratirastogi.healthclubmembershipapp.service.BroadRec;
import com.example.kratirastogi.healthclubmembershipapp.service.MyService;

import java.util.Date;
import java.util.Locale;

public class Welcome extends AppCompatActivity implements TextToSpeech.OnInitListener{
    public static final int REQUEST_CODE = (int) new Date().getTime();
TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        scheduleAlarm();
       // start();
        textToSpeech=new TextToSpeech(this,this);
          Handler handler=new Handler();
          handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                  SharedPreferences sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
                  String id=sharedPreferences.getString("id","");
                  if(id.equals(""))

                  {
                      Intent obj=new Intent(Welcome.this,Registration.class);
                      startActivity(obj);
                      Welcome.this.finish();
                  }
                  else {
                      Intent ob=new Intent(Welcome.this,Login.class);
                      startActivity(ob);
                      Welcome.this.finish();
              }
          }
    },8000);
    }

    @Override
    public void onInit(int status) {
        if (status==TextToSpeech.SUCCESS)
        {int res=textToSpeech.setLanguage(Locale.US);
            if(res==TextToSpeech.LANG_NOT_SUPPORTED||res==TextToSpeech.LANG_MISSING_DATA)
            {
                Toast.makeText(this,"not supported",Toast.LENGTH_LONG).show();
            }
            else
            {
                textToSpeech.speak("welcome to healthclub membership app",TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }
   /* Intent i=null;
   public  void start()
   {
        if(!isMyServiceRunning(MyService.class)) {
            i = new Intent(this, MyService.class);
            startService(i);
        }
   }
   private boolean isMyServiceRunning(Class serviceClass){
       ActivityManager manager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
       for(ActivityManager.RunningServiceInfo runningServiceInfo: manager.getRunningServices(Integer.MAX_VALUE))
           if(serviceClass.getName().equals(runningServiceInfo.service.getClassName()))
               return true;
       return false;
   }*/
   public void scheduleAlarm()
   {

       Intent intent = new Intent(getApplicationContext(), BroadRec.class);
       final PendingIntent pIntent = PendingIntent.getBroadcast(
               this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

       long firstMillis = System.currentTimeMillis(); // alarm is set right away
       AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

       alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, (long) (1000 * 60), pIntent);



   }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }
    }
