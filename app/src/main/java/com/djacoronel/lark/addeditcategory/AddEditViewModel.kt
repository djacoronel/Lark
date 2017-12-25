package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Schedule
import com.djacoronel.lark.data.repository.CategoryRepository

/**
 * Created by djacoronel on 12/13/17.
 */
class AddEditViewModel(val categoryRepository: CategoryRepository): ViewModel(){
    internal var categoryUpdatedEvent = SingleLiveEvent<Void>()
    internal var categoryAddedEvent = SingleLiveEvent<Void>()
    
    var id: Long = 0
    var color: Int = 0
    var label: String = ""
    var schedule: Schedule = Schedule()
    var ideas: List<Long> = listOf()

    fun loadCategory(categoryId: Long){
        val category = categoryRepository.getCategory(categoryId)
        id = category.id
        color = category.color
        label = category.label
        schedule = category.schedule
        ideas = category.ideas
    }

    fun addCategory(){
        val category = Category()
        category.color = color
        category.label = label
        category.schedule = schedule
        category.ideas = ideas
        
        categoryRepository.insertCategory(category)
        categoryAddedEvent.call()
    }

    fun updateCategory(category: Category){
        categoryRepository.updateCategory(category)
        categoryUpdatedEvent.call()
    }
}