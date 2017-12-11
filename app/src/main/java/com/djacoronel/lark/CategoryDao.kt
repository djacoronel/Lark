package com.djacoronel.lark

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by djacoronel on 12/11/17.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategories(): List<Category>

    @Insert(onConflict = REPLACE)
    fun insertCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)
}