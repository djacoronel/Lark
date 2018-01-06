package com.djacoronel.lark.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.databinding.ActivityCategoryBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.layout_add_idea.view.*
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
        viewModel.editIdeaEvent.observe(this, Observer { idea ->
            idea?.let {
                this.showEditIdeaDialog(it)
            }
        })
        viewModel.deleteIdeaEvent.observe(this, Observer { ideaId ->
            ideaId?.let {
                this.showDeleteIdeaDialog(ideaId)
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
        val builder = AlertDialog.Builder(this)
        val dialog = builder.setView(view)
                .setPositiveButton("Save", { _, _ ->
                    val content = view.editText_content.text.toString()
                    val source = view.editText_source.text.toString()

                    viewModel.addNewIdea(content, source)
                })
                .setNegativeButton("Cancel", null)
                .create()

        dialog.window.attributes.windowAnimations = R.style.AddIdeaAnimation
        dialog.show()
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun showEditIdeaDialog(idea: Idea){
        val view = View.inflate(this, R.layout.layout_add_idea, null)
        view.editText_content.setText(idea.content)
        view.editText_source.setText(idea.source)

        val builder = AlertDialog.Builder(this)
        val dialog = builder.setView(view)
                .setPositiveButton("Save", { _, _ ->
                    val content = view.editText_content.text.toString()
                    val source = view.editText_source.text.toString()

                    viewModel.updateIdea(idea.id,content, source)
                })
                .setNegativeButton("Cancel", null)
                .create()

        dialog.window.attributes.windowAnimations = R.style.AddIdeaAnimation
        dialog.show()
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun showDeleteIdeaDialog(ideaId: Long){
        alert {
            title = "Delete this idea?"
            yesButton { viewModel.deleteIdea(ideaId) }
            noButton {  }
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
