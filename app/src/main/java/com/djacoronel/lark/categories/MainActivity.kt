package com.djacoronel.lark.categories

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.util.AlarmReceiver
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
        setupDrawerToggle()
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
                setupNotificationAlarms(it)
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

    private fun setupDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setupRecycler() {
        recyclerAdapter = CategoriesAdapter(viewModel)
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = recyclerAdapter
    }

    private fun setupNotificationAlarms(categories: List<Category>) {
        for (i in 0..categories.lastIndex){
            val alarmIntent = Intent(this, AlarmReceiver::class.java)
            alarmIntent.putExtra("category", categories[i].label)
            alarmIntent.putExtra("categoryId", categories[i].id)
            alarmIntent.putExtra("requestCode", i)
            val pendingIntent = PendingIntent.getBroadcast(this, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT)

            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        val ADD_EDIT_RESULT_OK = 1
    }
}
