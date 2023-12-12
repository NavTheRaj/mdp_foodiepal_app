package com.example.foodiepal.ui.recipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.foodiepal.R

class RecipeFormBuilder(
    private val context: Context,
    private val onImageSelected: (Uri?) -> Unit
) {
    private var selectedImageUri: Uri? = null


    fun showForm(){
        createForm {  };
    }
    private fun createForm(onSave: (RecipeDataModel) -> Unit) {
        val recipeFormView = LayoutInflater.from(context).inflate(R.layout.recipe_form, null)

        val recipeNameField = recipeFormView.findViewById<EditText>(R.id.recipeNameField)
        val recipeDescriptionField =
            recipeFormView.findViewById<EditText>(R.id.recipeDescriptionField)
        val recipeIngredientsField =
            recipeFormView.findViewById<EditText>(R.id.recipeIngredientsField)
        val recipeInstructionField =
            recipeFormView.findViewById<EditText>(R.id.recipeInstructionField)
        val recipeImageView = recipeFormView.findViewById<ImageView>(R.id.recipeImageView)

        // Set up the AlertDialog.Builder
        val builder = AlertDialog.Builder(context)
            .setTitle("Add new recipe")
            .setView(recipeFormView)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Save Recipe") { dialog, _ ->
                // Retrieve values from the form
                val recipeData = RecipeDataModel(
                    recipeNameField.text.toString(),
                    recipeDescriptionField.text.toString(),
                    recipeIngredientsField.text.toString(),
                    recipeInstructionField.text.toString(),
                    selectedImageUri
                )

                Log.i("RecipeData", recipeData.toString())
                onSave(recipeData)
                dialog.dismiss()
            }

        // Add OnClickListener for the ImageView to open image picker
        recipeImageView.setOnClickListener {
            openImagePicker()
        }

        // Show the AlertDialog
        val recipeDialog = builder.create()
        recipeDialog.show()
    }

    private fun openImagePicker() {
        Log.i("RecipeFormBuilder", "openImagePicker called")

        val pickImageIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        (context as? Activity)?.startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun handleImageSelection(uri: Uri?) {
        Log.i("",uri.toString());
        selectedImageUri = uri
        Log.i("SelectedImage", selectedImageUri.toString())
        onImageSelected(selectedImageUri)
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 123
    }
}
