package com.example.kratirastogi.healthclubmembershipapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadRec extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyService.class);

        i.putExtra("foo", "AlarmReceiver");

        context.startService(i);
    }
}
