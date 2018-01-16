package com.djacoronel.lark.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.addeditidea.AddEditIdeaActivity
import com.djacoronel.lark.openidea.OpenIdeaActivity
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.databinding.ActivityCategoryBinding
import dagger.android.AndroidInjection
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import javax.inject.Inject


class CategoryActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var recyclerAdapter: IdeasAdapter
    private var categoryId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        initBinding()
        initViewModel()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupFab()
        setupAppBarContentFade()
        setupRecycler()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
    }

    private fun initViewModel() {
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(CategoryViewModel::class.java)
        viewModel.loadData(categoryId)
        binding.viewModel = viewModel

        viewModel.category.observe(this, Observer { category ->
            category?.let {
                binding.category = it
            }
        })
        viewModel.editCategoryEvent.observe(this, Observer { categoryId ->
            categoryId?.let {
                editCategory(it)
            }
        })
        viewModel.deleteCategoryEvent.observe(this, Observer { categoryId ->
            categoryId?.let {
                showDeleteCategoryDialog(it)
            }
        })
        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                recyclerAdapter.replaceData(it)
            }
        })
        viewModel.newIdeaEvent.observe(this, Observer {
            newIdea()
        })
        viewModel.openIdeaEvent.observe(this, Observer { ideaId ->
            ideaId?.let {
                openIdea(categoryId, it)
            }
        })
        viewModel.editIdeaEvent.observe(this, Observer { idea ->
            idea?.let {
                editIdea(it)
            }
        })
        viewModel.deleteIdeaEvent.observe(this, Observer { ideaId ->
            ideaId?.let {
                showDeleteIdeaDialog(it)
            }
        })
    }

    private fun editCategory(categoryId: Long) {
        val intent = Intent(this, AddEditCategoryActivity::class.java)
        intent.putExtra(AddEditCategoryActivity.EXTRA_CATEGORY_ID, categoryId)
        startActivity(intent)
    }

    private fun showDeleteCategoryDialog(categoryId: Long) {
        alert {
            title = "Are you sure you want to delete this category?"
            yesButton {
                viewModel.deleteCategory(categoryId)
                finish()
            }
            noButton { }
        }.show()
    }

    private fun newIdea() {
        val intent = Intent(this, AddEditIdeaActivity::class.java)
        intent.putExtra(AddEditIdeaActivity.EXTRA_CATEGORY_ID, categoryId)
        startActivity(intent)
    }

    private fun openIdea(categoryId: Long, ideaId: Long) {
        val intent = Intent(this, OpenIdeaActivity::class.java)
        intent.putExtra(OpenIdeaActivity.EXTRA_CATEGORY_ID, categoryId)
        intent.putExtra(OpenIdeaActivity.EXTRA_IDEA_ID, ideaId)
        startActivity(intent)
    }

    private fun editIdea(idea: Idea) {
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        val intent = Intent(this, AddEditIdeaActivity::class.java)
        intent.putExtra(AddEditIdeaActivity.EXTRA_IDEA_ID, idea.id)
        intent.putExtra(AddEditIdeaActivity.EXTRA_CATEGORY_ID, categoryId)

        startActivity(intent)
    }

    private fun showDeleteIdeaDialog(ideaId: Long) {
        alert {
            title = "Delete this idea?"
            yesButton { viewModel.deleteIdea(ideaId) }
            noButton { }
        }.show()
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            viewModel.newIdeaEvent.call()
        }
    }

    private fun setupRecycler() {
        recyclerAdapter = IdeasAdapter(viewModel)
        binding.ideaRecycler.layoutManager = LinearLayoutManager(this)
        binding.ideaRecycler.adapter = recyclerAdapter
    }

    private fun setupAppBarContentFade() {
        binding.appBar.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            binding.textViewSchedule.alpha =
                    1.0f - Math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_category_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit -> {
                viewModel.editCategoryEvent.value = categoryId
                true
            }
            R.id.menu_delete -> {
                viewModel.deleteCategoryEvent.value = categoryId
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val EXTRA_CATEGORY_ID = "CATEGORY_ID"
    }
}
