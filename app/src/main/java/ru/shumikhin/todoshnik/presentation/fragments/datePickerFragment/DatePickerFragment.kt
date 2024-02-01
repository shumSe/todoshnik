package ru.shumikhin.todoshnik.presentation.fragments.datePickerFragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragment.Companion.DATE_PICKER_EMPTY
import java.util.Calendar

class DatePickerFragment(
    private val date: Long
): DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val curTime = System.currentTimeMillis()

        calendar.timeInMillis = if(date != DATE_PICKER_EMPTY) date else curTime

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val res = DatePickerDialog(requireContext(),this, year, month, day)
        if(date != DATE_PICKER_EMPTY) {
            res.datePicker.minDate = if (curTime > date) date else curTime
        } else {
            res.datePicker.minDate = curTime
        }
        return res
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val bundle = Bundle()
        bundle.putLong("SELECTED_DATE", date)
        setFragmentResult("REQUEST_KEY", bundle)
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        val bundle = Bundle()
        bundle.putLong("SELECTED_DATE", calendar.timeInMillis)
        setFragmentResult("REQUEST_KEY", bundle)
    }

}