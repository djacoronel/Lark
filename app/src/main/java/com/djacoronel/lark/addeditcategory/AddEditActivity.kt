package com.djacoronel.lark.addeditcategory

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.addeditcategory.AddEditActivity_MembersInjector.create
import com.djacoronel.lark.databinding.ActivityAddEditBinding
import com.djacoronel.lark.databinding.LayoutSetTimeBinding
import com.djacoronel.lark.util.DateTimeUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_set_color.view.*
import org.jetbrains.anko.alert
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

    private fun initMainBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit)
        mainBinding.viewModel = viewModel
        mainBinding.textViewSetTime.setOnClickListener { showSetTimeDialog() }
        mainBinding.textViewSetColor.setOnClickListener { showSetColorDialog() }
    }

    private fun showSetTimeDialog() {
        val intervals = resources.getStringArray(R.array.intervals)
        val dialogBinding: LayoutSetTimeBinding = DataBindingUtil.
                inflate(LayoutInflater.from(this), R.layout.layout_set_time, null, false)
        dialogBinding.useInterval = viewModel.useInterval
        dialogBinding.numberPickerSetInterval.displayedValues = intervals
        dialogBinding.numberPickerSetInterval.minValue = 0
        dialogBinding.numberPickerSetInterval.maxValue = intervals.lastIndex

        val onClickListener = DialogInterface.OnClickListener { _, _ ->
            val interval = dialogBinding.numberPickerSetInterval.value
            val hourOfDay = dialogBinding.timePickerTimeOfDay.currentHour
            val minute = dialogBinding.timePickerTimeOfDay.currentMinute

            viewModel.interval = DateTimeUtil.intervalToMillis(intervals[interval])
            viewModel.time = DateTimeUtil.hourMinuteToMillis(hourOfDay, minute)
        }
        AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setPositiveButton("Set", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    private fun showSetColorDialog() {
        val view = View.inflate(this, R.layout.layout_set_color, null)

        alert {
            title = "Pick Category Color"
            customView = view
            positiveButton("Reset") { viewModel.clearColor() }
            negativeButton("Cancel") {}
        }.show()

        with(view) {
            val colorCircles = listOf(
                    color_circle_1, color_circle_2, color_circle_3, color_circle_4,
                    color_circle_5, color_circle_6, color_circle_7, color_circle_8,
                    color_circle_9, color_circle_10, color_circle_11, color_circle_12,
                    color_circle_13, color_circle_14, color_circle_15, color_circle_16,
                    color_circle_17, color_circle_18, color_circle_19, color_circle_20,
                    color_circle_21, color_circle_22, color_circle_23, color_circle_24
            )
            for (colorCircle in colorCircles){
                colorCircle.setOnClickListener {
                    //TODO: implement code to set color in viewModel
                }
            }
        }
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
