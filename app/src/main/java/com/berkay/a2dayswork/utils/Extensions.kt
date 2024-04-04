package com.berkay.a2dayswork.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import java.util.Calendar
import java.util.Locale

fun Navigation.transition(it: View, id:Int){
    findNavController(it).navigate(id)
}
fun Navigation.transition(it: View, id: NavDirections){
    findNavController(it).navigate(id)
}

object Utils {
    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    fun showTimePickerDialog(context: Context, inputTime: EditText) {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
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

    fun datePickerDialog(context: Context, inputDate: EditText) {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth, selectedYear)
                inputDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
    fun capitalizeFirstLetter(str: String): String {
    return if (str.isEmpty()) {
        str
    } else {
        str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
    }
}
}