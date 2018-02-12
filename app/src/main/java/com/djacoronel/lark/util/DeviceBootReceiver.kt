package com.djacoronel.lark.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast


/**
 * Created by djacoronel on 1/18/18.
 */
class DeviceBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val requestCode = 0
            val alarmIntent = Intent(context, DailyScheduleReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            manager.set(AlarmManager.RTC_WAKEUP, DateTimeUtil.getCurrentTime(), pendingIntent)
            manager.setRepeating(AlarmManager.RTC_WAKEUP, DateTimeUtil.hourMinuteToMillis(0, 0), AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }
}