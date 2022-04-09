package com.study.aos.mvvm.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.aos.mvvm.data.dao.DiariesDao
import com.study.aos.mvvm.data.db.DailyDiaryDataBase
import com.study.aos.mvvm.data.entity.DiaryEntity
import com.study.aos.mvvm.data.remote.service.DailyDiaryService
import com.study.aos.mvvm.domain.Diary
import kotlinx.coroutines.launch

class EditDiaryViewModel (application : Application) : AndroidViewModel(application) {

    private val diariesDao : DiariesDao = DailyDiaryDataBase.getInstance(application).getDiariesDao()

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val dailyDiaryService : DailyDiaryService = DailyDiaryService.getInstance()

    fun loadDiary(diaryId : String? = null) = viewModelScope.launch {
        val diary = diariesDao.getDiary(diaryId ?: return@launch)
            ?.let { Diary(it.id, it.title, it.content, it.createDate) }
        _diary.value = diary
        title.value = diary?.title
        content.value = diary?.content
    }

    fun saveDiary() {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        val diaryEntity = DiaryEntity(
            id = previousDiary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createDate = previousDiary.created,
        )
        diariesDao.insertDiary(diaryEntity)
    }

    //retrofit converter -> json moshi, .... 등 다양하게 있다. multipart 등 .
}