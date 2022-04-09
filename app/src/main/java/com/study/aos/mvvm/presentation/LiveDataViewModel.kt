package com.study.aos.mvvm.presentation

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

//AAC의 viewmodel임을 어필하기 viewmodel()
class LiveDataViewModel : ViewModel() {

    //읽기쓰기용과 읽기용 2가지 존재
    /*val readWriteLiveData = MutableLiveData<String>()
    val readOnlyLiveData : LiveData<String>*/

    //읽기 쓰기가 외부에서도 가능하면 예기치 못한 상태 발생 가능이기에 private으로
    private val _count = MutableLiveData<Int>()
    val count : LiveData<Int> = _count //kotlin property

    //태초에 liveData 이전에 observale필드가 있었다.
    val cnt = ObservableInt()

    //내부에서만 변경가능하고, 외부에서는 변경이 불가능한 형태의 변수를 쓰임을 노출시키고 싶을때 변수명에 _(언더바)를 붙임.

    init {
     /*   _count.value = 89
        val count = _count.value*/

        //본래는 handler, handlerThread 등을 써야함. 아래는 야매코드
        //이때 liveData 값의 변경은 mainTread에서 해야함. 안하면 떠짐.
        Thread{
            while(true){
                Thread.sleep(1_000)
                val currentCnt = _count.value ?: 0
                cnt.set(cnt.get() + 1)
                //하지만 그냥 value가 아니라 postValue를 해주면 내부적으로main 찾아다줌.
                _count.postValue(currentCnt + 1)
            }
        }.start()
    }


}