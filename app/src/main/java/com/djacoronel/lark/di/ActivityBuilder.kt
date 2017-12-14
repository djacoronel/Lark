package com.djacoronel.lark.di

import com.djacoronel.lark.categories.CategoryActivity
import com.djacoronel.lark.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by djacoronel on 12/12/17.
 */
@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    abstract fun bindMainActivity(): CategoryActivity
}