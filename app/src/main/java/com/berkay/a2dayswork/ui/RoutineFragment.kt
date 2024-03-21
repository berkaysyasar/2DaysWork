package com.berkay.a2dayswork.ui

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.adapter.RoutineAdapter
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.data.entity.RMaker
import com.berkay.a2dayswork.databinding.FragmentMainBinding
import com.berkay.a2dayswork.databinding.FragmentRoutineBinding
import com.google.android.material.internal.ViewUtils.dpToPx
import java.util.Calendar
import java.util.Locale


class RoutineFragment : Fragment() {
    private lateinit var binding: FragmentRoutineBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter
    private val routineCategories = mutableListOf<RMaker>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoutineBinding.inflate(inflater, container, false)


        binding.addRoutineButton.setOnClickListener{
            AddRoutineDialog()
        }

        recyclerView = binding.routineRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        routineAdapter = RoutineAdapter(routineCategories)
        recyclerView.adapter = routineAdapter

        return binding.root
    }

    private fun AddRoutineDialog() {
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
        inputRoutineLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        inputRoutine.layoutParams = inputRoutineLayoutParams
        layout.addView(inputRoutine)

        val inputTime = EditText(requireContext())
        inputTime.hint = "Time"
        inputTime.isFocusable = false
        val inputTimeLayoutParams = LinearLayout.LayoutParams(
            dpToPx(55, requireContext()), // Genişliği 50dp olarak ayarla
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputTimeLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        inputTime.layoutParams = inputTimeLayoutParams
        inputTime.setOnClickListener {
            showTimePickerDialog(inputTime)
        }
        layout.addView(inputTime)

        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val routineName = inputRoutine.text.toString()
            val routineTime = inputTime.text.toString()

            if (routineName.length in 2..40 && !routineTime.isEmpty()){
                val formattedRoutineName = capitalizeFirstLetter(routineName)
                routineCategories.add(RMaker(routineName = formattedRoutineName, routineTime = routineTime))
                routineAdapter.notifyDataSetChanged() // Adapter'a yeni veri eklendiğinde güncelleme yapılır
            } else {
                Toast.makeText(
                    requireContext(),
                    "The category name must be between 2 and 40 characters and don't forget to set time. ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }


    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
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