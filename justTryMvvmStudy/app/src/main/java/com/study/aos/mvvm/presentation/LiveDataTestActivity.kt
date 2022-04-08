package com.study.aos.mvvm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.study.aos.mvvm.databinding.ActivityLiveDataTestBinding

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLiveDataTestBinding
    //private val liveDataTest = LiveDataViewModel()
    private val liveDataViewModel : LiveDataViewModel by viewModels()

    /*private val factory = object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass == LiveDataViewModel::class.java){
                return LiveDataViewModel(
                    //~~~~
                )as T
            }
        }
        을 해서 by viewModels { factory }에 넣어줌. factory에 대해서는 따로 더 공부해보는거 추천.
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        binding.liveDataTest = liveDataViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
/*
        //type을 몰르기때문에. <부모는 자식을 모른다. 자식만 알뿐... 그래서 type을 모르기 때문에 아이디 등을 받아와서 하는 것.
        val binding = DataBindingUtil.inflate<ActivityLiveDataTestBinding>(
            layoutInflater, R.layout.activity_live_data_test, null, false)
*/
       /* liveDataTest.count.observe(this){
            Log.d("live_Data_cnt", it.toString())
        }*/

        //뷰모델은 뷰에 대한 상태를 가진것. (뷰는 모델을 가지면 안된다. mvc의 고질적 문제. 하지만 뷰를 갱신할려면 데이터는 필요함.
        //때문에 뷰를 위한 상태, 모델을 가진 그 중간 쯤 위치한 뷰모델을 만듬.
        //근데 뷰모델은 뷰에 대한 상태만 알고 있음.


        /*liveDataViewModel.cnt.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback {

        })*/
    }
}