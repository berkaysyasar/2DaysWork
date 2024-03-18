package com.berkay.a2dayswork.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.databinding.CardDesignBinding
import com.berkay.a2dayswork.ui.MainFragment
import com.berkay.a2dayswork.ui.SecondScreenFragment

class CategoriesAdapter(private val category: MutableList<CMaker>) :
        RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val categoryName: TextView = itemView.findViewById(R.id.textViewName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
            return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryItem = category[position]
        holder.categoryName.text = categoryItem.categoryname
    }
}