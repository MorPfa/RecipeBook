package com.example.recipebook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.data.Flavor
import com.example.recipebook.data.ListItem
import com.example.recipebook.data.Recipe
import com.example.recipebook.data.Title
import com.example.recipebook.model.BaseViewHolder
import com.example.recipebook.model.RecipeViewHolder
import com.example.recipebook.model.TitleViewHolder

private const val VIEW_TYPE_TITLE = 0
private const val VIEW_TYPE_RECIPE = 1

class RecipesAdapter(
    private val layoutInflater: LayoutInflater,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    interface OnClickListener {
        fun onItemClick(recipe: Recipe)
    }

    val swipeToDeleteCallback = SwipeToDeleteCallback()

    private val savoryTitle = Title("Savory")
    private val sweetTitle = Title("Sweet")
    private val listItems = mutableListOf<ListItem>(savoryTitle, sweetTitle)

    fun addRecipe(recipe: Recipe) {
        val insertionIndex = listItems.indexOf(when (recipe.flavor) {
            Flavor.SAVORY -> savoryTitle
            Flavor.SWEET -> sweetTitle
        }) + 1
        listItems.add(insertionIndex, recipe)
        notifyItemInserted(insertionIndex)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            VIEW_TYPE_TITLE -> TitleViewHolder(
                layoutInflater.inflate(R.layout.title_item, parent, false)
            )
            VIEW_TYPE_RECIPE -> RecipeViewHolder(
                layoutInflater.inflate(R.layout.recipe_item, parent, false),
                object : RecipeViewHolder.OnClickListener {
                    override fun onClick(recipe: Recipe) {
                        onClickListener.onItemClick(recipe)
                    }
                }
            )
            else -> throw IllegalStateException("Unexpected view type $viewType")
        }

    override fun getItemViewType(position: Int) = when (listItems[position]) {
        is Title -> VIEW_TYPE_TITLE
        is Recipe -> VIEW_TYPE_RECIPE
        else -> throw IllegalStateException("Unexpected data type at $position")
    }
    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bindData(listItems[position])



    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) = if (viewHolder is RecipeViewHolder) {
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) or makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        } else {
            0
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            listItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
