package com.djacoronel.lark.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

/**
 * Created by djacoronel on 2/20/18.
 */
class NotificationScheduler(val context: Context){
    fun setupNotificationAlarms() {
        val requestCode = 0
        val currentAlarmIntent = Intent(context, DailyScheduleReceiver::class.java)
        val scheduledAlarmIntent = Intent(context, DailyScheduleReceiver::class.java)

        val currentPendingIntent = PendingIntent.getBroadcast(context, requestCode, currentAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val scheduledPendingIntent = PendingIntent.getBroadcast(context, requestCode+1, scheduledAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.set(AlarmManager.RTC_WAKEUP, DateTimeUtil.getCurrentTime(), currentPendingIntent)
        manager.setRepeating(AlarmManager.RTC_WAKEUP, DateTimeUtil.hourMinuteToMillis(0,0), AlarmManager.INTERVAL_DAY, scheduledPendingIntent)
    }
}