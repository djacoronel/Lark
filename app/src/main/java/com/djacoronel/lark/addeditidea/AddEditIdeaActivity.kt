package com.djacoronel.lark.addeditidea

import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.djacoronel.lark.R
import CardsPagerTransformerShift
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

        val density = resources.displayMetrics.density
        val partialWidth = (16 * density).toInt() // 16dp
        val pageMargin = (3 * density).toInt() // 8dp

        val viewPagerPadding = partialWidth + pageMargin

        viewpager_idea.pageMargin = pageMargin
        viewpager_idea.setPadding(viewPagerPadding, 0, viewPagerPadding, 0)

        val screen = Point()
        windowManager.defaultDisplay.getSize(screen)
        val startOffset = viewPagerPadding.toFloat() / (screen.x - 2 * viewPagerPadding)

        val baseElevation = 10
        val raisingElevation = 10
        val smallerScale = .9f
        viewpager_idea.setPageTransformer(
                false,
                CardsPagerTransformerShift(baseElevation, raisingElevation, smallerScale, startOffset))
    }


    companion object {
        const val EXTRA_IDEA_ID = "IDEA_ID"
        const val REQUEST_CODE = 1
    }
}
