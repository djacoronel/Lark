package com.djacoronel.lark.addeditidea

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
    private var isNewIdea = true

    var category: LiveData<Category> = MutableLiveData<Category>()
    var idea = Idea()

    fun loadIdea(ideaId: Long) {
        isNewIdea = false
        idea = ideaRepository.getIdea(ideaId)
        category = categoryRepository.getLiveCategory(idea.category)
    }

    fun saveIdea() {
        if (isNewIdea) {
            ideaRepository.insertIdea(idea)
        } else {
            ideaRepository.updateIdea(idea)
        }
    }
}