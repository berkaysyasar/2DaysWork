package com.berkay.a2dayswork.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.FragmentRoutineBinding
import com.berkay.a2dayswork.databinding.FragmentSettingsBinding
import com.berkay.a2dayswork.ui.MainActivity


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

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


        return binding.root
    }

}