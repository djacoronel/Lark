package com.djacoronel.lark.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject


/**
 * Created by djacoronel on 1/18/18.
 */
class DeviceBootReceiver: BroadcastReceiver(){
    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var ideaRepository: IdeaRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            AndroidInjection.inject(this, context)

            val categories = categoryRepository.getCategories()

            for (i in 0..categories.lastIndex){
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                alarmIntent.putExtra("category", categories[i].label)
                alarmIntent.putExtra("categoryId", categories[i].id)
                alarmIntent.putExtra("requestCode", i)

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()

                val pendingIntent = PendingIntent.getBroadcast(context, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
                val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                        AlarmManager.INTERVAL_DAY, pendingIntent)
            }

        }
    }
}