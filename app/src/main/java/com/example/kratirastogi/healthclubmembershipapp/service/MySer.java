package com.example.kratirastogi.healthclubmembershipapp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MySer extends IntentService {
    public Context context=null;

    public MySer() {
        super("test-service");
    }
    public void onCreate() {

        super.onCreate();

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        context=this;
        try

        {

            Thread.sleep(5000);

        }

        catch (InterruptedException e)

        {

            e.printStackTrace();

        }



        String val = intent.getStringExtra("foo");

        // Do the task here


    }

    }

