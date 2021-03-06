package com.djacoronel.lark.data.room

import android.arch.persistence.room.TypeConverter
import com.djacoronel.lark.data.model.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by djacoronel on 12/11/17.
 */
class Converters{
    @TypeConverter
    fun stringToList(value: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Long>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToSchedule(value: String): Schedule {
        return Gson().fromJson(value, Schedule::class.java)
    }

    @TypeConverter
    fun scheduleToString(schedule: Schedule): String{
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