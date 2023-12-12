package com.example.foodiepal.ui.recipe

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiepal.R
import com.example.foodiepal.adapter.RecipeListAdapter
import com.example.foodiepal.databinding.FragmentRecipeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val recipeList: MutableList<RecipeDataModel> = mutableListOf()
    private val recipeListAdapter: RecipeListAdapter by lazy {
        RecipeListAdapter(requireContext(), recipeList)
    }
    private val recipeFormBuilder: RecipeFormBuilder by lazy {
        RecipeFormBuilder(requireContext(), ::onImageSelected)
    }

    private val binding get() = _binding!!

    private fun onImageSelected(uri: Uri?) {
        // Handle the selected image URI, you can use it as needed
        Log.i("Image selected", uri.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("ActivityResult", "requestCode: $requestCode, resultCode: $resultCode")

        if (requestCode == RecipeFormBuilder.PICK_IMAGE_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK && data != null
        ) {
            recipeFormBuilder.handleImageSelection(data.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter = recipeListAdapter

        loadRecipesFromJson()

        binding.addNewRecipeBtn.setOnClickListener {
            recipeFormBuilder.showForm()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadRecipesFromJson() {
        try {
            val jsonString = loadJsonFromAsset(requireContext(), "recipes.json")
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                val imageWithOutExt = jsonObject.getString("recipeImage").split(".")[0]
                Log.i("Image without extension", imageWithOutExt)
                val imagePath = "android.resource://${requireContext().packageName}/drawable/$imageWithOutExt"
                val imageUri = Uri.parse(imagePath)

                val recipe = RecipeDataModel(
                    jsonObject.getString("recipeName"),
                    jsonObject.getString("recipeDescription"),
                    jsonObject.getString("recipeIngredients"),
                    jsonObject.getString("recipeInstructions"),
                    imageUri
                )
                recipeList.add(recipe)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJsonFromAsset(context: Context, filename: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


}