package com.study.aos.mvvm.presentation

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.aos.mvvm.data.local.db.DailyDiaryDataBase
import com.study.aos.mvvm.data.remote.service.DailyDiaryService
import com.study.aos.mvvm.data.repository.RealDiariesRepository
import com.study.aos.mvvm.databinding.ActivityEditDiaryBinding
import java.lang.IllegalStateException

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditDiaryBinding

    private val editDiaryViewModel : EditDiaryViewModel by viewModels {
        DiaryActivity.DiariesViewModelFactory(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_diary)
        //inflate 안에 xml parser가 있어서 얘를 파싱해서 뷰를 만듦.
        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
      /*  binding.btnEditSubmit.setOnClickListener {
            editDiaryViewModel.saveDiary()
            setResult(Activity.RESULT_OK)
            finish()
        }*/

        binding.tvEditContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                val len = p0.toString()
            }

        })

        binding.viewModel = editDiaryViewModel
        editDiaryViewModel.loadDiary(getDiaryId())


        editDiaryViewModel.editSuccessEvent.observe(this) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun getDiaryId() : String? {
        return intent.getStringExtra(KEY_DIARY_ID)
    }

    companion object{
        const val KEY_DIARY_ID = "KEY_DIARY_ID"
    }

    class EditDiariesViewModelFactory(
        private val context : Context,
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return when(modelClass){
               EditDiaryViewModel::class.java -> {
                   RealDiariesRepository(
                       DailyDiaryDataBase.getInstance(context).getDiariesDao(),
                       DailyDiaryService.getInstance(),
                   )
               }
                else -> { throw  IllegalStateException("input class : ${modelClass.simpleName}")}
            } as T
        }
    }
}
