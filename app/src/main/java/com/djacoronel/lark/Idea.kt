package com.djacoronel.lark

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by djacoronel on 12/11/17.
 */
@Entity
class Idea {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
    var content: String = "IdeaContents"
    var source: String = "IdeaSource"
    var category: String = "IdeaCategory"
    var dateCreated: Date = Date()
}