package com.study.aos.mvvm.data.remote.parameters

import com.google.gson.annotations.SerializedName

data class SaveDairyParams(
    @SerializedName("diary_id") //여기는 gson에서 오는 변수명 그대로 써주기...
    val id : String,
    val title : String,
    val content : String,
    val createdAt : Long,
)
