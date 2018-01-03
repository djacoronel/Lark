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
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
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
        val colors = resources.getIntArray(R.array.colors)

        val scale = resources.displayMetrics.density
        val dpPaddingInPx = (8 * scale).toInt()
        val dpSideInPx = (50 * scale).toInt()
        val params = LinearLayout.LayoutParams(dpSideInPx,dpSideInPx)

        val dialog = alert {
            title = "Pick Category Color"
            customView = view
            positiveButton("Reset") { viewModel.clearColor() }
            negativeButton("Cancel") {}
        }.show()

        for (color in colors){
            val colorCircle = ImageView(this)
            colorCircle.setImageDrawable(resources.getDrawable(R.drawable.color_circle))
            colorCircle.layoutParams = params
            colorCircle.setColorFilter(color)
            colorCircle.setPadding(dpPaddingInPx, dpPaddingInPx, dpPaddingInPx, dpPaddingInPx)

            colorCircle.setOnClickListener {
                viewModel.color.set(color)
                dialog.dismiss()
            }

            view.color_grid.addView(colorCircle)
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
