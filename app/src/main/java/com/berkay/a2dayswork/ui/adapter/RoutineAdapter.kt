package com.berkay.a2dayswork.ui.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.data.entity.RMaker
import com.berkay.a2dayswork.data.workmanager.NotificationReceiver
import com.berkay.a2dayswork.databinding.DesignRoutineBinding
import com.berkay.a2dayswork.ui.viewmodel.RoutineViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Locale


class RoutineAdapter(var mContext:Context,
                     var routineList: MutableList<RMaker>,
                     var viewModel: RoutineViewModel) :
        RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: DesignRoutineBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DesignRoutineBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routines = routineList[position]
        val design = holder.binding

        design.routineCardView.setOnLongClickListener{
            val builder = AlertDialog.Builder(this.mContext)
            builder.setTitle("Edit Routine")

            val layout = LinearLayout(this.mContext)
            layout.orientation = LinearLayout.VERTICAL

            val category = EditText(this.mContext)
            category.setText(routines.routinename)

            val inputRoutineLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            category.layoutParams = inputRoutineLayoutParams
            inputRoutineLayoutParams.setMargins(dpToPx(20, this.mContext), 0, dpToPx(20, this.mContext), 0)

            val inputTime = EditText(this.mContext)
            inputTime.setText(routines.routinetime)
            inputTime.isFocusable = false

            val inputTimeLayoutParams = LinearLayout.LayoutParams(
                dpToPx(55, this.mContext), // Genişliği 50dp olarak ayarla
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            inputTimeLayoutParams.setMargins(dpToPx(20, this.mContext), 0, dpToPx(20, this.mContext), 0)
            inputTime.layoutParams = inputTimeLayoutParams

            val remindCheckBox = CheckBox(this.mContext)
            remindCheckBox.setText("Warn 15 minutes before")
            if(routines.isNotificationEnabled == 1){
                remindCheckBox.isChecked = true
            }else{
                remindCheckBox.isChecked = false
            }


            inputTime.setOnClickListener {
                showTimePickerDialog(inputTime)
            }
            builder.setView(layout)

            layout.addView(category)
            layout.addView(inputTime)
            layout.addView(remindCheckBox)

            builder.setPositiveButton("Update") { _, _ ->
                viewModel.update(routines.id, category.text.toString(), inputTime.text.toString(), 0, isnotificationenabled = if(remindCheckBox.isChecked) 1 else 0)

            }
            builder.setNegativeButton("Delete") { dialog, _ ->
                viewModel.delete(routines.id)
                dialog.cancel()
            }
            builder.show()
            true
        }

        design.routineTextView.text = routines.routinename
        design.routinetimeTextView.text = routines.routinetime

        if (routines.isDone == 1) {
            design.rotdesignconstraintlayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green))
        } else{
            design.rotdesignconstraintlayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.maincolor))
        }

        design.routineimageView.setOnClickListener {
            if (routines.isDone == 0){
                routines.isDone = 1
                viewModel.markasdone(routines.id)
                viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone,routines.isNotificationEnabled)
                notifyItemChanged(position)
            } else {
                Snackbar.make(design.root,"Routine is already done. Are you sure you want to mark it as undone?", Snackbar.LENGTH_LONG).setAction("Undo"){
                    routines.isDone = 0
                    viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone,routines.isNotificationEnabled)
                    notifyItemChanged(position)
                }.show()
            }
            design.routineimageView.setOnClickListener {
                if (routines.isDone == 0){
                    routines.isDone = 1
                    viewModel.markasdone(routines.id)
                    viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone,routines.isNotificationEnabled)
                    notifyItemChanged(position)
                } else {
                    Snackbar.make(design.root,"Routine is already done. Are you sure you want to mark it as undone?", Snackbar.LENGTH_LONG).setAction("Undo"){
                        routines.isDone = 0
                        viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone,routines.isNotificationEnabled)
                        notifyItemChanged(position)
                    }
                }

                // Alarmı güncelle
                val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(mContext, NotificationReceiver::class.java)
                intent.putExtra("routineName", routines.routinename)
                val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                if (routines.isDone == 1) {
                    // Eğer isDone değeri 1 ise, alarmı iptal et
                    alarmManager.cancel(pendingIntent)
                } else {
                    // Eğer isDone değeri 0 ise, alarmı ayarla
                    val timeParts = routines.routinetime.split(":")
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
    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
    private fun showTimePickerDialog(inputTime: EditText) {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this.mContext,
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
}
