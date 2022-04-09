package com.study.aos.mvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.study.aos.mvvm.data.entity.DiaryEntity

@Dao
interface DiariesDao {

    @Query("SELECT * FROM DiaryEntity")
    suspend fun getAllDiaries() : List<DiaryEntity>

    @Query("SELECT * FROM DiaryEntity WHERE id = :diaryId")
    suspend fun getDiary(diaryId : String): DiaryEntity?

    //db 시간이 오래걸리면 null 일 수도.. 조금 주의 _
    // liveData로 구독 시 계속 최신 데이터가 들어옴. <- 그리고 room이 이를 인식 알아서 바뀜 + 알아서 뷰가 바뀜. ( + 수정이나 삭제 시...)
    // 일기장 목록 변경 시 감지를 해서 _ livedata에 넘겨줌. 갱신!.

/*
    @Query("SELECT * FROM DiaryEntity")
    suspend fun getAllDiaries() : LiveData<List<DiaryEntity>>
*/

   /* @Query("SELECT * FROM DiaryEntity WHERE id = :diaryId")
    fun getDiary(diaryId : String): LiveData<DiaryEntity>*/

    @Insert
    fun insertDiary(diaryEntity: DiaryEntity)


}
