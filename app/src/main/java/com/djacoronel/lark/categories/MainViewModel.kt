package com.djacoronel.lark.categories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.repository.CategoryRepository


/**
 * Created by djacoronel on 12/11/17.
 */
class MainViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    internal var newCategoryEvent = SingleLiveEvent<Void>()
    internal var openCategoryEvent = SingleLiveEvent<Long>()

    var categories: LiveData<List<Category>> = MutableLiveData<List<Category>>()

    fun loadCategories() {
        categories = categoryRepository.getLiveCategories()
    }

    fun addNewCategory() {
        newCategoryEvent.call()
    }
}