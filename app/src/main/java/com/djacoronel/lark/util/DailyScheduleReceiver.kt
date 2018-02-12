package com.djacoronel.lark.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Schedule
import com.djacoronel.lark.data.repository.CategoryRepository
import dagger.android.AndroidInjection
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

/**
 * Created by djacoronel on 2/12/18.
 */
class DailyScheduleReceiver : BroadcastReceiver() {

    @Inject
    lateinit var categoryRepository: CategoryRepository
    lateinit var context: Context
    lateinit var intent: Intent

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        this.context = context
        this.intent = intent

        context.toast("daily")

        val categories = categoryRepository.getCategories()
        scheduleNotifications(categories)
    }

    private fun scheduleNotifications(categories: List<Category>) {
        for (i in 0..categories.lastIndex) {
            val category = categories[i]
            val schedule = category.schedule

            if (isScheduledDayToday(schedule)) {
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                alarmIntent.putExtra("category", category.label)
                alarmIntent.putExtra("categoryId", category.id)
                alarmIntent.putExtra("requestCode", i)

                val pendingIntent = PendingIntent.getBroadcast(context, i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                if (schedule.useInterval) {
                    val triggerTime = DateTimeUtil.getCurrentTime() + schedule.interval
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, schedule.interval, pendingIntent)
                } else {
                    if (schedule.time > DateTimeUtil.getCurrentTime())
                        manager.set(AlarmManager.RTC_WAKEUP, schedule.time, pendingIntent)
                }
            }
        }
    }

    private fun isScheduledDayToday(schedule: Schedule): Boolean {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        return when (day) {
            Calendar.SUNDAY -> schedule.sunday
            Calendar.MONDAY -> schedule.monday
            Calendar.TUESDAY -> schedule.tuesday
            Calendar.WEDNESDAY -> schedule.wednesday
            Calendar.THURSDAY -> schedule.thursday
            Calendar.FRIDAY -> schedule.friday
            Calendar.SATURDAY -> schedule.saturday
            else -> false
        }
    }
}