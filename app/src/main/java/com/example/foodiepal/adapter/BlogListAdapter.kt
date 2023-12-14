package com.example.foodiepal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
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
        val cardView: CardView = itemView.findViewById(R.id.blogCardView)
        val blogTitleView: TextView = itemView.findViewById(R.id.blogTitleTextView)
        val blogContentView: TextView = itemView.findViewById(R.id.blogContentTextView)
        val blogPosterTextView: TextView = itemView.findViewById(R.id.blogPosterTextView)

        //        val commentListView: ListView = itemView.findViewById(R.id.commentsListView)
        val editTextComment: TextView = itemView.findViewById(R.id.editTextComment)
        val btnPostComment: Button = itemView.findViewById(R.id.btnPostComment)
        val commentListView: RecyclerView = itemView.findViewById(R.id.commentsListView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.blogs_list, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int = blogList.size

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogObj = blogList[position]

        holder.blogTitleView.text = blogObj.blogTitle
        holder.blogContentView.text = blogObj.blogContent
        holder.blogPosterTextView.text = blogObj.blogPostedBy

        val commentAdapter = CommentListAdapter(context, blogObj.commentList)
        holder.commentListView.layoutManager = LinearLayoutManager(context)
        holder.commentListView.adapter = commentAdapter

        holder.btnPostComment.setOnClickListener {
            val newCommentText = holder.editTextComment.text.toString()

            if (newCommentText.isNotEmpty()) {
                val newComment = CommentDataModel(newCommentText, loggedInUser, LocalDateTime.now())
                blogObj.commentList.add(newComment)
                holder.editTextComment.setText("")
                commentAdapter.notifyDataSetChanged() // Notify the CommentListAdapter of the data change
            }
        }

        justifyRecyclerViewHeightBasedOnChildren(holder.commentListView)
    }

    private fun justifyRecyclerViewHeightBasedOnChildren(recyclerView: RecyclerView) {
        val adapter = recyclerView.adapter ?: return
        val layoutManager = recyclerView.layoutManager
        if (layoutManager == null || layoutManager !is LinearLayoutManager) {
            return
        }

        val itemCount = adapter.itemCount
        val dividerHeight =
            recyclerView.context.resources.getDimensionPixelSize(R.dimen.item_divider_height) // Replace with your desired divider height resource

        var totalHeight = 0
        for (i in 0 until itemCount) {
            val childView = layoutManager.findViewByPosition(i)
            childView?.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            totalHeight += childView?.measuredHeight ?: 0
        }

        val layoutParams = recyclerView.layoutParams
        layoutParams.height = totalHeight + (dividerHeight * (itemCount - 1))
        recyclerView.layoutParams = layoutParams
        recyclerView.requestLayout()
    }
}