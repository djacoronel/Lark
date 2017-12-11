package com.djacoronel.lark

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

/**
 * Created by djacoronel on 12/11/17.
 */
@Database(entities = arrayOf(Category::class, Idea::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun ideaDao(): IdeaDao
}