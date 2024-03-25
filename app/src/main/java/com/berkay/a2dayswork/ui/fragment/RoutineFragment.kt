package com.berkay.a2dayswork.ui.fragment

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.ui.adapter.RoutineAdapter
import com.berkay.a2dayswork.data.entity.RMaker
import com.berkay.a2dayswork.databinding.FragmentRoutineBinding
import com.berkay.a2dayswork.ui.viewmodel.MainViewModel
import com.berkay.a2dayswork.ui.viewmodel.RoutineViewModel
import com.google.android.material.internal.ViewUtils.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class RoutineFragment : Fragment() {
    private lateinit var binding: FragmentRoutineBinding
    private lateinit var viewModel : RoutineViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoutineBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        viewModel.routineList.observe(viewLifecycleOwner){
            val routineAdapter = RoutineAdapter(requireContext(),it,viewModel)
            binding.routineRecyclerView.adapter = routineAdapter
        }

        binding.routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.addRoutineButton.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Routine")

            val layout = LinearLayout(requireContext())
            layout.orientation = LinearLayout.VERTICAL

            val inputRoutine = EditText(requireContext())
            inputRoutine.hint = "Routine Name"
            val inputRoutineLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            inputRoutine.layoutParams = inputRoutineLayoutParams
            layout.addView(inputRoutine)

            val inputTime = EditText(requireContext())
            inputTime.hint = "Time"
            inputTime.isFocusable = false


            inputTime.setOnClickListener {
                showTimePickerDialog(inputTime)
            }
            layout.addView(inputTime)

            builder.setView(layout)

            builder.setPositiveButton("Add") { _, _ ->
                val name = capitalizeFirstLetter(inputRoutine.text.toString())
                val time = inputTime.text.toString()
                viewModel.save(name,time)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

        }

        return binding.root
    }


    private fun showTimePickerDialog(inputTime: EditText) {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                inputTime.setText(formattedTime)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun capitalizeFirstLetter(input: String): String {
        return input.substring(0, 1).toUpperCase() + input.substring(1)
    }

}