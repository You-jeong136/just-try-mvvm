package com.study.aos.mvvm.data.source

import com.study.aos.mvvm.domain.Diary

interface DiariesSource {
    suspend fun getAllDiaries() : Result<List<Diary>>
    suspend fun getDiary(diaryId : String) : Result<Diary>
    suspend fun save(diary : Diary) : Result<Unit>
}