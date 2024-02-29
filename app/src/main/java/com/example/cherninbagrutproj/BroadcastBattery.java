package com.example.cherninbagrutproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BroadcastBattery extends BroadcastReceiver {
    //private boolean msgFlag;
    public BroadcastBattery() {
        //msgFlag = false;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        if (batteryLevel < 20)
            Toast.makeText(context.getApplicationContext(),"low battery",Toast.LENGTH_LONG).show();
    }
}

