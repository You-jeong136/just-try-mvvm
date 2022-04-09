package com.study.aos.mvvm.domain

import java.util.*

data class Diary(
    val id : String,
    val title : String,
    val content : String,
    val created : Date
){
    companion object {
        fun createEmpty(
            id: String = UUID.randomUUID().toString(),
            createDate: Date = Date(),
        ): Diary = Diary(
            id,
            "",
            "",
            createDate,
        )
    }
}
