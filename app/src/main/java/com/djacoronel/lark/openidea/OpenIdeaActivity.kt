package com.djacoronel.lark.openidea

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditidea.AddEditIdeaActivity
import com.djacoronel.lark.databinding.ActivityOpenIdeaBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_open_idea.*
import javax.inject.Inject


class OpenIdeaActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: OpenIdeaViewModel
    private lateinit var binding: ActivityOpenIdeaBinding
    private var updatedPage = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)

        initBinding()
        initViewModel()
        setupIdeasViewPager()
        setupFab()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_idea)
    }

    private fun initViewModel() {
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(OpenIdeaViewModel::class.java)

        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        viewModel.loadData(categoryId)

        viewModel.category.observe(this, Observer { category ->
            category?.let {
                binding.category = category
            }
        })
        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                viewPagerAdapter.replaceData(it)
                setSelectedIdea()

                if (updatedPage != -1) {
                    binding.viewpagerIdea.adapter = viewPagerAdapter
                    binding.viewpagerIdea.currentItem = updatedPage
                    updatedPage = -1
                }
            }
        })
    }

    private fun setSelectedIdea() {
        val selectedIdea = intent.getLongExtra(EXTRA_IDEA_ID, 0)
        val selectedIdeaPosition = viewPagerAdapter.getIdeaPosition(selectedIdea)
        binding.viewpagerIdea.currentItem = selectedIdeaPosition
    }

    private fun setupIdeasViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        binding.viewpagerIdea.adapter = viewPagerAdapter
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
            val currentPosition = binding.viewpagerIdea.currentItem
            val currentIdea = viewPagerAdapter.getIdeaId(currentPosition)

            val intent = Intent(this, AddEditIdeaActivity::class.java)
            intent.putExtra(AddEditIdeaActivity.EXTRA_IDEA_ID, currentIdea)
            intent.putExtra(AddEditIdeaActivity.EXTRA_CATEGORY_ID, categoryId)
            updatedPage = currentPosition

            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "CATEGORY_ID"
        const val EXTRA_IDEA_ID = "IDEA_ID"
    }
}
