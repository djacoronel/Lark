package com.djacoronel.lark.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.djacoronel.lark.R
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.data.repository.IdeaRepository
import dagger.android.AndroidInjection
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

/**
 * Created by djacoronel on 1/18/18.
 */
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ideaRepository: IdeaRepository

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        context.toast("alarm")

        val requestCode = intent.getIntExtra("requestCode", -1)
        val category = intent.getStringExtra("category")
        val categoryId = intent.getLongExtra("categoryId", 0)

        val alarmIntent = Intent(context, CategoryActivity::class.java)
        val pIntent = PendingIntent.getActivity(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val ideas = ideaRepository.getIdeas(categoryId)
        if (ideas.isNotEmpty()) {
            val idea = if (ideas.size > 1) ideas[randomNumber(ideas.lastIndex)] else ideas[0]

            val notification = Notification.Builder(context)
                    .setStyle(Notification.BigTextStyle())
                    .setContentTitle(category)
                    .setContentText(idea.content + "\n - " + idea.source)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .addAction(R.drawable.ic_notifications_active_black_24dp, "Open card", pIntent)
                    .addAction(R.drawable.ic_notifications_active_black_24dp, "Next card", pIntent)
                    .build()

            notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(requestCode, notification)
        }
    }

    private fun randomNumber(to: Int): Int {
        return Random().nextInt(to)
    }
}