package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.categories.MainActivity
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
        val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(AddEditViewModel::class.java)
        viewModel.loadCategory(categoryId)

        viewModel.categorySavedEvent.observe(this, Observer {
            setResult(MainActivity.ADD_EDIT_RESULT_OK)
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
        dialogBinding.numberPickerSetInterval.minValue = 0
        dialogBinding.numberPickerSetInterval.maxValue = intervals.lastIndex
        dialogBinding.numberPickerSetInterval.displayedValues = intervals

        alert {
            customView = dialogBinding.root
            positiveButton("Set") {
                val interval = dialogBinding.numberPickerSetInterval.value
                val hourOfDay = dialogBinding.timePickerTimeOfDay.currentHour
                val minute = dialogBinding.timePickerTimeOfDay.currentMinute

                viewModel.interval = DateTimeUtil.intervalToMillis(intervals[interval])
                viewModel.time = DateTimeUtil.hourMinuteToMillis(hourOfDay, minute)
            }
            negativeButton("Cancel") {}
        }.show()
    }

    private fun showSetColorDialog() {
        val view = View.inflate(this, R.layout.layout_set_color, null)
        val colors = resources.getIntArray(R.array.colors)

        val scale = resources.displayMetrics.density
        val dpPaddingInPx = (8 * scale).toInt()
        val dpSideInPx = (50 * scale).toInt()
        val params = LinearLayout.LayoutParams(dpSideInPx, dpSideInPx)

        val dialog = alert {
            title = "Pick Category Color"
            customView = view
            negativeButton("Cancel") {}
        }.show()

        for (color in colors) {
            val colorCircle = ImageView(this)
            colorCircle.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.color_circle, null))
            colorCircle.layoutParams = params
            colorCircle.setColorFilter(color)
            colorCircle.setPadding(dpPaddingInPx, dpPaddingInPx, dpPaddingInPx, dpPaddingInPx)

            colorCircle.setOnClickListener {
                viewModel.color.set(color)
                Log.i("COLOR:", color.toString())
                dialog.dismiss()
            }

            view.color_grid.addView(colorCircle)
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "CATEGORY_ID"
        const val REQUEST_CODE = 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_add_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                viewModel.saveCategory()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
