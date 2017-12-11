package com.djacoronel.lark

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by djacoronel on 12/11/17.
 */
@Entity
class Category{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var categoryLabel: String = "CategoryString"
    var color: Int = 0
    var schedule: Schedule = Schedule()
    var ideas: List<Long> = listOf()
}