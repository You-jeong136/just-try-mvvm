package com.study.aos.mvvm.data.remote.service

import com.study.aos.mvvm.data.remote.parameters.SaveDairyParams
import com.study.aos.mvvm.data.remote.response.DailyResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*

interface DailyDiaryService {

    @GET("/diary")
    suspend fun getAllDiaries(
        @Header(AUTHORIZATION) token  : String? = null,
    ) : Response<List<DailyResponse>>
    //okhttps를 가공한게 retrofit2 <- 즉 okhttp가 더 low level.
    //suspend 이전에 Call<>로 하는 것을 정확히 파악하는게 먼저이다.
    // Response를 감싸지 않고 DailyResponse를 바로 써줘도 돌아가지만, 이 경우 500이나 404 에러 등이 나올 경우 터짐.. 이를 trycatch를 일일이 하는 것은 좋은 코드라 볼 수 없음.

    @GET("/diary/{id}")
    suspend fun getDiary(
        @Path("id") diaryId : String,
    ) : Response<DailyResponse>

    @POST("/diary")
    suspend fun saveDiary(
        @Header(AUTHORIZATION) token: String? = null,
        @Body params: SaveDairyParams,
    ): Response<Unit>


    /* companion object {
         private var instance : DailyDiaryService? = null
         fun getInstance() : DailyDiaryService{
             return instance?: synchronized(this){
                 Retrofit.Builder().baseUrl("http://52.42.22.72:7373")//https가 아님. _ 근데 android에서 보안 문제로 이 막음.
                     .addConverterFactory(GsonConverterFactory.create())
                     .build()
                     .create() //reified 어쩌구 덕분에 가능... _ 첫번째 create와 다른 점.
             }
         }
     }*/

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val ID = "id"
        private const val BASE_URL = "http://52.42.22.72:7373"

        private var instance:  DailyDiaryService? = null

        fun getInstance():  DailyDiaryService {
            return instance ?: synchronized(this) {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create< DailyDiaryService>()
                    .also { instance = it }
            }
        }
    }
}