package com.study.aos.mvvm.data.local

import com.study.aos.mvvm.data.local.dao.DiariesDao
import com.study.aos.mvvm.data.local.entity.DiaryEntity
import com.study.aos.mvvm.data.local.mapper.LocalDiaryMapper
import com.study.aos.mvvm.data.local.mapper.toDiary
import com.study.aos.mvvm.data.source.DiariesSource
import com.study.aos.mvvm.domain.Diary
import java.lang.IllegalStateException

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
    private val localDiaryMapper: LocalDiaryMapper,
) : DiariesSource {
    override suspend fun getAllDiaries(): Result<List<Diary>> {
        return runCatching {
            diariesDao.getAllDiaries()
                .map { /*Diary(it.id, it.title, it.content, it.createDate)*/
                    localDiaryMapper.toDiary(it)
                }
        }
    }

    override suspend fun getDiary(diaryId: String): Result<Diary> {
        return runCatching {
            diariesDao.getDiary(diaryId)
                ?: throw IllegalStateException("cannot find diary of id : $diaryId")
        }.map { /*Diary(it.id, it.title, it.content, it.createDate)*/
                it.toDiary()
        }
    }

    override suspend fun save(diary: Diary): Result<Unit> {
        return runCatching {
            diariesDao.insertDiary( /*DiaryEntity(
                diary.title,
                diary.content,
                diary.created,
                diary.id
            )*/
            localDiaryMapper.toDiaryEntity(diary)
            )
        }
    }
}