package com.berkay.a2dayswork.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentReminderBinding


class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReminderBinding.inflate(inflater, container, false)

        binding.reminderButton.setOnClickListener{
            showRemindDialog()
        }


        return binding.root
    }
    private fun showRemindDialog() {
        val builder = AlertDialog.Builder(requireContext()) 

    }

}