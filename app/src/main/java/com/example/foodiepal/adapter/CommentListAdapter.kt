// CommentAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.foodiepal.R
import com.example.foodiepal.ui.blogs.CommentDataModel

class CommentListAdapter(
    private val context: Context,
    private val comments: List<CommentDataModel>
) : BaseAdapter() {

    override fun getCount(): Int {
        return comments.size
    }

    override fun getItem(position: Int): Any {
        return comments[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val commentView: View = LayoutInflater.from(context).inflate(R.layout.comment_list, null)

        val usernameTextView: TextView = commentView.findViewById(R.id.textViewUsername)
        val commentTextView: TextView = commentView.findViewById(R.id.textViewComment)

        val commentObj = getItem(position) as CommentDataModel
        usernameTextView.text = commentObj.postBy
        commentTextView.text = commentObj.commentText

        return commentView
    }
}
