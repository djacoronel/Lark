package com.djacoronel.lark.openidea

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository

/**
 * Created by djacoronel on 1/10/18.
 */
class OpenIdeaViewModel(
        private val categoryRepository: CategoryRepository,
        private val ideaRepository: IdeaRepository
) : ViewModel() {
    internal var addIdeaEvent = SingleLiveEvent<Void>()
    internal var editIdeaEvent = SingleLiveEvent<Void>()
    internal var updateIdeaEvent = SingleLiveEvent<Void>()

    var category: LiveData<Category> = MutableLiveData<Category>()
    var ideas: LiveData<List<Idea>> = MutableLiveData<List<Idea>>()

    fun loadData(categoryId: Long) {
        category = categoryRepository.getLiveCategory(categoryId)
        ideas = ideaRepository.getIdeas(categoryId)
    }
}