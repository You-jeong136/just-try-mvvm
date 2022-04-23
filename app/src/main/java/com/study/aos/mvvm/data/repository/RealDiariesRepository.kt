package com.study.aos.mvvm.data.repository

import com.study.aos.mvvm.data.DiariesLocalSource
import com.study.aos.mvvm.data.local.dao.DiariesDao
import com.study.aos.mvvm.data.local.entity.DiaryEntity
import com.study.aos.mvvm.data.remote.DiariesRemoteSource
import com.study.aos.mvvm.data.remote.parameters.SaveDairyParams
import com.study.aos.mvvm.data.remote.service.DailyDiaryService
import com.study.aos.mvvm.domain.Diary
import com.study.aos.mvvm.domain.repository.DiariesRepository
import java.util.*

class RealDiariesRepository (
  /*  private val diariesDao: DiariesDao,
    private val dailyDiaryService: DailyDiaryService,*/
    private val diariesLocalSource: DiariesLocalSource,
    private val diariesRemoteSource: DiariesRemoteSource
) :  DiariesRepository {

    override suspend fun getAllDiaries(): Result<List<Diary>> {
        /*val response = dailyDiaryService.getAllDiaries()
        if(response.isSuccessful) {
            val diaries = response.body().orEmpty()
                .map {
                    Diary(it.id, it.title, it.content, Date(it.created))
                }
            return Result.success(diaries)
        }
        return Result.failure(java.lang.IllegalStateException(response.message()))*/
        return diariesRemoteSource.getAllDiaries()
    }

    override suspend fun getDiary(diaryId: String): Result<Diary> {
        val localdiary = loadLocalDiary(diaryId)
            //?: loadRemoteDiary(diaryId)
        if(localdiary != null)
            return Result.success(localdiary)

        val remotediary = loadRemoteDiary(diaryId)
        if(remotediary == null){
            return Result.failure(IllegalStateException(" "))
        }
        return Result.success(remotediary)

    }

    private suspend fun loadLocalDiary(diaryId : String) : Diary?{
        return diariesDao.getDiary(diaryId)
            ?.let { Diary(it.id, it.title, it.content, it.createDate) }
    }

    private suspend fun loadRemoteDiary(diaryId: String) : Diary?{
        val response = dailyDiaryService.getDiary(diaryId)
        if(response.isSuccessful){
            return response.body()
                ?.let{ Diary(it.id, it.title, it.content, Date(it.created))}
        }
        return null
    }


    override suspend fun saveDiary(diary: Diary): Result<Unit> {
        saveDiaryLocal(diary)
        saveDiaryRemote(diary)
        return Result.success(Unit)
    }

    private suspend fun saveDiaryLocal(diary: Diary) {
        val diaryEntity = DiaryEntity(
            id = diary.id,
            title = diary.title,
            content = diary.content,
            createDate = diary.created,
        )
        diariesDao.insertDiary(diaryEntity)
    }

    private suspend fun saveDiaryRemote(diary: Diary) {
        val diaryParams = SaveDairyParams(
            title = diary.title,
            content = diary.content,
            createdAt = diary.created.time,
            id = diary.id,
        )
        dailyDiaryService.saveDiary(params = diaryParams)
    }

}