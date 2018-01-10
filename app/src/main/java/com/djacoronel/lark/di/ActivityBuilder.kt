package com.djacoronel.lark.di

import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.categories.MainActivity
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by djacoronel on 12/12/17.
 */
@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindAddEditActivity(): AddEditCategoryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindCategoryActivity(): CategoryActivity
}