package com.djacoronel.lark.categories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.repository.CategoryRepository


/**
 * Created by djacoronel on 12/11/17.
 */
class MainViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    internal var newCategoryEvent = SingleLiveEvent<Void>()
    internal var openCategoryEvent = SingleLiveEvent<Long>()
    internal var showCategoriesEvent = SingleLiveEvent<Void>()
    internal var newIdeaEvent = SingleLiveEvent<Void>()
    internal var showAllIdeasEvent = SingleLiveEvent<Void>()

    var categories: LiveData<List<Category>> = MutableLiveData<List<Category>>()
    var ideas: LiveData<List<Idea>> = MutableLiveData<List<Idea>>()

    fun loadCategories() {
        categories = categoryRepository.getCategories()
    }

    fun showCategories() {
        showCategoriesEvent.call()
    }

    fun addNewCategory() {
        newCategoryEvent.call()
    }

    fun openCategory() {
        openCategoryEvent.call()
    }

    fun loadIdeas() {
        //ideas = ideasRepository.getIdeas()
    }

    fun showAllIdeas() {
        showAllIdeasEvent.call()
    }

    fun addNewIdea() {
        newIdeaEvent.call()
    }
}
