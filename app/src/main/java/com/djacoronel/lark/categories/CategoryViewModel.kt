package com.djacoronel.lark.categories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.repository.CategoryRepository


/**
 * Created by djacoronel on 12/11/17.
 */
class CategoryViewModel : ViewModel() {
    lateinit var categoryRepository: CategoryRepository
    lateinit var categories: LiveData<List<Category>>

    fun init(categoryRepository: CategoryRepository){
        this.categoryRepository = categoryRepository
        categories = categoryRepository.getCategories()
    }
}
