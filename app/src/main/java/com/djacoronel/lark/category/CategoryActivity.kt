package com.djacoronel.lark.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager.LayoutParams
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.addeditidea.AddEditIdeaActivity
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.databinding.ActivityCategoryBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_add_edit_idea.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
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
        setContentView(R.layout.activity_category)

        categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        initViewModel()
        initBinding()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupFab()
        setupAppBarContentFade()
        setupRecycler()
    }

    private fun initViewModel() {
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(CategoryViewModel::class.java)
        viewModel.loadData(categoryId)

        viewModel.ideas.observe(this, Observer { ideas ->
            ideas?.let {
                recyclerAdapter.replaceData(it)
            }
        })
        viewModel.newIdeaEvent.observe(this, Observer {
            toast("Idea added!")
        })
        viewModel.openIdeaEvent.observe(this, Observer { ideaId ->
            ideaId?.let {
                this.openIdea(categoryId,it)
            }
        })
        viewModel.editIdeaEvent.observe(this, Observer { idea ->
            idea?.let {
                this.showEditIdeaDialog(it)
            }
        })
        viewModel.deleteIdeaEvent.observe(this, Observer { ideaId ->
            ideaId?.let {
                this.showDeleteIdeaDialog(it)
            }
        })
        viewModel.editCategoryEvent.observe(this, Observer { categoryId ->
            categoryId?.let {
                this.editCategory(it)
            }
        })
        viewModel.deleteCategoryEvent.observe(this, Observer { categoryId ->
            categoryId?.let {
                this.showDeleteCategoryDialog(it)
            }
        })
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        binding.viewModel = viewModel

        viewModel.category.observe(this, Observer { category ->
            category?.let {
                binding.category = it
            }
        })
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showAddIdeaDialog()
        }
    }

    private fun openIdea(categoryId:Long,ideaId: Long){
        val intent = Intent(this, AddEditIdeaActivity::class.java)
        intent.putExtra(AddEditIdeaActivity.EXTRA_CATEGORY_ID, categoryId)
        intent.putExtra(AddEditIdeaActivity.EXTRA_IDEA_ID, ideaId)
        startActivity(intent)
    }

    private fun showAddIdeaDialog() {
        val view = View.inflate(this, R.layout.layout_add_edit_idea, null)
        val builder = AlertDialog.Builder(this)
        val dialog = builder.setView(view)
                .setPositiveButton("Save", { _, _ ->
                    val content = view.editText_content.text.toString()
                    val source = view.editText_source.text.toString()

                    viewModel.addNewIdea(content, source, categoryId)
                })
                .setNegativeButton("Cancel", null)
                .create()

        dialog.window.attributes.windowAnimations = R.style.AddIdeaAnimation
        dialog.show()
        dialog.window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private fun showEditIdeaDialog(idea: Idea) {
        val view = View.inflate(this, R.layout.layout_add_edit_idea, null)
        view.editText_content.setText(idea.content)
        view.editText_source.setText(idea.source)

        val builder = AlertDialog.Builder(this)
        val dialog = builder.setView(view)
                .setPositiveButton("Save", { _, _ ->
                    val content = view.editText_content.text.toString()
                    val source = view.editText_source.text.toString()

                    viewModel.updateIdea(idea.id, content, source)
                })
                .setNegativeButton("Cancel", null)
                .create()

        dialog.window.attributes.windowAnimations = R.style.AddIdeaAnimation
        dialog.show()
        dialog.window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private fun showDeleteIdeaDialog(ideaId: Long) {
        alert {
            title = "Delete this idea?"
            yesButton { viewModel.deleteIdea(ideaId) }
            noButton { }
        }.show()
    }

    private fun setupAppBarContentFade() {
        binding.appBar.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            binding.textViewSchedule.alpha =
                    1.0f - Math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        })
    }

    private fun setupRecycler() {
        recyclerAdapter = IdeasAdapter(viewModel)
        binding.ideaRecycler.layoutManager = LinearLayoutManager(this)
        binding.ideaRecycler.adapter = recyclerAdapter
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

    companion object {
        val EXTRA_CATEGORY_ID = "CATEGORY_ID"
    }
}
