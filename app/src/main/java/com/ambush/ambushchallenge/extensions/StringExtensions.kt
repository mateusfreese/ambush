package com.ambush.ambushchallenge.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.toFormattedDateTime(): String {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).let { format ->
        return DateFormat.getDateInstance().format(format.parse(this.orEmpty()) ?: "")
    }
}
