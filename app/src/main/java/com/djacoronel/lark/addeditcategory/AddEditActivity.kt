package com.djacoronel.lark.addeditcategory

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditActivity_MembersInjector.create
import com.djacoronel.lark.databinding.ActivityAddEditBinding
import com.djacoronel.lark.databinding.LayoutSetTimeBinding
import dagger.android.AndroidInjection
import javax.inject.Inject


class AddEditActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddEditViewModel
    private lateinit var mainBinding: ActivityAddEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initViewModel()
        initMainBinding()
    }

    private fun initViewModel() {
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(AddEditViewModel::class.java)

        viewModel.categoryAddedEvent.observe(this, Observer {
            setResult(REQUEST_CODE_ADD)
            finish()
        })
    }

    private fun initMainBinding(){
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit)
        mainBinding.viewModel = viewModel
        mainBinding.textViewSetTime.setOnClickListener { getSetTimeDialog().show() }
    }

    private fun getSetTimeDialog(): AlertDialog {
        val intervals = resources.getStringArray(R.array.intervals)
        val dialogBinding = LayoutSetTimeBinding
                .inflate(LayoutInflater.from(this), mainBinding.root as ViewGroup, false)
        dialogBinding.viewModel = viewModel
        dialogBinding.numberPickerSetInterval.displayedValues = intervals
        dialogBinding.numberPickerSetInterval.minValue = 0
        dialogBinding.numberPickerSetInterval.maxValue = intervals.lastIndex

        val onClickListener = DialogInterface.OnClickListener { _, _ ->
            viewModel.interval = dialogBinding.numberPickerSetInterval.value
            viewModel.hourOfDay = dialogBinding.timePickerTimeOfDay.currentHour
            viewModel.minute = dialogBinding.timePickerTimeOfDay.currentMinute
        }
        return AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setPositiveButton("Set", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
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
