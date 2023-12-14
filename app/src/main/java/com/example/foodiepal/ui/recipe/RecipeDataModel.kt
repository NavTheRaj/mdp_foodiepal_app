package com.example.foodiepal.ui.recipe

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class RecipeDataModel(
    val recipeName: String?,
    val recipeDescription: String?,
//    val recipeIngredients: String?,
    val recipeInstructions: String?,
    val recipeImage: Uri? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(recipeName)
        parcel.writeString(recipeDescription)
        parcel.writeString(recipeInstructions)
        parcel.writeParcelable(recipeImage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeDataModel> {
        override fun createFromParcel(parcel: Parcel): RecipeDataModel {
            return RecipeDataModel(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
