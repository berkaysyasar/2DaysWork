package com.berkay.a2dayswork.ui.fragment.menuItems

import android.os.Bundle
import android.text.Html
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentAboutBinding
import com.berkay.a2dayswork.databinding.FragmentSettingsBinding

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        val text = "Welcome to 2DaysWork. The aim of this app is to keep track of daily routines and keep notes more organized. This app is developed by one person and will be improved further. Please let the developer know what you find interesting. Thank you for choosing this app."

        // Metni iki yana yaslamak için Layout.Alignment kullanma
        binding.welcomeTextView.text = text
        binding.welcomeTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START


        return binding.root
    }

}