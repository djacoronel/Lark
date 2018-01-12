package com.djacoronel.lark.addeditidea

import CardsPagerTransformerShift
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_add_edit_idea.*
import javax.inject.Inject


class AddEditIdeaActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_add_edit_idea)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        initViewModel()

        setupIdeasViewPager()
        setSelectedIdea()
    }

    private fun initViewModel(){
        var isFirstDataUpdate = true
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID,0)

        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        val viewModel = viewModelProvider.get(AddEditIdeaViewModel::class.java)
        viewModel.loadData(categoryId)

        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                viewPagerAdapter.replaceData(it)

                if(isFirstDataUpdate){
                    setSelectedIdea()
                    isFirstDataUpdate = false
                }
            }
        })
    }

    private fun setupIdeasViewPager(){
        viewPagerAdapter = ViewPagerAdapter()
        viewpager_idea.adapter = viewPagerAdapter

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

    private fun setSelectedIdea(){
        val selectedIdea = intent.getLongExtra(EXTRA_IDEA_ID,0)
        val selectedIdeaPosition = viewPagerAdapter.getIdeaPosition(selectedIdea)
        Log.i("SELECTED", selectedIdeaPosition.toString())
        viewpager_idea.currentItem = selectedIdeaPosition
    }


    companion object {
        const val EXTRA_CATEGORY_ID = "CATEGORY_ID"
        const val EXTRA_IDEA_ID = "IDEA_ID"
        const val REQUEST_CODE = 1
    }
}
