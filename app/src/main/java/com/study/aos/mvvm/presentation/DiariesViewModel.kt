package com.study.aos.mvvm.presentation

import android.app.Application
import androidx.lifecycle.*
import com.study.aos.mvvm.data.DiaryMemory
import com.study.aos.mvvm.data.dao.DiariesDao
import com.study.aos.mvvm.data.db.DailyDiaryDataBase
import com.study.aos.mvvm.data.entity.DiaryEntity
import com.study.aos.mvvm.domain.Diary
import kotlinx.coroutines.launch

class DiariesViewModel(application : Application) : AndroidViewModel(application) {

    // viewmodel에서 android context를 어떻게 써야하나.  _ compaion object에 받아오면 절대 안됨!!!!
    // android viewmodel을 때문에 구글에서 만들어둠. (이것도 안티패턴...)
    // byViewModels()에서 알아서 application을 찾아서 불러줄 것임.

    private val diariesDao : DiariesDao = DailyDiaryDataBase.getInstance(application).getDiariesDao()
    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries : LiveData<List<Diary>> = _diaries

    init {
        //_diaries.value = listOf()
        loadDiaries()

    }
    fun loadDiaries() {
        viewModelScope.launch {
            _diaries.value = diariesDao.getAllDiaries()
                .map{ Diary(it.id, it.title, it.content, it.createDate )}
        }
    }



/*    override fun onCleared() {
        super.onCleared()
        //do something
    }
*/
}