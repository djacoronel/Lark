package com.djacoronel.lark.util

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.djacoronel.lark.App
import com.djacoronel.lark.R
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.data.repository.IdeaRepository
import dagger.android.AndroidInjection
import java.util.Random
import javax.inject.Inject

/**
 * Created by djacoronel on 1/18/18.
 */
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ideaRepository: IdeaRepository

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        val requestCode = intent.getIntExtra("requestCode", -1)

        val alarmIntent = Intent(context, CategoryActivity::class.java)
        val pIntent = PendingIntent.getActivity(context, requestCode, alarmIntent, PendingIntent.FLAG_ONE_SHOT)

        val category = intent.getStringExtra("category")
        val categoryId = intent.getLongExtra("categoryId", 0)

        Log.i(category, categoryId.toString())

        val ideas = ideaRepository.getIdeas(categoryId)
        if (ideas.isNotEmpty()) {

            val idea = if (ideas.size > 1) ideas[randomNumber(0, ideas.lastIndex)] else ideas[0]

            val n = Notification.Builder(context)
                    .setContentTitle(category)
                    .setContentText(idea.content + "\n - " + idea.source)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_notifications_active_black_24dp, "Open card", pIntent)
                    .addAction(R.drawable.ic_notifications_active_black_24dp, "Next card", pIntent)
                    .setStyle(Notification.BigTextStyle())
                    .build()

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            n.flags = n.flags or Notification.FLAG_AUTO_CANCEL
            notificationManager.notify(requestCode, n)
        }
    }

    fun randomNumber(from: Int, to: Int): Int {
        Log.i(from.toString(), to.toString())
        return Random().nextInt(to - from) + from
    }
}