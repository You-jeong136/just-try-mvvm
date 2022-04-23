package com.study.aos.mvvm.presentation

import androidx.lifecycle.*
import com.study.aos.mvvm.data.repository.RealDiariesRepository
import com.study.aos.mvvm.domain.Diary
import kotlinx.coroutines.launch

class DiariesViewModel(
    private val diariesRepository: RealDiariesRepository,
) : ViewModel() {

    // viewmodel에서 android context를 어떻게 써야하나.  _ compaion object에 받아오면 절대 안됨!!!!
    // android viewmodel을 때문에 구글에서 만들어둠. (이것도 안티패턴...)
    // byViewModels()에서 알아서 application을 찾아서 불러줄 것임.

    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries : LiveData<List<Diary>> = _diaries

    init {
        loadDiaries()

    }
    fun loadDiaries() = viewModelScope.launch {
        /*    //local용
           _diaries.value = diariesDao.getAllDiaries()
                .map{ Diary(it.id, it.title, it.content, it.createDate )}
        // remote 용
        val response = dailyDiaryService.getAllDiaries()
        if(response.isSuccessful){
            val diaries = response.body().orEmpty()
                .map{
                    Diary(it.id, it.title, it.content, Date(it.created) )
                }
            _diaries.value = diaries
        } //실패 시 처리 따로.*/
        diariesRepository.getAllDiaries()
            .onSuccess {  }
            .onFailure {  }

    }

}