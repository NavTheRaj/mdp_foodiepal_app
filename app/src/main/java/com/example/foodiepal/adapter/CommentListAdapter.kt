package com.example.foodiepal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiepal.R
import com.example.foodiepal.ui.blogs.CommentDataModel

class CommentListAdapter(
    private val context: Context,
    private val comments: List<CommentDataModel>
) : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val commentView = LayoutInflater.from(context).inflate(R.layout.comment_list, parent, false)
        return ViewHolder(commentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentObj = comments[position]
        holder.usernameTextView.text = commentObj.postBy
        holder.commentTextView.text = commentObj.commentText
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.textViewUsername)
        val commentTextView: TextView = view.findViewById(R.id.textViewComment)
    }
}

