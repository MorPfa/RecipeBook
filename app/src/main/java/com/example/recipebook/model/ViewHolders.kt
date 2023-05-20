package com.example.recipebook.model

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.data.ListItem
import com.example.recipebook.data.Recipe
import com.example.recipebook.data.Title

abstract class BaseViewHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {
    abstract fun bindData(listItem: ListItem)
}

class TitleViewHolder(containerView: View) : BaseViewHolder(containerView) {
    private val titleView: TextView
            by lazy { containerView.findViewById(R.id.Title) }

    override fun bindData(listItem: ListItem) {
        titleView.text = (listItem as Title).title
    }
}

class RecipeViewHolder(
    containerView: View,
    private val onClickListener: OnClickListener
) : BaseViewHolder(containerView) {
    private val titleView: TextView
            by lazy { containerView.findViewById(R.id.Recipe_name) }

    override fun bindData(listItem: ListItem) {
        titleView.text = (listItem as Recipe).title
        titleView.setOnClickListener {
            onClickListener.onClick(listItem)
        }
    }

    interface OnClickListener {
        fun onClick(recipe: Recipe)
    }
}
