package com.berkay.a2dayswork.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.data.entity.NMaker
import com.berkay.a2dayswork.databinding.DesignReminderBinding
import com.berkay.a2dayswork.ui.viewmodel.ReminderViewModel

class ReminderAdapter(var mContext: Context,
                      var reminderList:MutableList<NMaker>,
                      var viewModel: ReminderViewModel) :
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: DesignReminderBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderAdapter.ViewHolder {
        val binding = DesignReminderBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderAdapter.ViewHolder, position: Int) {
        val reminder = reminderList[position]
        val design = holder.binding

        design.ReminderTextView.text = reminder.reminderName
        design.reminderDateTextView.text = reminder.reminderDate
        design.reminderTimeTextView.text = reminder.reminderTime
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

}