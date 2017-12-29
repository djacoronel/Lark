package com.djacoronel.lark.data.model

/**
* Created by djacoronel on 12/11/17.
*/
class Schedule{
    var vibrateOnNotification: Boolean = false

    var useInterval: Boolean = false
    var interval: Int = 0
    var time: Long = 0

    var monday: Boolean = false
    var tuesday: Boolean = false
    var wednesday: Boolean = false
    var thursday: Boolean = false
    var friday: Boolean = false
    var saturday: Boolean = false
    var sunday: Boolean = false

    override fun toString(): String {
        return "Category Schedule"
    }
}