package com.example.bar.firmss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);

        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {

            if (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) == 100) {

                Toast.makeText(context, String.valueOf("BATARYA FULL"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
