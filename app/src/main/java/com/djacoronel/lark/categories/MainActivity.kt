package com.djacoronel.lark.categories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.util.NotificationScheduler
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViewModel()
        setupFab()
        setupRecycler()
    }

    private fun initViewModel() {
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(MainViewModel::class.java)
        viewModel.loadCategories()

        viewModel.newCategoryEvent.observe(this, Observer {
            this.addNewCategory()
        })
        viewModel.openCategoryEvent.observe(this, Observer<Long> { categoryId ->
            categoryId?.let {
                this.openCategory(it)
            }
        })
        viewModel.categories.observe(this, Observer { categories ->
            categories?.let {
                    recyclerAdapter.replaceData(it)
            }
        })
    }

    private fun addNewCategory() {
        val intent = Intent(this, AddEditCategoryActivity::class.java)
        startActivityForResult(intent, AddEditCategoryActivity.REQUEST_CODE)
    }

    private fun openCategory(categoryId: Long) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId)
        startActivity(intent)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            viewModel.addNewCategory()
        }
    }

    private fun setupRecycler() {
        recyclerAdapter = CategoriesAdapter(viewModel)
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = recyclerAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddEditCategoryActivity.REQUEST_CODE) {
            if (resultCode == AddEditCategoryActivity.ADD_EDIT_RESULT_OK) {
                NotificationScheduler(this).setupNotificationAlarms()
            }
        }
    }
}
