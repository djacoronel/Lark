package com.djacoronel.lark.data.repository

import android.arch.lifecycle.LiveData
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.data.room.IdeaDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by djacoronel on 1/5/18.
 */
@Singleton
class IdeaRepository @Inject constructor(private val ideaDao: IdeaDao) {

    fun getIdeas(): LiveData<List<Idea>> {
        return ideaDao.getIdeas()
    }

    fun getIdeas(ideaCategory: Long): LiveData<List<Idea>>{
        return ideaDao.getIdeas(ideaCategory)
    }

    fun getIdea(ideaId: Long): Idea{
        return ideaDao.getIdea(ideaId)
    }

    fun insertIdea(idea: Idea){
        ideaDao.insertIdea(idea)
    }

    fun updateIdea(idea: Idea){
        ideaDao.updateIdea(idea)
    }

    fun deleteIdea(idea: Idea){
        ideaDao.deleteIdea(idea)
    }
}