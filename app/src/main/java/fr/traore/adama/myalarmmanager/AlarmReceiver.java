package fr.traore.adama.myalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "onReceive - Intent Action: " + intent.getAction());

        context.startActivity(new Intent(context, MainActivity.class));

    }
}
