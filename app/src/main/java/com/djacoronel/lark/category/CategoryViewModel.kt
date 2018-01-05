package com.djacoronel.lark.category

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository

/**
 * Created by djacoronel on 1/5/18.
 */
class CategoryViewModel(
        private val categoryRepository: CategoryRepository,
        private val ideaRepository: IdeaRepository
) : ViewModel() {
    internal var editCategoryEvent = SingleLiveEvent<Long>()
    internal var deleteCategoryEvernt = SingleLiveEvent<Long>()
    internal var newIdeaEvent = SingleLiveEvent<String>()

    var category = Category()
    var ideas: LiveData<List<Idea>> = MutableLiveData<List<Idea>>()

    fun loadData(categoryId: Long){
        category = categoryRepository.getCategory(categoryId)
        ideas = ideaRepository.getIdeas(category.label)
    }

    fun addNewIdea(){
        newIdeaEvent.value = "NEW IDEA!"
    }
}