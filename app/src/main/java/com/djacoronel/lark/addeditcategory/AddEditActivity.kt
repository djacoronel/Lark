package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.data.model.Category
import dagger.android.AndroidInjection
import javax.inject.Inject

class AddEditActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        initViewModel()
    }

    private fun initViewModel(){
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(AddEditViewModel::class.java)

        viewModel.categoryAddedEvent.observe(this, Observer {
            setResult(REQUEST_CODE_ADD)
            finish()
        })

    }

    companion object {
        const val REQUEST_CODE = 0
        const val REQUEST_CODE_ADD = 1
        const val REQUEST_CODE_EDIT = 2
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_add_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                Log.i("test","save")
                val newCategory = Category()
                newCategory.categoryLabel = "test"
                viewModel.addCategory(newCategory)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
