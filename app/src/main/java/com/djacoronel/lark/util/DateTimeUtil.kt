package com.djacoronel.lark.util

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by djacoronel on 12/31/17.
 */
object DateTimeUtil {
    fun millisToTimeString(time: Long): String {
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = time
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val ampm = calendar.get(Calendar.AM_PM)

        val minuteString = String.format("%02d", minute)
        val ampmString = if (ampm == 0) "AM" else "PM"

        return "$hour:$minuteString $ampmString"
    }

    fun millisToIntervalString(interval: Long): String {
        val hourInMillis = TimeUnit.HOURS.toMillis(1)

        return if (interval < hourInMillis){
            val minute = TimeUnit.MILLISECONDS.toMinutes(interval)
            "every $minute mins"
        } else {
            val timeUnit = if(interval == hourInMillis) "hr" else "hrs"
            val hour = TimeUnit.MILLISECONDS.toHours(interval)
            "every $hour $timeUnit"
        }
    }

    fun hourMinuteToMillis(hourOfDay: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        return calendar.timeInMillis
    }

    fun intervalToMillis(intervalString: String): Long {
        val p = Pattern.compile("-?\\d+")
        val m = p.matcher(intervalString)

        val intervalValue = if (m.find()) m.group().toLong() else -1

        return if (intervalString.contains("min"))
            TimeUnit.MINUTES.toMillis(intervalValue)
        else
            TimeUnit.HOURS.toMillis(intervalValue)
    }
}

