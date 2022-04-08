package com.study.aos.mvvm.data.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

//뭐하는 코드인고....
class DispatchExecutors(
    val ioThread: Executor = IoThreadExecutor,
    val mainThread: Executor = MainThreadExecutor,
) {
    //싱글톤 객체
    companion object {
        private var instance: DispatchExecutors? = null

        fun getInstance(): DispatchExecutors {
            return instance ?: synchronized(this) {
                DispatchExecutors().also { instance = it }
            }
        }
    }
}

//Executor : java 에서 만든 스레드를 편리하게 쓰기 위한 툴.

private object MainThreadExecutor : Executor {
    //루퍼를 통해서 핸들러를 가져올 것임.
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    //이 함수를 통해서 핸들러로 이를 메인 스레드에서 돌릴 수 있게 해줌.
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}

private object IoThreadExecutor : Executor {
    //백그라운드 스레드, 아이오 스레드가 실제 그 스레드가 아니라, 명칭일뿐. 메인 스레드가 아닌 다른 스레드를 이야기함.
    private val ioThreadExecutor = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        ioThreadExecutor.execute(command)
    }
}