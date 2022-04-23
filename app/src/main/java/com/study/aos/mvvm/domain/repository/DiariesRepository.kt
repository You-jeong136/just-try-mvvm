package com.study.aos.mvvm.domain.repository

import com.study.aos.mvvm.domain.Diary

interface DiariesRepository {
    suspend fun getAllDiaries() : Result<List<Diary>>

    suspend fun getDiary(diaryId : String) : Result<Diary>

    suspend fun saveDiary(diary : Diary) : Result<Unit>
}

//왜 레포를 interface로 만들까.