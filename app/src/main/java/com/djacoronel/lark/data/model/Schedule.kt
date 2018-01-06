package com.djacoronel.lark.data.model

import com.djacoronel.lark.util.DateTimeUtil

/**
* Created by djacoronel on 12/11/17.
*/
class Schedule{
    var vibrateOnNotification: Boolean = false

    var useInterval: Boolean = false
    var interval: Long = 0
    var time: Long = 0

    var monday: Boolean = false
    var tuesday: Boolean = false
    var wednesday: Boolean = false
    var thursday: Boolean = false
    var friday: Boolean = false
    var saturday: Boolean = false
    var sunday: Boolean = false

    override fun toString(): String {
        val timeString = DateTimeUtil.millisToTimeString(time)
        val intervalString = DateTimeUtil.millisToIntervalString(interval)

        var string = if(useInterval) intervalString else timeString
        if(monday) string += ", Mon"
        if(tuesday) string += ", Tue"
        if(wednesday) string += ", Wed"
        if(thursday) string += ", Thu"
        if(friday) string += ", Fri"
        if(saturday) string += ", Sat"
        if(sunday) string += ", Sun"

        return string
    }
}