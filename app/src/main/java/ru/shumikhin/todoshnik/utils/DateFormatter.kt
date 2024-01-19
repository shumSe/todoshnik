package ru.shumikhin.todoshnik.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter {

    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun stringToDate(str: String): Date {
        val res = formatter.parse(str)
        return res
    }

    fun dateToString(date: Date): String{
        return formatter.format(date)
    }

}