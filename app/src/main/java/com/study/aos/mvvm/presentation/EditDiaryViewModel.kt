package com.study.aos.mvvm.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.aos.mvvm.data.dao.DiariesDao
import com.study.aos.mvvm.data.db.DailyDiaryDataBase
import com.study.aos.mvvm.data.entity.DiaryEntity
import com.study.aos.mvvm.data.remote.parameters.SaveDairyParams
import com.study.aos.mvvm.data.remote.service.DailyDiaryService
import com.study.aos.mvvm.domain.Diary
import com.study.aos.mvvm.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class EditDiaryViewModel (application : Application) : AndroidViewModel(application) {

    private val diariesDao : DiariesDao = DailyDiaryDataBase.getInstance(application).getDiariesDao()

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    val editSuccessEvent = SingleLiveEvent<Unit>()
    private val dailyDiaryService : DailyDiaryService = DailyDiaryService.getInstance()

    //코루틴에서  viewmodelScope는 기본적으로 main에서 돌아가며, viewmodel이 죽으면 viewmodel도 죽는다.
    fun loadDiary(diaryId : String? = null) = viewModelScope.launch {
        val diary = loadLocalDiary(diaryId ?: return@launch)
            ?: loadRemoteDiary(diaryId)

        _diary.value = diary
        title.value = diary?.title
        content.value = diary?.content
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

    fun saveDiary() = viewModelScope.launch {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        saveDiaryLocal(previousDiary)
        saveDiaryRemote(previousDiary)
        editSuccessEvent.value = Unit
    }

    private suspend fun saveDiaryLocal(diary: Diary) {
        val diaryEntity = DiaryEntity(
            id = diary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createDate = diary.created,
        )
        diariesDao.insertDiary(diaryEntity)
    }

    private suspend fun saveDiaryRemote(diary: Diary) {
        val diaryParams = SaveDairyParams(
            id = diary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createdAt = diary.created.time,
        )

        if(dailyDiaryService.saveDiary(params = diaryParams).isSuccessful)
                Log.d("*************************save", "successful")
    }

    //retrofit converter -> json moshi, .... 등 다양하게 있다. multipart 등 .
}