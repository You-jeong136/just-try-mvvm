package com.study.aos.mvvm.data.remote.response

import java.util.*

data class DailyResponse (
    val id : String,
    val title : String,
    val content : String,
    val created : Long,
)/*{
    init {
        Date().time
    }
}*/