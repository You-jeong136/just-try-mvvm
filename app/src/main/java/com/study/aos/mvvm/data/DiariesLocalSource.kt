package com.study.aos.mvvm.data

import com.study.aos.mvvm.data.local.dao.DiariesDao
import com.study.aos.mvvm.data.local.entity.DiaryEntity
import com.study.aos.mvvm.data.local.executor.DispatchExecutors
import com.study.aos.mvvm.domain.Diary

class DiariesLocalSource (
    private val diariesDao : DiariesDao,
    private val dispatchExecutors: DispatchExecutors = DispatchExecutors.getInstance()
){
    /*fun getDiary(
        diaryId : String, onSuccess : (Diary?) -> Unit, onFailure : (Throwable) -> Unit,
    ) {
        dispatchExecutors.ioThread.execute {
            //바로 return 하면 될 것 같지만, 메인 이외 스레드에서 return 불가능
            //-> 따라서 콜백을 사용해서 해야함. (성공 / 실패 시 값을 넘겨주기 위해)
            try {
                val diaryEntity = diariesDao.getDiary(diaryId)
                *//*
                val diary = diaryEntity.takeIf { it == null }
                    ?.let {
                        Diary(
                            id = it.id,
                            title = it.title,
                            content = it.content,
                            created = it.createDate
                        )
                    }
                *//*
                dispatchExecutors.mainThread.execute {
                    onSuccess(diaryEntity.toDiary())
                }
            } catch (e: Exception) {
                onFailure(e)
            }

        }
    }*/
    /*fun saveDiary(
        diary : Diary,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        dispatchExecutors.ioThread.execute{
            try{
                diariesDao.insertDiary(diary.toDiaryEntity())

                dispatchExecutors.mainThread.execute{
                    onSuccess()
                }
            }catch (e : Exception){
                dispatchExecutors.mainThread.execute{
                    onFailure(e)
                }
            }
        }
    }*/
    //db의 실행은 메인이 아닌 스레드에서 수행. 결과값은 메인에서 실행해야한다.
    //에러 핸들링은은 최대한 상세하게 해야 좋다. catch(e : Exception)은 해선 안되는 코드...

   /* fun getDiary(
        diaryId: String,
        onResult : (Result<Diary?>)-> Unit,
    ){
        //runCatching : result를 return함._정확히는 Result<Diary?> (이는 onSucess와 onFailure를 하나로 합쳤다 생각하면 됨.)

        dispatchExecutors.ioThread.execute{
            val result = runCatching { diariesDao.getDiary(diaryId)  }
                .map{ it.toDiary()}
                .also{
                    dispatchExecutors.mainThread.execute{ onResult(it) }
                }


              *//*  .onSuccess { entitiy : DiaryEntity? ->
                    //성공시 결과물은 메인에서..
                    dispatchExecutors.mainThread.execute{

                    }

                }
                .onFailure { exception ->

                }*//*
        }


    }*/


    private fun Diary.toDiaryEntity(): DiaryEntity = DiaryEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        createDate = this.created,
    )

    private fun DiaryEntity?.toDiary(): Diary?{
        return Diary(
            id = this?.id ?: return null,
            title = this.title,
            content = this.content,
            created = this.createDate,
        )
    }

}