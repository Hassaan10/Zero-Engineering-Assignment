package com.example.zeroengineeringassignment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return format.format(date)
}

fun convertTimeToLong(time: String): Long {
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return format.parse(time)?.time ?: 0
}