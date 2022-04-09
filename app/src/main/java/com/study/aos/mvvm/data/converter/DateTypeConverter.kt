package com.study.aos.mvvm.data.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun fromDate(date : Date?) : Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisecond : Long?) : Date? {
        return Date(millisecond ?: return null)
    }
}