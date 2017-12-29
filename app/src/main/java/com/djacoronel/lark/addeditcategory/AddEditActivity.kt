package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.databinding.DataBindingUtil
import com.djacoronel.lark.databinding.ActivityAddEditBinding


class AddEditActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initViewModel()
        val binding: ActivityAddEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit)
        binding.viewModel = viewModel

    }

    private fun initViewModel() {
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
                viewModel.addCategory()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
