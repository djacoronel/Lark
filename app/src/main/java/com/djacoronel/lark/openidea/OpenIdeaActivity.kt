package com.djacoronel.lark.openidea

import CardPagerTransformerShift
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.databinding.ActivityOpenIdeaBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_open_idea.*
import javax.inject.Inject


class OpenIdeaActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: OpenIdeaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_open_idea)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
        }

        initViewModel()
        initBinding()

        setupIdeasViewPager()
        setSelectedIdea()
    }

    private fun initViewModel() {
        var isFirstDataUpdate = true
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)

        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(OpenIdeaViewModel::class.java)
        viewModel.loadData(categoryId)

        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                viewPagerAdapter.replaceData(it)

                if (isFirstDataUpdate) {
                    setSelectedIdea()
                    isFirstDataUpdate = false
                }
            }
        })
    }

    private fun initBinding(){
        val binding:ActivityOpenIdeaBinding = DataBindingUtil.setContentView(this, R.layout.activity_open_idea)
        viewModel.category.observe(this, Observer { category ->
            category?.let {
                binding.category = it
            }
        })
    }

    private fun setupIdeasViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        viewpager_idea.adapter = viewPagerAdapter

        val density = resources.displayMetrics.density
        val partialWidth = (8 * density).toInt() // 16dp
        val pageMargin = (8 * density).toInt() // 8dp

        val viewPagerPadding = partialWidth + pageMargin

        viewpager_idea.pageMargin = pageMargin
        viewpager_idea.setPadding(viewPagerPadding, 0, viewPagerPadding, 0)

        val screen = Point()
        windowManager.defaultDisplay.getSize(screen)
        val startOffset = viewPagerPadding.toFloat() / (screen.x - 2 * viewPagerPadding)

        val baseElevation = 5
        val raisingElevation = 5
        val smallerScale = .8f
        viewpager_idea.setPageTransformer(
                false,
                CardPagerTransformerShift(baseElevation, raisingElevation, smallerScale, startOffset))
    }

    private fun setSelectedIdea() {
        val selectedIdea = intent.getLongExtra(EXTRA_IDEA_ID, 0)
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
