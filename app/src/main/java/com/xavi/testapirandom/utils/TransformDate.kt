package com.xavi.testapirandom.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransformDate {
    companion object {
        fun transformDate(inputDateString: String, outputDateFormat: String): String {
            try {
                val inputFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat(outputDateFormat, Locale.getDefault())

                val date: Date = inputFormat.parse(inputDateString) ?: throw ParseException(
                    "Error parsing date",
                    0
                )
                return outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
        }
    }
}
