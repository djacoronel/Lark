package com.djacoronel.lark.addeditcategory

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
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

    }

    companion object {
        const val REQUEST_CODE = 0
        const val REQUEST_CODE_ADD = 0
        const val REQUEST_CODE_EDIT = 0
    }
}
