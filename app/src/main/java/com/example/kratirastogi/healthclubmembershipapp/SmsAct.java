package com.example.kratirastogi.healthclubmembershipapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SmsAct extends AppCompatActivity {
Intent obj;
    EditText txtphno;
    String number;
    String msg;
    EditText txtmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        obj=getIntent();
        number=obj.getStringExtra("key");
        txtphno=findViewById(R.id.txtphno);
        txtphno.setText(number);
        txtmsg=findViewById(R.id.txtmsg);
    }

    public void send(View view) {
        msg=txtmsg.getText().toString();
        SmsManager sms=SmsManager.getDefault();
        // Intent obj=new Intent(this,SmsAct.class);
        PendingIntent pi=PendingIntent.getActivity(this,1,obj,PendingIntent.FLAG_ONE_SHOT);
        sms.sendTextMessage(number,"",msg,pi,null);
        Toast.makeText(this, "messagesent", Toast.LENGTH_SHORT).show();
    }
}
