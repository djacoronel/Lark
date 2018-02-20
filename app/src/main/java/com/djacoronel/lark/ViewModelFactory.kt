package com.djacoronel.lark

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.djacoronel.lark.addeditcategory.AddEditCategoryViewModel
import com.djacoronel.lark.addeditidea.AddEditIdeaViewModel
import com.djacoronel.lark.openidea.OpenIdeaViewModel
import com.djacoronel.lark.categories.MainViewModel
import com.djacoronel.lark.category.CategoryViewModel
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.repository.IdeaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
/**
 * Created by djacoronel on 12/12/17.
 */
@Singleton
class ViewModelFactory @Inject constructor(
        private val categoryRepository: CategoryRepository,
        private val ideaRepository: IdeaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(categoryRepository) as T
            modelClass.isAssignableFrom(AddEditCategoryViewModel::class.java) ->
                AddEditCategoryViewModel(categoryRepository) as T
            modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                CategoryViewModel(categoryRepository, ideaRepository) as T
            modelClass.isAssignableFrom(OpenIdeaViewModel::class.java) ->
                OpenIdeaViewModel(categoryRepository, ideaRepository) as T
            modelClass.isAssignableFrom(AddEditIdeaViewModel::class.java) ->
                AddEditIdeaViewModel(categoryRepository, ideaRepository) as T
            else -> throw IllegalArgumentException("Unknown View Model class name")
        }
    }
}