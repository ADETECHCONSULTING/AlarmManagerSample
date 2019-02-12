package fr.traore.adama.myalarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {
    private static final String packageName = "fr.traore.adama.myalarmmanager.BootReceiver";
    private static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(BOOT_ACTION)){
            Log.d("BootReceiver", "BOOT ACTION");
            setAlarmAtSpecificTime(context, 5, 0, 0, false);
        }

    }


    public static void setAlarmAtSpecificTime(Context context, int hour, int minute, int sec, boolean isFromActivity){

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0, isFromActivity ? new Intent(context, MainActivity.class) : new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);

        Log.d("BootReceiver", "Already an alarm ? "+ alarmUp);

        if(alarmUp){
            return;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if(alarmManager == null) return;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, sec);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public static void enableBroadCastReceiver(Context context, boolean enable){
        Log.d("BootReceiver", "Enable BroadcastReceiver ? "+ enable);

        //Activer/Desactiver le broadcast receiver
        ComponentName componentName = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
                enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
