package com.djacoronel.lark.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.djacoronel.lark.data.model.Category



/**
 * Created by djacoronel on 12/11/17.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    fun getCategory(categoryId: Long): Category

    @Query("SELECT * FROM category WHERE id = :categoryId")
    fun getLiveCategory(categoryId: Long): LiveData<Category>

    @Insert(onConflict = REPLACE)
    fun insertCategory(category: Category)

    @Update(onConflict = REPLACE)
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)
}