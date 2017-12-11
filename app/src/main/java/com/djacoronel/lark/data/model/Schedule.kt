package com.djacoronel.lark.data.model

import java.util.*

/**
* Created by djacoronel on 12/11/17.
*/
class Schedule{
    var useInterval: Boolean = true
    var vibrateOnNotification: Boolean = false
    var interval: Int = 0
    var date: Date = Date()
    var days: Days = Days()
}