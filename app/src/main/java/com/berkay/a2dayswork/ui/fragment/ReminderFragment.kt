package com.berkay.a2dayswork.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentReminderBinding
import com.berkay.a2dayswork.ui.adapter.ReminderAdapter
import com.berkay.a2dayswork.ui.viewmodel.ReminderViewModel
import com.berkay.a2dayswork.utils.Utils.dpToPx
import com.berkay.a2dayswork.utils.Utils.capitalizeFirstLetter
import com.berkay.a2dayswork.utils.Utils.showTimePickerDialog
import com.berkay.a2dayswork.utils.Utils.datePickerDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding
    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReminderBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        viewModel.reminderList.observe(viewLifecycleOwner){
            val reminderAdapter = ReminderAdapter(requireContext(), it, viewModel)
            binding.reminderRecyclerView.adapter = reminderAdapter
        }
        binding.reminderRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.reminderButton.setOnClickListener{
            showRemindDialog()
        }


        return binding.root
    }
    private fun showRemindDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Reminder")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL

        val inputReminder = EditText(requireContext())
        inputReminder.hint = "Reminder Note"
        val inputReminderLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        inputReminder.layoutParams = inputReminderLayoutParams
        inputReminderLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)

       val inputDate = EditText(requireContext())
        inputDate.hint = "Date"
        inputDate.isFocusable = false
        val inputDateLayoutParams = LinearLayout.LayoutParams(
            dpToPx(105, requireContext()), // Genişliği 50dp olarak ayarla
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputDateLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        inputDate.layoutParams = inputDateLayoutParams

       val inputTime = EditText(requireContext())
        inputTime.hint = "Time"
        inputTime.isFocusable = false
        val inputTimeLayoutParams = LinearLayout.LayoutParams(
            dpToPx(55, requireContext()), // Genişliği 50dp olarak ayarla
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputTimeLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        inputTime.layoutParams = inputTimeLayoutParams

        inputDate.setOnClickListener {
            datePickerDialog(requireContext(),inputDate)
        }

        inputTime.setOnClickListener {
            showTimePickerDialog(requireContext(),inputTime)
        }

        layout.addView(inputReminder)
        layout.addView(inputDate)
        layout.addView(inputTime)
        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val inputText = inputReminder.text.toString()
            val name = capitalizeFirstLetter(inputText)
            val date = inputDate.text.toString()
            val time = inputTime.text.toString()

            if(inputText.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()){
                viewModel.save(name, date, time)
            }else{
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

}