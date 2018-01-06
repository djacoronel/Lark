package com.djacoronel.lark.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.databinding.ActivityCategoryBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_category.*
import org.jetbrains.anko.toast
import javax.inject.Inject


class CategoryActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)
        initViewModel()
        initBinding()
        setupFab()
        setupAppBarContentFade()
    }

    private fun initViewModel() {
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)

        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(CategoryViewModel::class.java)
        viewModel.loadData(categoryId)

        viewModel.newIdeaEvent.observe(this, Observer { category ->
            category?.let {
                this.addNewIdea(it)
            }
        })
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        binding.viewModel = viewModel
        binding.category = viewModel.category
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            viewModel.addNewIdea()
        }
    }

    private fun setupAppBarContentFade() {
        binding.appBar.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            binding.textViewSchedule.alpha = 1.0f - Math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        })
    }

    private fun addNewIdea(category: String) {
        toast(category).show()
    }

    companion object {
        val EXTRA_CATEGORY_ID = "CATEGORY_ID"
    }
}
