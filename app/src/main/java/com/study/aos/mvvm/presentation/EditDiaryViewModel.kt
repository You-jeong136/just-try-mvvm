package com.study.aos.mvvm.presentation

import androidx.lifecycle.*
import com.study.aos.mvvm.domain.Diary
import com.study.aos.mvvm.domain.repository.DiariesRepository
import com.study.aos.mvvm.util.SingleLiveEvent
import kotlinx.coroutines.launch

class EditDiaryViewModel (
    private val diariesRepository: DiariesRepository
) : ViewModel() {

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    val editSuccessEvent = SingleLiveEvent<Unit>()

    //코루틴에서  viewmodelScope는 기본적으로 main에서 돌아가며, viewmodel이 죽으면 viewmodel도 죽는다.
    fun loadDiary(diaryId : String? = null) = viewModelScope.launch {
       /* val diary = loadLocalDiary(diaryId ?: return@launch)
            ?: loadRemoteDiary(diaryId)*/

        diariesRepository.getDiary(diaryId ?: return@launch)
            .onSuccess {
                _diary.value = it
                title.value = it.title
                content.value = it.content
            }
            .onFailure{
                //처리하기.
                throw it
            }

    }


    fun saveDiary() = viewModelScope.launch {
        val diary = Diary(
            id = _diary.value?.id ?: error(""),
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            created = _diary.value?.created ?: error(""),
        )

        diariesRepository.saveDiary(diary)
            .onSuccess{ editSuccessEvent.value = Unit}
            .onFailure{ }
    }

    //retrofit converter -> json moshi, .... 등 다양하게 있다. multipart 등 .
}