package com.example.foodiepal.ui.contacts

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.foodiepal.R

class ContactsFragment : Fragment() {

    private lateinit var viewModel: ContactsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
        val phoneTextView = rootView?.findViewById<ImageView>(R.id.phoneView)
        val emailTextView = rootView?.findViewById<ImageView>(R.id.gmailView)
        val fbImageView = rootView?.findViewById<ImageView>(R.id.facebookView)
        val snapChatView = rootView?.findViewById<ImageView>(R.id.snapChatView)
        val youtubeView = rootView?.findViewById<ImageView>(R.id.youtubeView)


        phoneTextView?.setOnClickListener {
            val phoneNumber = phoneTextView?.contentDescription.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialIntent)
        }

        emailTextView?.setOnClickListener {
            val emailAddress = emailTextView?.contentDescription.toString()
            val mailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$emailAddress"))
            startActivity(mailIntent)
        }

        fbImageView?.setOnClickListener {
            var fbProfileName = fbImageView?.contentDescription.toString()
            openFacebookProfile(fbProfileName)
        }

        snapChatView?.setOnClickListener {
            var scProfileName = snapChatView?.contentDescription.toString()
            openSnapChatProfile(scProfileName)
        }

        youtubeView?.setOnClickListener {
            var ytProfileName = youtubeView?.contentDescription.toString()
            openYoutubePofile(ytProfileName)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactsModel::class.java)
    }

    private fun openFacebookProfile(fbProfileName: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/$fbProfileName")
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openSnapChatProfile(scProfile: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.snapchat.com/$scProfile")
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openYoutubePofile(website: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/$website")
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}