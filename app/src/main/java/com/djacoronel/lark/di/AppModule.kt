package com.djacoronel.lark.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.djacoronel.lark.data.repository.CategoryRepository
import com.djacoronel.lark.data.room.AppDatabase
import com.djacoronel.lark.data.room.CategoryDao
import com.djacoronel.lark.data.room.IdeaDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by djacoronel on 12/12/17.
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "my-todo-db").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideIdeaDao(database: AppDatabase): IdeaDao = database.ideaDao()

    @Provides
    @Singleton
    fun provideCategoryRepo(categoryDao: CategoryDao): CategoryRepository = CategoryRepository(categoryDao)
}