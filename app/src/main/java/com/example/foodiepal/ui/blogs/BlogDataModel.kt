package com.example.foodiepal.ui.blogs

import java.io.Serializable

class BlogDataModel(
    val blogTitle: String?,
    val blogPostedBy: String?,
    val blogContent: String?,
    val commentList: MutableList<CommentDataModel>
) : Serializable