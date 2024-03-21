package com.berkay.a2dayswork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.data.entity.RMaker

class RoutineAdapter(private val routine : MutableList<RMaker>) : RecyclerView.Adapter<RoutineAdapter.RoutineHolder>() {

    inner class RoutineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineName : TextView = itemView.findViewById(R.id.routineTextView)
        val routineTime : TextView = itemView.findViewById(R.id.routinetimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_design, parent, false)
        return RoutineHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineHolder, position: Int) {
        val routineItem = routine[position]
        holder.routineName.text = routineItem.routineName
        holder.routineTime.text = routineItem.routineTime
    }

    override fun getItemCount(): Int {
        return routine.size
    }
}