package ru.shumikhin.todoshnik.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {

    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    private fun stringToDate(str: String): Date {
        val res = formatter.parse(str)
        return res
    }

    private fun dateToString(date: Date): String{
        return formatter.format(date)
    }

    private fun timestampToDate(time: Long): Date {
        val date = formatter.format(time)
        return stringToDate(date)
    }

    fun timestampToString(time: Long): String{
        return dateToString(timestampToDate(time))
    }
}