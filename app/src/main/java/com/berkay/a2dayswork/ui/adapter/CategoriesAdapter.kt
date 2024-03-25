package com.berkay.a2dayswork.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.databinding.CardDesignBinding
import com.berkay.a2dayswork.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class CategoriesAdapter(var mContext: Context, var categoryList:MutableList<CMaker>, var viewModel: MainViewModel)
    :RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

        inner class ViewHolder(var binding:CardDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryy = categoryList.get(position)
        val design = holder.binding
        design.cardTextView.text = categoryy.name

        design.cardViewRow.setOnLongClickListener{
            val builder = AlertDialog.Builder(this.mContext)
            builder.setTitle("Edit Category")

            val layout = LinearLayout(this.mContext)
            layout.orientation = LinearLayout.VERTICAL

            val category = EditText(this.mContext)
            category.setText(categoryy.name)

            val inputRoutineLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            category.layoutParams = inputRoutineLayoutParams
            layout.addView(category)

            builder.setView(layout)

            builder.setPositiveButton("Update") { _, _ ->

                viewModel.update(categoryy.id, category.text.toString())
            }
            builder.setNegativeButton("Delete") { dialog, _ ->
                viewModel.delete(categoryy.id)
                dialog.cancel()
            }
            builder.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}