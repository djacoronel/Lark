package com.djacoronel.lark

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by djacoronel on 12/11/17.
 */
@Dao
interface IdeaDao{
    @Query("SELECT * FROM idea")
    fun getIdeas(): List<Idea>

    @Insert(onConflict = REPLACE)
    fun insertIdea(idea: Idea)

    @Update
    fun updateIdea(idea: Idea)

    @Delete
    fun deleteIdea(idea: Idea)
}