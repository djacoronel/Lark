package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Schedule
import com.djacoronel.lark.data.repository.CategoryRepository

/**
 * Created by djacoronel on 12/13/17.
 */
class AddEditViewModel(val categoryRepository: CategoryRepository) : ViewModel() {
    internal var categoryUpdatedEvent = SingleLiveEvent<Void>()
    internal var categoryAddedEvent = SingleLiveEvent<Void>()

    var id: Long = 0
    var color = ObservableField<String>("#FFFFFF")
    var label: String = ""

    var useInterval = ObservableBoolean(false)
    var time: Long = 0
    var interval: Long = 0

    var schedule = Schedule()
    var ideas: List<Long> = listOf()

    fun clearColor(){
        color.set("#FFFFFF")
    }

    fun loadCategory(categoryId: Long) {
        val category = categoryRepository.getCategory(categoryId)
        id = category.id
        label = category.label
        schedule = category.schedule
        ideas = category.ideas

        color.set(category.color)
        useInterval.set(category.schedule.useInterval)
    }

    fun addCategory() {
        schedule.useInterval = useInterval.get()
        if (useInterval.get()) schedule.interval = interval
        else schedule.time = time

        val category = Category()
        category.color = color.get()
        category.label = label
        category.schedule = schedule
        category.ideas = ideas

        categoryRepository.insertCategory(category)
        categoryAddedEvent.call()
    }

    fun updateCategory(category: Category) {
        categoryRepository.updateCategory(category)
        categoryUpdatedEvent.call()
    }
}