package com.example.foodiepal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.foodiepal.R
import com.example.foodiepal.ui.recipe.RecipeDataModel

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val bundle = intent.extras
        var recipeDetailsObj: RecipeDataModel? = null
        if (bundle != null) {
            recipeDetailsObj = bundle.getParcelable("RECIPE_OBJECT") as RecipeDataModel?
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var recipeNameTxt = findViewById<TextView>(R.id.recipeNameLabel)
        var recipeDescTxt = findViewById<TextView>(R.id.descriptionTextView)
        var instructionTxt = findViewById<TextView>(R.id.instructionTextView)
        var foodAvatar = findViewById<ImageView>(R.id.foodAvatar);

        recipeNameTxt.text = recipeDetailsObj?.recipeName.toString()
        recipeDescTxt.text = recipeDetailsObj?.recipeDescription.toString()
        instructionTxt.text = recipeDetailsObj?.recipeInstructions.toString()
        foodAvatar.setImageURI(recipeDetailsObj?.recipeImage);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}