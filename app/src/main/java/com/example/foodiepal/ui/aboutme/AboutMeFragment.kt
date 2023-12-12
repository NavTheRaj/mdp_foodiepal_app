package com.example.foodiepal.ui.aboutme

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodiepal.R

class AboutMeFragment : Fragment() {

    companion object {
        fun newInstance() = AboutMeFragment()
    }

    private lateinit var viewModel: AboutMeModel

    private val aboutMeContent = "Aromas of exotic spices and simmering broths fill the air, a symphony of scents that awaken the senses and beckon one into the heart of the culinary experience. As a master chef, I've dedicated my life to orchestrating these symphonies of flavor, transforming humble ingredients into edible masterpieces that tantalize the taste buds and evoke memories of cherished moments.\n" +
            "\n" +
            "Each dish I create is a journey, a voyage through a tapestry of textures and flavors that transport the diner to far-off lands or evoke nostalgic comfort. The crisp crunch of freshly sautéed vegetables, the delicate melt-in-your-mouth tenderness of braised meats, the subtle sweetness of caramelized onions – these are the brushstrokes that paint my edible canvases."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_about_me, container, false)
        var aboutMeContentTextView = rootView.findViewById<TextView>(R.id.aboutMeContentTextView)
        aboutMeContentTextView?.setText(aboutMeContent)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutMeModel::class.java)
        // TODO: Use the ViewModel
    }

}