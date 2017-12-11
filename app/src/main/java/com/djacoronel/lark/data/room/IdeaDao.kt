package com.djacoronel.lark.data.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.djacoronel.lark.data.model.Idea

/**
 * Created by djacoronel on 12/11/17.
 */
@Dao
interface IdeaDao{
    @Query("SELECT * FROM idea")
    fun getIdeas(): List<Idea>

    @Insert(onConflict = REPLACE)
    fun insertIdea(idea: Idea)

    @Update(onConflict = REPLACE)
    fun updateIdea(idea: Idea)

    @Delete
    fun deleteIdea(idea: Idea)
}