package com.berkay.a2dayswork.ui.fragment

import RoutineWorker
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkay.a2dayswork.data.workmanager.NotificationReceiver
import com.berkay.a2dayswork.ui.adapter.RoutineAdapter
import com.berkay.a2dayswork.databinding.FragmentRoutineBinding
import com.berkay.a2dayswork.ui.viewmodel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale
import com.berkay.a2dayswork.utils.Utils.dpToPx
import com.berkay.a2dayswork.utils.Utils.capitalizeFirstLetter
import com.berkay.a2dayswork.utils.Utils.showTimePickerDialog
import kotlinx.coroutines.launch


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
            addRoutine()
        }

        getargsfromSettings()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRoutines()
        viewModel.routineList.observe(viewLifecycleOwner) { routines ->
            val routineAdapter = RoutineAdapter(requireContext(), routines, viewModel)
            binding.routineRecyclerView.adapter = routineAdapter
        }
    }

    private fun getargsfromSettings(){
        val args = arguments
        val noteSwitchValue = args?.getInt("routineswitch")
        if (noteSwitchValue == null) {
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val isNoteSwitchChecked = sharedPreferences.getBoolean("routineswitch", true)
            binding.addRoutineButton.visibility = if (isNoteSwitchChecked) View.VISIBLE else View.INVISIBLE
        } else {
            // Eğer Bundle'dan değer alınabilirse, bu değere göre işlem yap
            binding.addRoutineButton.visibility = if (noteSwitchValue == 1) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun addRoutine(){
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
        inputRoutineLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)


        val inputTime = EditText(requireContext())
        inputTime.hint = "Time"
        inputTime.isFocusable = false

        val inputTimeLayoutParams = LinearLayout.LayoutParams(
            dpToPx(55, requireContext()), // Genişliği 50dp olarak ayarla
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputTimeLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        inputTime.layoutParams = inputTimeLayoutParams

        val remindCheckBox = CheckBox(requireContext())
        remindCheckBox.text = "Warn 15 minutes before"
        remindCheckBox.focusable = View.NOT_FOCUSABLE
        remindCheckBox.isClickable = false

        val remindCheckBoxLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        remindCheckBoxLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        remindCheckBox.layoutParams = remindCheckBoxLayoutParams

        inputTime.setOnClickListener {
            showTimePickerDialog(requireContext(),inputTime)
                remindCheckBox.focusable = View.FOCUSABLE
                remindCheckBox.isClickable = true
        }
        builder.setView(layout)

        layout.addView(inputRoutine)
        layout.addView(inputTime)
        layout.addView(remindCheckBox)

        builder.setPositiveButton("Add") { _, _ ->
            val inputText = inputRoutine.text.toString()
            val remindChecked = remindCheckBox.isChecked
            val name = capitalizeFirstLetter(inputText)
            val time = inputTime.text.toString()

            if (inputText.isNotEmpty() && name.length in 2..20){
                if(time.isNotEmpty() || !remindChecked){
                    viewModel.save(name, time, if (remindChecked) 1 else 0)
                    if(remindChecked){
                        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val intent = Intent(context, NotificationReceiver::class.java)
                        intent.putExtra("routineName", name)
                        lifecycleScope.launch {
                            val routineId = viewModel.getLastRoutineId()
                            val requestCode = if (routineId == -1) 0 else routineId
                            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)

                            val timeParts = time.split(":")
                            val routineHour = timeParts[0].toInt()
                            val routineMinute = timeParts[1].toInt()

                            val routineMinuteAdjusted = if(routineMinute < 15) routineMinute + 45 else routineMinute - 15
                            val routineHourAdjusted = if(routineMinute < 15) routineHour - 1 else routineHour

                            // Set the alarm to start at the routine time
                            val calendar: Calendar = Calendar.getInstance().apply {
                                timeInMillis = System.currentTimeMillis()
                                set(Calendar.HOUR_OF_DAY, routineHourAdjusted)
                                set(Calendar.MINUTE, routineMinuteAdjusted)
                            }
                            // setExactAndAllowWhileIdle to ensure precise delivery of the alarm
                            // even when the device is in low-power idle modes
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                        }
                    }
                }
            }
                else if (inputTime.text.isEmpty() && remindCheckBox.isChecked) {
                Toast.makeText(requireContext(), "You cannot check reminder if you dont use time", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(requireContext(), "Routine name cannot be empty and must be between 2-20 characters", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }


}