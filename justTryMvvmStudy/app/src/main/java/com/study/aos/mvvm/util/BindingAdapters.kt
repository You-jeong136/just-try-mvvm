package com.study.aos.mvvm.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:createdDate")
    fun bindMemoCreatedDate(textView: TextView, date: Date?) {
        if (date == null) return
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        textView.text = simpleDateFormat.format(date)
    }
}