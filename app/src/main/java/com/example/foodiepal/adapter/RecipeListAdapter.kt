package com.example.foodiepal.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiepal.R
import com.example.foodiepal.activities.RecipeDetailsActivity
import com.example.foodiepal.ui.recipe.RecipeDataModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import kotlin.math.ceil

class RecipeListAdapter(
    private val context: Context,
    private val recipeList: List<RecipeDataModel>
) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    companion object {
        const val MAX_DESCRIPTION_LENGTH = 150
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeCardView: CardView = itemView.findViewById(R.id.recipeCardView)
        val foodAvatar: ImageView = itemView.findViewById(R.id.foodAvatar)
        val recipeNameView: TextView = itemView.findViewById(R.id.recipeNameView)
        val recipeDescriptionView: TextView = itemView.findViewById(R.id.recipeDescriptionView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_list, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeObj = recipeList[position]
        holder.recipeNameView.text = recipeObj.recipeName
        var productDesc = recipeObj.recipeDescription.toString()

        if (productDesc.length > MAX_DESCRIPTION_LENGTH) {
            productDesc = productDesc.substring(0, MAX_DESCRIPTION_LENGTH) + "..."
        }

        holder.recipeDescriptionView.text = productDesc

        val recipeImageResourceId = recipeObj.recipeImage
        Log.i("Recipe Object", recipeImageResourceId.toString())

        if (recipeImageResourceId != null) {
            holder.foodAvatar.setImageBitmap(
                getCompressedBitmapFromImageUri(
                    recipeImageResourceId,
                    context,
                    100,
                    100,
                    100
                )
            )
        } else {
            holder.foodAvatar.setBackgroundResource(R.drawable.no_recipe)
        }

        holder.recipeCardView.setOnClickListener {
            val intent = Intent(context, RecipeDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("RECIPE_OBJECT", recipeObj)
            intent.putExtras(bundle)
            ContextCompat.startActivity(context, intent, null)
        }
    }
    private fun getCompressedBitmapFromImageUri(
        imageUri: Uri?,
        context: Context,
        maxWidth: Int,
        maxHeight: Int,
        compressionQuality: Int
    ): Bitmap? {
        return try {
            if (imageUri != null) {
                val imageResourceName = imageUri.lastPathSegment
                    ?: throw IllegalArgumentException("Invalid image URI: $imageUri")

                val resourceId = context.resources.getIdentifier(
                    imageResourceName,
                    "drawable",
                    context.packageName
                )

                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeResource(context.resources, resourceId, options)

                val imageWidth = options.outWidth
                val imageHeight = options.outHeight

                val scaleFactor = calculateScaleFactor(imageWidth, imageHeight, maxWidth, maxHeight)
                options.inSampleSize = scaleFactor

                decodeAndCompressBitmap(context, imageUri, options, compressionQuality)
            } else {
                Log.e("RecipeFragment", "Image Uri is null")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RecipeFragment", "Error decoding image from Uri: $imageUri", e)
            null
        }
    }

    private fun decodeAndCompressBitmap(
        context: Context,
        imageUri: Uri,
        options: BitmapFactory.Options,
        compressionQuality: Int
    ): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            options.inJustDecodeBounds = false
            options.inMutable = true

            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            // Compress the bitmap
            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)

            // Recycle the original bitmap if it's not needed anymore
            bitmap?.recycle()

            // Convert the compressed image data to a new Bitmap
            val compressedBitmapData = outputStream.toByteArray()
            BitmapFactory.decodeByteArray(compressedBitmapData, 0, compressedBitmapData.size)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RecipeFragment", "Error decoding and compressing bitmap from Uri: $imageUri", e)
            null
        }
    }

    private fun calculateScaleFactor(imageWidth: Int, imageHeight: Int, maxWidth: Int, maxHeight: Int): Int {
        var scaleFactor = 2

        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            val widthScale = imageWidth.toFloat() / maxWidth.toFloat()
            val heightScale = imageHeight.toFloat() / maxHeight.toFloat()
            scaleFactor = ceil(widthScale.coerceAtLeast(heightScale).toDouble()).toInt()
        }

        return scaleFactor
    }


}
