package com.study.aos.mvvm.data.remote

import com.study.aos.mvvm.data.remote.parameters.SaveDairyParams
import com.study.aos.mvvm.data.remote.response.DailyResponse
import com.study.aos.mvvm.domain.Diary
import java.util.*

class RemoteDiairesMapper {
    fun toSaveDiairyParams(diary : Diary) : SaveDairyParams = SaveDairyParams(
        id = diary.id,
        title = diary.title,
        content = diary.content,
        createdAt = diary.created.time,
    )

    fun toDiary(diaryResponse : DailyResponse) : Diary = Diary(
        id = diaryResponse.id,
        title = diaryResponse.title,
        content = diaryResponse.content,
        created = Date(diaryResponse.created),
    )
}