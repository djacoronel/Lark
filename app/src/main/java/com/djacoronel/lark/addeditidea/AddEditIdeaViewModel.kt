package com.djacoronel.lark.addeditidea

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository

/**
 * Created by djacoronel on 1/14/18.
 */
class AddEditIdeaViewModel(
        private val categoryRepository: CategoryRepository,
        private val ideaRepository: IdeaRepository
) : ViewModel() {
    internal var ideaSavedEvent = SingleLiveEvent<Void>()
    private var isNewIdea = true

    var category: LiveData<Category> = MutableLiveData<Category>()
    var idea = Idea()

    fun loadCategory(categoryId: Long){
        category = categoryRepository.getLiveCategory(categoryId)
        idea.category = categoryId
    }

    fun loadIdea(ideaId: Long) {
        isNewIdea = false
        idea = ideaRepository.getIdea(ideaId)
    }

    fun saveIdea() {
        if (isNewIdea) {
            ideaRepository.insertIdea(idea)
        } else {
            ideaRepository.updateIdea(idea)
        }

        ideaSavedEvent.call()
    }
}