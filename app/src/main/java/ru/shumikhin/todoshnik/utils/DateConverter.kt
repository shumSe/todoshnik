package ru.shumikhin.todoshnik.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {

    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun stringToDate(str: String): Date {
        val res = formatter.parse(str)
        return res
    }

    fun dateToString(date: Date): String{
        return formatter.format(date)
    }

    fun stringToTimestamp(str: String): Long{
        val date = formatter.parse(str)
        return date.time
    }

    fun timestampToDate(time: Long): Date {
        val date = formatter.format(time * 1000L)
        return stringToDate(date)
    }

    fun dateToTimestamp(date: Date): Long{
        return stringToTimestamp(dateToString(date))
    }
}