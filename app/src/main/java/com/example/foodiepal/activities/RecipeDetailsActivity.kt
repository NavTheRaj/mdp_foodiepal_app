package com.example.foodiepal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

//        var toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

        var recipeNameTxt = findViewById<TextView>(R.id.recipeNameLabel)
        var recipeDescTxt = findViewById<TextView>(R.id.descriptionTextView)
        var ingredientsTxt = findViewById<TextView>(R.id.ingredientsTextView)
        var instructionTxt = findViewById<TextView>(R.id.instructionTextView)

        recipeNameTxt.text = recipeDetailsObj?.recipeName.toString()
        recipeDescTxt.text = recipeDetailsObj?.recipeDescription.toString()
        ingredientsTxt.text = recipeDetailsObj?.recipeIngredients.toString()
        instructionTxt.text = recipeDetailsObj?.recipeInstructions.toString()

//        productImageView.setImageResource(productObj?.productImage!!)
//        productLogoView.setImageResource(productObj?.productLogo!!)
//
//        val decimalFormat = DecimalFormat("#.##")
//        val priceStr = "$" + decimalFormat.format(productObj?.productPrice)
//
//        productNameTxt.setText(productObj?.productName.toString())
//        productDescTxt.setText(productObj?.productDescription.toString())
//        productPriceTxt.setText(priceStr)
//
//        homeBtn.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button click
        onBackPressed()
        return true
    }

}