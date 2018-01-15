package com.djacoronel.lark.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by djacoronel on 12/11/17.
 */
@Entity
class Idea {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var content: String = ""
    var source: String = ""
    var category: Long = 0
    var dateCreated: Date = Date()
}