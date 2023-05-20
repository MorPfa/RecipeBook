package com.example.recipebook

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.recipebook.adapters.RecipesAdapter
import com.example.recipebook.data.Flavor
import com.example.recipebook.data.Recipe
import com.example.recipebook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val recipesList: RecyclerView
            by lazy { findViewById(R.id.list_of_recipes) }
    private val addSavoryButton: View
            by lazy { findViewById(R.id.btn_add_savory) }
    private val addSweetButton: View
            by lazy { findViewById(R.id.btn_add_sweet) }
    private val titleView: TextView
            by lazy { findViewById(R.id.list_of_recipetitles) }
    private val descriptionView: TextView
            by lazy { findViewById(R.id.recipe_description) }

    private lateinit var binding : ActivityMainBinding

    private val recipesAdapter by lazy {
        RecipesAdapter(
            layoutInflater,
            object : RecipesAdapter.OnClickListener {
                override fun onItemClick(recipe: Recipe) {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage(recipe.description)
                        .setPositiveButton("OK", null)
                        .create()
                        .show()
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipesList.apply {
            adapter = recipesAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, VERTICAL, false)

            val itemTouchHelper =
                ItemTouchHelper(recipesAdapter.swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(this)
        }

        addSavoryButton.setOnClickListener {
            addRecipeAndClearForm(Flavor.SAVORY)
        }

        addSweetButton.setOnClickListener {
            addRecipeAndClearForm(Flavor.SWEET)
        }
    }

    private fun addRecipeAndClearForm(flavor: Flavor) {
        val title = titleView.text.toString().trim()
        val description = descriptionView.text.toString().trim()
        if (title.isEmpty() || description.isEmpty()) return

        recipesAdapter.addRecipe(
            Recipe(title, description, flavor)
        )
        titleView.text = ""
        descriptionView.text = ""
    }
}
