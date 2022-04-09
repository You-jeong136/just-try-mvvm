package com.study.aos.mvvm.data.remote

import com.study.aos.mvvm.data.remote.parameters.GetTokenParams
import com.study.aos.mvvm.data.remote.service.AuthService
import okhttp3.Interceptor
import okhttp3.Response

class AddingAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        //서비스 분리~~~ 되었다는 가정 하. 예외처리 따로.
        val token = AuthService.getInstance().getToken(GetTokenParams("id", "pw"))
            .body()!!.token

        //여기가 납치. _chain이라는 친구안에 request가 있어. 여기에 바디랑 헤더랑 기타 등등 다 들어있음.
        val addedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", token) //헤더에 토큰을 소매 넣기.
            //.post() // post로 보낸 body를 간섭.
            .build()
        //request안에 무슨 짓을 해서 소매를 넣어 돌려보냄.
        //response는 한번 생성 시 변경 불가능 보통 newBuilder를 통해 복사하여 조작함.
        //그리고 조작한 addResquest 또한 불변 객체. 신뢰도 높음.

        return chain.proceed(addedRequest)

  /*      val response : Response = chain.proceed(chain.request()) //proceed 자체 : 다시 갈길 지나가세요~~ 호출 이후부터는 server갔다가 다시 돌아온 시점임. response

        //아래는 response를 조작등 지지고 볶을때
        return response.newBuilder()
            .body()
            .code()
            .message()
            .build()
    */

    }
}