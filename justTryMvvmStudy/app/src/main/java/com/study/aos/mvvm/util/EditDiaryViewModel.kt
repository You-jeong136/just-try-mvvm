package com.study.aos.mvvm.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.aos.mvvm.data.DiaryMemory
import com.study.aos.mvvm.domain.Diary
import java.lang.IllegalStateException
import java.util.*

class EditDiaryViewModel :ViewModel() {
    //양방향 데이터 바인딩을 써보자.

    //제목
      val title = MutableLiveData<String>("")
//    val title : LiveData<String> = _title

    //내용
     val content = MutableLiveData<String>("")

    //쓴날짜
    private val _createDate = MutableLiveData<Date>(Date())
    val createDate : LiveData<Date> = _createDate

    fun loadDiary(diaryId : String?){
        val diary = DiaryMemory.getDiary(diaryId ?: return)

        title.value = diary.title
        content.value = diary.content
        _createDate.value = diary.created
    }

    fun saveDiary() {
        val title = this.title.value.orEmpty()
        val content = this.content.value.orEmpty()
        val createDate = this.createDate.value ?: throw IllegalStateException("create date cam...")

        val diary = Diary(
            title = title,
            content = content,
            created = createDate,
            id = UUID.randomUUID().toString()
        )
        DiaryMemory.saveDiary(diary)
    }



    //companion object
    //템플릿..~
    //toast 범위 지정.
}