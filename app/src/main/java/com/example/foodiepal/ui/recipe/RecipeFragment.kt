package com.example.foodiepal.ui.recipe
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiepal.R
import com.example.foodiepal.adapter.RecipeListAdapter
import com.example.foodiepal.databinding.FragmentRecipeBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val recipeList: MutableList<RecipeDataModel> = mutableListOf()
    private lateinit var recipeImageView: ImageView // Declare the ImageView

    private val recipeListAdapter: RecipeListAdapter by lazy {
        RecipeListAdapter(requireContext(), recipeList)
    }

    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    handleImageSelection(it.data)
                }
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
            showRecipeForm()
        }

        return root
    }

    private fun showRecipeForm() {
        val recipeFormView = LayoutInflater.from(requireContext())
            .inflate(R.layout.recipe_form, null)

        val recipeNameField = recipeFormView.findViewById<EditText>(R.id.recipeNameField)
        val recipeDescriptionField =
            recipeFormView.findViewById<EditText>(R.id.recipeDescriptionField)
//        val recipeIngredientsField =
//            recipeFormView.findViewById<EditText>(R.id.recipeIngredientsField)
        val recipeInstructionField =
            recipeFormView.findViewById<EditText>(R.id.recipeInstructionField)
        recipeImageView = recipeFormView.findViewById(R.id.recipeImageView)

        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Add new recipe")
            .setView(recipeFormView)
            .setNeutralButton("Reset",null)
            .setPositiveButton("Save Recipe",null)
            .setNegativeButton("Cancel",null)


        recipeImageView.setOnClickListener {
            openImagePicker()
        }

        val recipeDialog = builder.create()
        recipeDialog.show()
        recipeDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener{
            recipeNameField.text.clear()
            recipeDescriptionField.text.clear()
//            recipeIngredientsField.text.clear()
            recipeInstructionField.text.clear()
            selectedImageUri = null
            recipeImageView.setImageURI(null)
        }

        recipeDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{
            selectedImageUri?.let {
                val recipeData = RecipeDataModel(
                    recipeNameField.text.toString(),
                    recipeDescriptionField.text.toString(),
//                    recipeIngredientsField.text.toString(),
                    recipeInstructionField.text.toString(),
                    it
                )

                Log.i("RecipeData", recipeData.toString())
                onImageSelected(it)
                recipeList.add(recipeData)
                recipeListAdapter.notifyDataSetChanged();
                Log.i("RecipeData", recipeData.toString())
                recipeDialog.dismiss()
                Toast.makeText(context,"Added a new recipe",Toast.LENGTH_SHORT)
            }
        }

        recipeDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener{
            Toast.makeText(context,"Cancelled",Toast.LENGTH_SHORT)
            recipeDialog.dismiss()
        }
    }

    private fun openImagePicker() {
        if (startForResult != null) {
            val pickImageIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startForResult.launch(pickImageIntent)
        } else {
            Log.e("RecipeFragment", "startForResult is null")
        }
    }

    private fun onImageSelected(uri: Uri) {
        Log.i("Image selected", uri.toString())

        // Handle the selected image URI, you can use it as needed
        recipeImageView.setImageURI(uri)
    }

    private fun loadRecipesFromJson() {
        try {
            val jsonString = loadJsonFromAsset(requireContext(), "recipes.json")
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                val imageWithOutExt = jsonObject.getString("recipeImage").split(".")[0]
                Log.i("Image without extension", imageWithOutExt)
                val imagePath =
                    "android.resource://${requireContext().packageName}/drawable/$imageWithOutExt"
                val imageUri = Uri.parse(imagePath)

                val recipe = RecipeDataModel(
                    jsonObject.getString("recipeName"),
                    jsonObject.getString("recipeDescription"),
//                    jsonObject.getString("recipeIngredients"),
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

    private fun handleImageSelection(uri: Uri?) {
        selectedImageUri = uri
        Log.i("SelectedImage", selectedImageUri.toString())
        onImageSelected(selectedImageUri!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
