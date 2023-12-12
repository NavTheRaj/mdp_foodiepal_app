package com.example.foodiepal.ui.blogs

import CommentListAdapter
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiepal.R
import com.example.foodiepal.adapter.BlogListAdapter
import com.example.foodiepal.databinding.FragmentBlogsBinding
import java.time.LocalDateTime

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogsBinding? = null

    private lateinit var sharedPreferences: SharedPreferences

    private val blogList = mutableListOf<BlogDataModel>(
        BlogDataModel(
            "Roasted Jerusalem Artichokes with Burnt Hay Butter @ Noma (Copenhagen, Denmark)",
            "Massimo Bottura",
            " .A culinary masterpiece. The Jerusalem artichokes are roasted to perfection, with a slightly crispy exterior and a creamy, melt-in-your-mouth interior. The Burnt Hay Butter adds a rich, smoky flavor that is both complex and sophisticated. This dish is a must-try for any fan of fine dining.",
            mutableListOf<CommentDataModel>(
                CommentDataModel(
                    "If you are looking for a unique and unforgettable dining experience, Noma is the place to go. The restaurant's tasting menu is a culinary adventure that will leave you wanting more. If you have the opportunity to dine at Noma, be sure to order the Roasted Jerusalem Artichokes with Burnt Hay Butter. You won't be disappointed.",
                    "Anne Hathaway",
                    LocalDateTime.now()
                ), CommentDataModel(
                    "This dish is not only delicious, but it is also incredibly beautiful to look at. The Jerusalem artichokes are presented in a striking arrangement, and the Burnt Hay Butter adds a touch of drama. This dish is truly a feast for the eyes and the senses.",
                    "James Bond",
                    LocalDateTime.now()
                )
            )
        )
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(BlogModel::class.java)

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedPreferences =
            requireActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)

        var recyclerView = binding.blogRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val blogListAdapter = BlogListAdapter(
            requireContext(),
            sharedPreferences.getString("username", null).toString(),
            blogList
        )
        recyclerView.adapter = blogListAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}