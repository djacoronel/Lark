package com.djacoronel.lark.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


/**
 * Created by djacoronel on 1/18/18.
 */
class DeviceBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            NotificationScheduler(context).setupNotificationAlarms()
        }
    }
}