package com.djacoronel.lark.util

import java.text.SimpleDateFormat
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
        return SimpleDateFormat("h:mm a", Locale.getDefault()).format(calendar.time)
    }

    fun millisToIntervalString(interval: Long): String {
        val hourInMillis = TimeUnit.HOURS.toMillis(1)

        return if (interval < hourInMillis){
            val minute = TimeUnit.MILLISECONDS.toMinutes(interval)
            "every $minute minutes"
        } else {
            val timeUnit = if(interval == hourInMillis) "hour" else "hours"
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

    fun getCurrentTime(): Long =  Calendar.getInstance().time.time
}