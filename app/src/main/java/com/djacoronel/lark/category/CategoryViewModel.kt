package com.djacoronel.lark.category

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.djacoronel.lark.SingleLiveEvent
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository

/**[]
 * Created by djacoronel on 1/5/18.
 */
class CategoryViewModel(
        private val categoryRepository: CategoryRepository,
        private val ideaRepository: IdeaRepository
) : ViewModel() {
    internal var editCategoryEvent = SingleLiveEvent<Long>()
    internal var deleteCategoryEvent = SingleLiveEvent<Long>()
    internal var newIdeaEvent = SingleLiveEvent<Void>()
    internal var openIdeaEvent = SingleLiveEvent<Long>()
    internal var deleteIdeaEvent = SingleLiveEvent<Long>()
    internal var editIdeaEvent = SingleLiveEvent<Idea>()

    var category: LiveData<Category> = MutableLiveData<Category>()
    var ideas: LiveData<List<Idea>> = MutableLiveData<List<Idea>>()

    fun loadData(categoryId: Long){
        category = categoryRepository.getLiveCategory(categoryId)
        ideas = ideaRepository.getIdeas(categoryId)
    }

    fun deleteIdea(ideaId: Long){
        val idea = ideaRepository.getIdea(ideaId)
        ideaRepository.deleteIdea(idea)
    }

    fun deleteCategory(categoryId: Long){
        val category = categoryRepository.getCategory(categoryId)
        categoryRepository.deleteCategory(category)
    }
}