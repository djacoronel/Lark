package com.djacoronel.lark.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.databinding.ActivityCategoryBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.layout_add_idea.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import javax.inject.Inject


class CategoryActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var recyclerAdapter: IdeasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)
        initViewModel()
        initBinding()
        setupFab()
        setupAppBarContentFade()
        setupRecycler()
    }

    private fun initViewModel() {
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)

        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(CategoryViewModel::class.java)
        viewModel.loadData(categoryId)

        viewModel.newIdeaEvent.observe(this, Observer {
            toast("Idea added!")
        })
        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                recyclerAdapter.replaceData(it)
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
            showAddIdeaDialog()
        }
    }

    private fun showAddIdeaDialog() {
        val view = View.inflate(this, R.layout.layout_add_idea, null)
        alert {
            customView = view
            positiveButton("Save") {
                val content = view.editText_content.text.toString()
                val source = view.editText_source.text.toString()

                viewModel.addNewIdea(content, source)
            }
            negativeButton("Cancel") {}
        }.show()
    }

    private fun setupAppBarContentFade() {
        binding.appBar.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            binding.textViewSchedule.alpha = 1.0f - Math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        })
    }

    private fun setupRecycler() {
        recyclerAdapter = IdeasAdapter(viewModel)
        idea_recycler.layoutManager = LinearLayoutManager(this)
        idea_recycler.adapter = recyclerAdapter
    }

    companion object {
        val EXTRA_CATEGORY_ID = "CATEGORY_ID"
    }
}
