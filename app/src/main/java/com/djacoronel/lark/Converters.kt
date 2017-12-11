package com.djacoronel.lark

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by djacoronel on 12/11/17.
 */
class Converters{
    @TypeConverter
    fun StringToList(value: String): ArrayList<Long> {
        val listType = object : TypeToken<ArrayList<Long>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun ListToString(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun StringToSchedule(value: String): Schedule{
        return Gson().fromJson(value, Schedule::class.java)
    }

    @TypeConverter
    fun ScheduleToString(schedule: Schedule): String{
        return Gson().toJson(schedule)
    }

    @TypeConverter
    fun timestampToDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long{
        return date.time
    }
}