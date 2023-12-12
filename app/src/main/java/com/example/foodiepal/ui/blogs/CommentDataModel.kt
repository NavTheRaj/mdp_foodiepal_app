package com.example.foodiepal.ui.blogs

import java.io.Serializable
import java.time.LocalDateTime

class CommentDataModel(
    val commentText: String,
    val postBy: String,
    val postDate: LocalDateTime
) : Serializable {
}