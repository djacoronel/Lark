package com.djacoronel.lark.addeditidea

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.djacoronel.lark.R
import com.djacoronel.lark.data.model.Idea

import kotlinx.android.synthetic.main.activity_add_edit_idea.*

class AddEditIdeaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_idea)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val ideas = mutableListOf<Idea>()
        for (i in 1..5){
            val idea = Idea()
            idea.content = "idea $i"
            idea.source = "source $i"

            ideas.add(idea)
        }

        val adapter = ViewPagerAdapter()
        adapter.replaceData(ideas)
        viewpager_idea.adapter = adapter
    }


    companion object {
        const val EXTRA_IDEA_ID = "IDEA_ID"
        const val REQUEST_CODE = 1
    }
}
