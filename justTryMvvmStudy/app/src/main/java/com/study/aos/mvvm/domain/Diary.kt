package com.study.aos.mvvm.domain

import java.util.*

data class Diary(
    val id : String,
    val title : String,
    val content : String,
    val created : Date
)
