package com.example.foodiepal.adapter

import CommentListAdapter
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiepal.R
import com.example.foodiepal.ui.blogs.BlogDataModel
import com.example.foodiepal.ui.blogs.CommentDataModel
import java.time.LocalDateTime

class BlogListAdapter(
    private val context: Context,
    private val loggedInUser: String,
    private val blogList: List<BlogDataModel>
) : RecyclerView.Adapter<BlogListAdapter.BlogViewHolder>() {

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.blogCardView)
        val blogTitleView = itemView.findViewById<TextView>(R.id.blogTitleTextView)
        val blogContentView = itemView.findViewById<TextView>(R.id.blogContentTextView)
        val blogPosterTextView = itemView.findViewById<TextView>(R.id.blogPosterTextView)
        val commentListView = itemView.findViewById<ListView>(R.id.commentsListView)
        val editTextComment = itemView.findViewById<TextView>(R.id.editTextComment)
        val btnPostComment = itemView.findViewById<Button>(R.id.btnPostComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.blogs_list, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blogList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogObj = blogList[position]

        holder.blogTitleView.text = blogObj.blogTitle
        holder.blogContentView.text = blogObj.blogContent
        holder.blogPosterTextView.text = blogObj.blogPostedBy

        val commentAdapter = CommentListAdapter(context, blogObj.commentList)
        holder.commentListView.adapter = commentAdapter

        holder.btnPostComment.setOnClickListener {
            blogObj.commentList.add(
                CommentDataModel(
                    holder.editTextComment.text.toString(),
                    loggedInUser,
                    LocalDateTime.now()
                )
            )
            holder.editTextComment.setText("")
            commentAdapter.notifyDataSetChanged()
            this.notifyDataSetChanged()
        }

        justifyListViewHeightBasedOnChildren(holder.commentListView)
    }

    fun justifyListViewHeightBasedOnChildren(listView: ListView) {
        val adapter = listView.adapter ?: return
        val vg: ViewGroup = listView
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, vg)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val par = listView.layoutParams
        par.height = totalHeight + listView.dividerHeight * (adapter.count - 1)
        listView.layoutParams = par
        listView.requestLayout()
    }
}