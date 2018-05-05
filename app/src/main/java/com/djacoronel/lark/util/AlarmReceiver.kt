package com.djacoronel.lark.util

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.djacoronel.lark.R
import com.djacoronel.lark.data.repository.IdeaRepository
import com.djacoronel.lark.openidea.OpenIdeaActivity
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
    lateinit var intent: Intent
    lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        this.intent = intent
        this.context = context

        createNotification()
    }

    private fun createNotification() {
        val requestCode = intent.getIntExtra("requestCode", -1)
        val category = intent.getStringExtra("category")
        val categoryId = intent.getLongExtra("categoryId", 0)

        val ideas = ideaRepository.getIdeas(categoryId)
        if (ideas.isNotEmpty()) {
            val idea = if (ideas.size > 1) ideas[randomNumber(ideas.lastIndex)] else ideas[0]

            val alarmIntent = Intent(context, OpenIdeaActivity::class.java)
            alarmIntent.putExtra(OpenIdeaActivity.EXTRA_IDEA_ID, idea.id)
            alarmIntent.putExtra(OpenIdeaActivity.EXTRA_CATEGORY_ID,categoryId)
            val pIntent = PendingIntent.getActivity(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val CHANNEL_ID = "channel_01"
            val name = "Lark Notifications"

            val notification = NotificationCompat.Builder(context,CHANNEL_ID)
                    .setStyle(NotificationCompat.BigTextStyle())
                    .setContentTitle(category)
                    .setContentText(idea.content + "\n - " + idea.source)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .addAction(R.drawable.ic_notifications_active_black_24dp, "Open card", pIntent)
                    .build()

            notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(requestCode, notification)
        }
    }

    private fun randomNumber(to: Int): Int {
        return Random().nextInt(to)
    }
}