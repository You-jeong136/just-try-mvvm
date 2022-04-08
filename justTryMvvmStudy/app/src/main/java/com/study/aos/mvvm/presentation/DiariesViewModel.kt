package com.study.aos.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.aos.mvvm.data.DiaryMemory
import com.study.aos.mvvm.domain.Diary

class DiariesViewModel : ViewModel() {
    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries : LiveData<List<Diary>> = _diaries

/*    init {
        _diaries.value = listOf()
    }
*/
    fun loadDiaries() {
        _diaries.value = DiaryMemory.getAllDiaries()
    }

/*    override fun onCleared() {
        super.onCleared()
        //do something
    }
*/
}