package com.djacoronel.lark.data.repository

import android.arch.lifecycle.LiveData
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.room.CategoryDao

/**
 * Created by djacoronel on 12/12/17.
 */

class CategoryRepository(private val categoryDao: CategoryDao) {

    fun getCategories(): LiveData<List<Category>> {
        return categoryDao.getCategories()
    }

    fun insertCategory(category: Category){
        categoryDao.insertCategory(category)
    }

    fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

    fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category)
    }
}