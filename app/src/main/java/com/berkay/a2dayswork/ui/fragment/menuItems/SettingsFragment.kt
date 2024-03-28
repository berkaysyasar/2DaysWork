package com.berkay.a2dayswork.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentRoutineBinding
import com.berkay.a2dayswork.databinding.FragmentSettingsBinding
import com.berkay.a2dayswork.ui.MainActivity


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

        // Not switch'in son durumunu al
        val isNoteSwitchChecked = sharedPreferences.getBoolean("noteswitch", true)
        val isRoutineSwitchChecked = sharedPreferences.getBoolean("routineswitch", true)
        val isEnabled = sharedPreferences.getBoolean("enabled", false)



        binding.noteswitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("noteswitch", isChecked).apply()
        }

        binding.routineswitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("routineswitch", isChecked).apply()
        }

        binding.enabledisablebutton.setOnClickListener{
            binding.noteaddTextview.visibility = if (binding.noteaddTextview.visibility == View.VISIBLE) {
                View.INVISIBLE // Görünürse, görünürlüğünü kaldır
            } else {
                View.VISIBLE // Aksi takdirde görünür hale getir
            }

            // Rutin ekleme View'lerinin görünürlüğünü tersine çevir
            binding.routineaddTextview.visibility = if (binding.routineaddTextview.visibility == View.VISIBLE) {
                View.INVISIBLE // Görünürse, görünürlüğünü kaldır
            } else {
                View.VISIBLE // Aksi takdirde görünür hale getir
            }

            // Not anahtarının görünürlüğünü tersine çevir
            binding.noteswitch.visibility = if (binding.noteswitch.visibility == View.VISIBLE) {
                View.INVISIBLE // Görünürse, görünürlüğünü kaldır
            } else {
                View.VISIBLE // Aksi takdirde görünür hale getir
            }

            // Rutin anahtarının görünürlüğünü tersine çevir
            binding.routineswitch.visibility = if (binding.routineswitch.visibility == View.VISIBLE) {
                View.INVISIBLE // Görünürse, görünürlüğünü kaldır
            } else {
                View.VISIBLE // Aksi takdirde görünür hale getir
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