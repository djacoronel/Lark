package com.djacoronel.lark

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.djacoronel.lark.addeditcategory.AddEditViewModel
import com.djacoronel.lark.categories.CategoryViewModel
import com.djacoronel.lark.data.repository.CategoryRepository
import javax.inject.Inject

/**
 * Created by djacoronel on 12/12/17.
 */
class ViewModelFactory @Inject constructor(val categoryRepository: CategoryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(categoryRepository) as T
        } else if(modelClass.isAssignableFrom(AddEditViewModel::class.java)){
            return AddEditViewModel(categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class name")
    }
}