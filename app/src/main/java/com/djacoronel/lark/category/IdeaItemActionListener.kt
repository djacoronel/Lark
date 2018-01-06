package com.djacoronel.lark.category

import android.view.View

/**
 * Created by djacoronel on 1/6/18.
 */
interface IdeaItemActionListener{
    fun onIdeaClicked(ideaId: Long)
    fun onIdeaLongClicked(view: View): Boolean
}