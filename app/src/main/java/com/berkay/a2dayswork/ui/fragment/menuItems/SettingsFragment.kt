package com.berkay.a2dayswork.ui.fragment.menuItems

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentSettingsBinding
import com.berkay.a2dayswork.ui.fragment.MainFragment
import com.berkay.a2dayswork.ui.fragment.RoutineFragment


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val constrainSet1 = ConstraintSet().apply { clone(context,R.layout.fragment_settings) }
        val constrainSet2 = ConstraintSet().apply { clone(context,R.layout.fragment_settings_alternative) }

        // Not switch'in son durumunu al
        val isNoteSwitchChecked = sharedPreferences.getBoolean("noteswitch", true)
        val isRoutineSwitchChecked = sharedPreferences.getBoolean("routineswitch", true)


        binding.noteswitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("noteswitch", isChecked).apply()
        }

        binding.routineswitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("routineswitch", isChecked).apply()
        }

        binding.enabledisablebutton.setOnClickListener{
    // Görünürlük durumunu değiştir
    binding.noteaddTextview.visibility = if (binding.noteaddTextview.visibility == View.INVISIBLE) {
        View.VISIBLE // Görünmezse, görünür hale getir
    } else {
        View.INVISIBLE // Aksi takdirde görünmez hale getir
    }

    binding.routineaddTextview.visibility = if (binding.routineaddTextview.visibility == View.INVISIBLE) {
        View.VISIBLE // Görünmezse, görünür hale getir
    } else {
        View.INVISIBLE // Aksi takdirde görünmez hale getir
    }

    binding.noteswitch.visibility = if (binding.noteswitch.visibility == View.INVISIBLE) {
        View.VISIBLE // Görünmezse, görünür hale getir
    } else {
        View.INVISIBLE // Aksi takdirde görünmez hale getir
    }

    binding.routineswitch.visibility = if (binding.routineswitch.visibility == View.INVISIBLE) {
        View.VISIBLE // Görünmezse, görünür hale getir
    } else {
        View.INVISIBLE // Aksi takdirde görünmez hale getir
    }

    // ConstraintSet'leri uygula
    if (binding.routineaddTextview.visibility == View.VISIBLE) {
        constrainSet2.applyTo(binding.root as ConstraintLayout)
    } else {
        constrainSet1.applyTo(binding.root as ConstraintLayout)
    }
}
        val bundle = Bundle()
        if(isNoteSwitchChecked.equals(true)) {
            binding.noteswitch.isChecked = true
            bundle.putInt("noteswitch", 1)
        }else{
            binding.noteswitch.isChecked = false
            bundle.putInt("noteswitch", 0)
        }
        if(isRoutineSwitchChecked.equals(true)) {
            binding.routineswitch.isChecked = true
            bundle.putInt("routineswitch", 1)
        }else{
            binding.routineswitch.isChecked = false
            bundle.putInt("routineswitch", 0)
        }

        val routineFragment = RoutineFragment()
        routineFragment.arguments = bundle

        // MainFragment'e geçiş yapmadan önce Bundle'ı ekleyin
        val mainFragment = MainFragment()
        mainFragment.arguments = bundle

        return binding.root
    }
}