package com.berkay.a2dayswork.ui.adapter

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.berkay.a2dayswork.databinding.RoutineDesignBinding
import com.berkay.a2dayswork.ui.viewmodel.RoutineViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Locale


class RoutineAdapter(var mContext:Context,
                     var routineList: MutableList<RMaker>,
                     var viewModel: RoutineViewModel) :
        RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: RoutineDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RoutineDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
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
            layout.addView(category)

            val inputTime = EditText(this.mContext)
            inputTime.setText(routines.routinetime)
            inputTime.isFocusable = false


            inputTime.setOnClickListener {
                showTimePickerDialog(inputTime)
            }
            layout.addView(inputTime)
            builder.setView(layout)

            builder.setPositiveButton("Update") { _, _ ->
                viewModel.update(routines.id, category.text.toString(), inputTime.text.toString(), 0)

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
                viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone)
                notifyItemChanged(position)
            } else {
                Snackbar.make(design.root,"Routine is already done. Are you sure you want to mark it as undone?", Snackbar.LENGTH_LONG).setAction("Undo"){
                    routines.isDone = 0
                    viewModel.update(routines.id, routines.routinename, routines.routinetime, routines.isDone)
                    notifyItemChanged(position)
                }.show()
            }
        }
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
