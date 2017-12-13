package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.repository.CategoryRepository

/**
 * Created by djacoronel on 12/13/17.
 */
class AddEditViewModel(val categoryRepository: CategoryRepository): ViewModel(){
    internal var categoryUpdatedEvent = SingleLiveEvent<Void>()
    internal var categoryAddedEvent = SingleLiveEvent<Void>()
    var category: LiveData<Category> = MutableLiveData<Category>()

    fun loadCategory(categoryId: Long){
        category = categoryRepository.getCategory(categoryId)
    }

    fun addCategory(category: Category){
        categoryRepository.insertCategory(category)
        categoryAddedEvent.call()
    }

    fun updateCategory(category: Category){
        categoryRepository.updateCategory(category)
        categoryUpdatedEvent.call()
    }
}