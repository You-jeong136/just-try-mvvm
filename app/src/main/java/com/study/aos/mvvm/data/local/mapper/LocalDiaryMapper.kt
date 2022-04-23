package com.study.aos.mvvm.data.local.mapper

import com.study.aos.mvvm.data.local.entity.DiaryEntity
import com.study.aos.mvvm.domain.Diary

class LocalDiaryMapper {
    fun toDiary(diaryEntity: DiaryEntity) : Diary = Diary(
        id = diaryEntity.id,
        title = diaryEntity.title,
        content = diaryEntity.content,
        created = diaryEntity.createDate
    )

    fun toDiaryEntity(diary: Diary) : DiaryEntity = DiaryEntity(
        id = diary.id,
        title = diary.title,
        content = diary.content,
        createDate = diary.created
    )
}

fun DiaryEntity.toDiary() : Diary = Diary(
    id = this.id,
    title = this.title,
    content = this.content,
    created = this.createDate
)

fun Diary.toDiaryEntity() : DiaryEntity = DiaryEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    createDate = this.created
)