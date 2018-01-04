package com.djacoronel.lark.categories

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.djacoronel.lark.BR
import com.djacoronel.lark.data.model.Category
import com.djacoronel.lark.databinding.ItemCategoryBinding


/**
 * Created by djacoronel on 12/24/17.
 */
class CategoriesAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var categories = listOf<Category>()

    fun replaceData(categories: List<Category>) {
        setList(categories)
    }

    private fun setList(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = categories.size

    inner class MyViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            val itemActionListener = object: CategoryItemActionListener{
                override fun onCategoryClicked(categoryId: Long) {
                    mainViewModel.openCategoryEvent.value = categoryId
                }
            }

            binding.category = category
            binding.executePendingBindings()
            binding.listener = itemActionListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItemForPosition(position)
        (holder as MyViewHolder).bind(item)
    }

    private fun getItemForPosition(position: Int): Category {
        return categories[position]
    }
}