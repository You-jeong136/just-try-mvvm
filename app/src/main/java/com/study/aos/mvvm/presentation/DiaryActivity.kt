package com.study.aos.mvvm.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.study.aos.mvvm.DiariesAdapter
import com.study.aos.mvvm.data.DiariesLocalSource
import com.study.aos.mvvm.data.db.DailyDiaryDataBase
import com.study.aos.mvvm.databinding.ActivityMainBinding
import com.study.aos.mvvm.domain.Diary
import java.util.*

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var diariesAdapter: DiariesAdapter

    private lateinit var editDiaryActivityLauncher: ActivityResultLauncher<Intent>


    private val diariesViewModel : DiariesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        onResume으로 이사.
        diariesAdapter.submitList(STUB_DIARY){
            //아래와 같은 상황을 방지하기 위해.
            //해당 비동기가 다끝난 다음 동작할 코드를 넣어주면 해결 가능함
        }
        //뭔가 어썸 코드를 호출함. _> 이때 submitList는 비동기적으로 처리되기 때문에,
        // 처리 중 어썸 코드가 호출될 경우. 변경이 있지만 호출 전 기준으로 결과가 나올 수도 있다.
*/
        initView()

        //Contract는 직접 구현. contracts는 많이 쓴다고 생각해둔 구현체들이 이미 있는것.
        //권한 관련 것들도 직접 구현하기 힘든데, 이미 되어있음. 자세한거는 말리빈님 블로그에 물어보면 ok
        // 이때 launcher는 생명주기에 맞춰서 띄워줘야만 함. 안 그럼 터짐.
        editDiaryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onEditMemoFinished(it)
        }

        diariesViewModel.diaries.observe(this){
            diariesAdapter.submitList(it)
        }


        //db 임시코드
        val dao = DailyDiaryDataBase.newInstance(this).getDiariesDao()
      /*  Log.d("*********MAIN_DAILY_DAIRY_DB ", "${dao.getAllDiaries()}")
        dao.insertDiary(DiaryEntity("title", "conent", Date()))
        Log.d("*********MAIN_DAILY_DAIRY_DB ", "${dao.getAllDiaries()}")
       */
        val diariesLocalSource = DiariesLocalSource(dao)
        val diary = Diary("1", "2", "3", Date())
        diariesLocalSource.saveDiary(
            diary = diary,
            onSuccess = {
                Log.d("*********MAIN_DAILY_DAIRY_DB ", "save success")
                diariesLocalSource.getDiary(
                    "1",
                    onSuccess = {
                        Log.d("*********MAIN_DAILY_DAIRY_DB ", "diary : $it")
                    },
                    onFailure = {
                        Log.d("*********MAIN_DAILY_DAIRY_DB ", "get fail")
                    }
                )
            },
            onFailure = {
                Log.d("*********MAIN_DAILY_DAIRY_DB ", "save fail")
            }
        )
    }

    fun initView(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMainDiary.adapter = DiariesAdapter(::onDiaryClick).also { diariesAdapter = it }
        binding.btnMainDiaryNew.setOnClickListener { deployEditDiaryActivity() }
    }

    override fun onResume() {
        super.onResume()
        diariesViewModel.loadDiaries()
    }

    private fun deployEditDiaryActivity(diary: Diary? = null) {
        val intent = Intent(this, EditDiaryActivity::class.java)
        if (diary != null) {
            intent.putExtra(EditDiaryActivity.KEY_DIARY_ID, diary.id)
        }
        editDiaryActivityLauncher.launch(intent)
    }

    private fun onDiaryClick(diary : Diary? = null){
        Log.d("************Main_DIARY_CLICK", "$diary")
        showToast(diary.toString())
        val intent = Intent(this, EditDiaryActivity::class.java)
        intent.putExtra(EditDiaryActivity.KEY_DIARY_ID, diary?.id)
        startActivity(intent)
    }
    private fun onEditMemoFinished(result: ActivityResult) = when (result.resultCode) {
        Activity.RESULT_OK -> showToast("작성 완료!")
        Activity.RESULT_CANCELED -> showToast("작성이 취소되었습니다.")
        else -> showToast("Unexpected Activity Result : $result")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

  /*  companion object {
        private val STUB_DIARY = listOf(
            Diary("0", "title", "content", Date()),
            Diary("1", "title1", "content1", Date()),
            Diary("2", "title2", "content2", Date()),
            Diary("3", "title3", "content3", Date()),
            Diary("4", "title4", "content4", Date()),
            Diary("5", "title5", "content5", Date()),
            Diary("6", "title6", "content6", Date()),

        )
    }*/
}