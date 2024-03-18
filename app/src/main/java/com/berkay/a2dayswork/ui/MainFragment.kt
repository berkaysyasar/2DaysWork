package com.berkay.a2dayswork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.adapter.CategoriesAdapter
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val categories = mutableListOf<CMaker>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.addButton.setOnClickListener{
            AddCategoryDialog()
        }

        binding.deleteButton.setOnClickListener{
            DeleteCategoryDialog()
        }

        categories.add(CMaker(categoryname = "Note"))

        recyclerView = binding.categoriesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        categoriesAdapter = CategoriesAdapter(categories)

        recyclerView.adapter = categoriesAdapter

        return binding.root
    }

    private fun AddCategoryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Category")

        val input = EditText(requireContext())
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val categoryName = input.text.toString()
            if (categoryName.length in 2..10) {
                val formattedCategoryName = capitalizeFirstLetter(categoryName)
                categories.add(CMaker(categoryname = formattedCategoryName))
                categoriesAdapter.notifyDataSetChanged() // Adapter'a yeni veri eklendiğinde güncelleme yapılır
            } else {
                Toast.makeText(
                    requireContext(),
                    "The category name must be between 2 and 10 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }


    private fun capitalizeFirstLetter(input: String): String {
        return input.substring(0, 1).toUpperCase() + input.substring(1)
    }


    private fun DeleteCategoryDialog(){

    }

    data class Categories(val category: String)
}