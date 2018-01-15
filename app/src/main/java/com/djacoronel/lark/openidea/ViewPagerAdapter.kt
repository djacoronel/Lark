package com.djacoronel.lark.openidea

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djacoronel.lark.R
import com.djacoronel.lark.data.model.Idea
import kotlinx.android.synthetic.main.layout_view_idea.view.*

/**
 * Created by djacoronel on 1/10/18.
 */
class ViewPagerAdapter : PagerAdapter() {
    private var ideas = listOf<Idea>()

    fun getIdeaPosition(ideaId: Long): Int {
        val idea = ideas.find { it.id == ideaId }
        return ideas.indexOf(idea)
    }

    fun getIdeaId(position: Int): Long {
        return ideas[position].id
    }

    fun replaceData(ideas: List<Idea>) {
        setList(ideas)
    }

    private fun setList(ideas: List<Idea>) {
        this.ideas = ideas
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val idea = ideas[position]
        val view = LayoutInflater.from(container.context).inflate(R.layout.layout_view_idea, null)
        view.textView_content.text = idea.content
        val source = "- ${idea.source}"
        view.textView_source.text = source

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return ideas.size
    }
}