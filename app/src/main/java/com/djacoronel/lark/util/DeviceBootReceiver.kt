package com.djacoronel.lark.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import java.util.*


/**
 * Created by djacoronel on 1/18/18.
 */
class DeviceBootReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, 7)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 1)

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }
}