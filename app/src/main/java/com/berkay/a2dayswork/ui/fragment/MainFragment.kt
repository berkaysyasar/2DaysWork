package com.berkay.a2dayswork.ui.fragment

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.ui.adapter.CategoriesAdapter
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.databinding.FragmentMainBinding
import com.berkay.a2dayswork.ui.adapter.RoutineAdapter
import com.berkay.a2dayswork.ui.viewmodel.MainViewModel
import com.berkay.a2dayswork.ui.viewmodel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import com.berkay.a2dayswork.utils.Utils.capitalizeFirstLetter
import com.berkay.a2dayswork.utils.Utils.dpToPx


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.categorList.observe(viewLifecycleOwner){
            val categoryAdapter = CategoriesAdapter(requireContext(),it,viewModel)
            binding.categoriesRecyclerView.adapter = categoryAdapter
        }

        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addButton.setOnClickListener{
            categoryAdd()
        }
        val args = arguments
        val noteSwitchValue = args?.getInt("noteswitch")
        if (noteSwitchValue == null) {
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val isNoteSwitchChecked = sharedPreferences.getBoolean("noteswitch", true)
            binding.addButton.visibility = if (isNoteSwitchChecked) View.VISIBLE else View.INVISIBLE
        } else {
            // Eğer Bundle'dan değer alınabilirse, bu değere göre işlem yap
            binding.addButton.visibility = if (noteSwitchValue == 1) View.VISIBLE else View.INVISIBLE
        }
        return binding.root
    }
    private fun categoryAdd(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Category")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL

        val category = EditText(requireContext())
        category.hint = "Category Name"
        val inputRoutineLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        category.layoutParams = inputRoutineLayoutParams
        inputRoutineLayoutParams.setMargins(dpToPx(20, requireContext()), 0, dpToPx(20, requireContext()), 0)
        layout.addView(category)

        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val name = capitalizeFirstLetter(category.text.toString())
            viewModel.save(name)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}

