package com.djacoronel.lark.category

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djacoronel.lark.R
import com.djacoronel.lark.data.model.Idea
import com.djacoronel.lark.databinding.ItemIdeaBinding

/**
 * Created by djacoronel on 1/6/18.
 */
class IdeasAdapter(private val categoryViewModel: CategoryViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var ideas = listOf<Idea>()

    fun replaceData(ideas: List<Idea>) {
        setList(ideas)
    }

    private fun setList(ideas: List<Idea>) {
        this.ideas = ideas
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ideas.size

    inner class ViewHolder(private val binding: ItemIdeaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(idea: Idea) {
            val itemActionListener = object : IdeaItemActionListener {
                override fun onIdeaClicked(ideaId: Long) {
                    if (ideaId != 0.toLong())
                        categoryViewModel.openIdeaEvent.value = ideaId
                }

                override fun onIdeaLongClicked(view: View): Boolean {
                    if (idea.id != 0.toLong())
                        showPopupMenu()
                    return false
                }
            }

            binding.idea = idea
            binding.listener = itemActionListener
            binding.executePendingBindings()
        }

        fun showPopupMenu() {
            val popup = PopupMenu(itemView.context, itemView, Gravity.END)
            popup.menuInflater.inflate(R.menu.menu_idea_item, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Edit" -> categoryViewModel.editIdeaEvent.value = ideas[adapterPosition]
                    "Delete" -> categoryViewModel.deleteIdeaEvent.value = ideas[adapterPosition].id
                }
                true
            }
            popup.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemIdeaBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItemForPosition(position)
        (holder as ViewHolder).bind(item)
    }

    private fun getItemForPosition(position: Int): Idea {
        return ideas[position]
    }
}