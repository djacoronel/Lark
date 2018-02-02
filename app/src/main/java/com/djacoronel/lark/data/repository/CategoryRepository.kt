package com.djacoronel.lark.data.repository

import android.arch.lifecycle.LiveData
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.room.CategoryDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by djacoronel on 12/12/17.
 */
@Singleton
class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    fun getCategories(): List<Category>{
        return categoryDao.getCategories()
    }

    fun getLiveCategories(): LiveData<List<Category>> {
        return categoryDao.getLiveCategories()
    }

    fun getCategory(categoryId: Long): Category{
        return categoryDao.getCategory(categoryId)
    }

    fun getLiveCategory(categoryId: Long): LiveData<Category>{
        return categoryDao.getLiveCategory(categoryId)
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