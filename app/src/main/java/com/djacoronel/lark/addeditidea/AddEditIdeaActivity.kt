package com.djacoronel.lark.addeditidea

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.djacoronel.lark.R
import com.djacoronel.lark.ViewModelFactory
import com.djacoronel.lark.databinding.ActivityAddEditIdeaBinding
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_add_edit_idea.*
import javax.inject.Inject

class AddEditIdeaActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityAddEditIdeaBinding
    private lateinit var viewModel: AddEditIdeaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)

        initBinding()
        initViewModel()
    }

    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_idea)
    }

    private fun initViewModel(){
        val ideaId = intent.getLongExtra(EXTRA_IDEA_ID,0)
        val viewModelProvider = ViewModelProviders.of(this, viewModelFactory)
        viewModel = viewModelProvider.get(AddEditIdeaViewModel::class.java)
        viewModel.loadIdea(ideaId)

        viewModel.category.observe(this, Observer { category ->
            category?.let {
                binding.category = it
            }
        })

        binding.viewModel = viewModel
    }

    companion object {
        const val EXTRA_IDEA_ID = "IDEA_ID"
    }
}
