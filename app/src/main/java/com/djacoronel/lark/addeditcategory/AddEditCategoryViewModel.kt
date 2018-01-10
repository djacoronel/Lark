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
class AddEditCategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    internal var categorySavedEvent = SingleLiveEvent<Void>()
    private val defaultColor = -15165471

    private var isNewCategory = true
    private var ideas: List<Long> = listOf()

    var id: Long = 0
    var color = ObservableField(defaultColor)
    var label: String = ""

    var useInterval = ObservableBoolean(false)
    var time: Long = 0
    var interval: Long = 0

    var schedule = Schedule()

    fun loadCategory(categoryId: Long) {
        isNewCategory = false

        val category = categoryRepository.getCategory(categoryId)
        id = category.id
        label = category.label
        schedule = category.schedule
        ideas = category.ideas

        time = category.schedule.time
        interval = category.schedule.interval

        color.set(category.color)
        useInterval.set(category.schedule.useInterval)
    }

    fun saveCategory() {
        schedule.useInterval = useInterval.get()
        schedule.interval = interval
        schedule.time = time

        val category = Category()
        category.color = color.get()
        category.label = label
        category.schedule = schedule
        category.ideas = ideas

        if (isNewCategory)
            categoryRepository.insertCategory(category)
        else {
            category.id = id
            categoryRepository.updateCategory(category)
        }

        categorySavedEvent.call()
    }
}