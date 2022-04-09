package com.study.aos.mvvm.data.remote.service

import com.study.aos.mvvm.data.remote.parameters.GetTokenParams
import com.study.aos.mvvm.data.remote.response.AuthTokenResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.POST

interface AuthService {

    @POST("/study/member")
    fun getToken(getTokenParams: GetTokenParams): Response<AuthTokenResponse>

    companion object {
        private var instance : AuthService? = null
        fun getInstance() : AuthService{
            return instance?: synchronized(this){
                Retrofit.Builder().baseUrl("http://52.42.22.72:7373")//https가 아님. _ 근데 android에서 보안 문제로 이 막음.
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create() //reified 어쩌구 덕분에 가능... _ 첫번째 create와 다른 점.
            }
        }
    }
}