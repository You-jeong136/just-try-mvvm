package com.study.aos.mvvm.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import com.study.aos.mvvm.data.DiaryMemory
import com.study.aos.mvvm.databinding.ActivityEditDiaryBinding
import com.study.aos.mvvm.domain.Diary
import com.study.aos.mvvm.presentation.EditDiaryViewModel
import java.util.*

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditDiaryBinding

    private val editDiaryViewModel : EditDiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_diary)
        //inflate 안에 xml parser가 있어서 얘를 파싱해서 뷰를 만듦.
        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.btnEditSubmit.setOnClickListener {
            editDiaryViewModel.saveDiary()
            setResult(Activity.RESULT_OK)
            finish()
        }

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

        editDiaryViewModel.loadDiary(getDiaryId())
        binding.viewModel = editDiaryViewModel

        editDiaryViewModel.title.observe(this){
            Log.d("********Edit_Title", it)
        }
    }

    private fun getDiaryId() : String? {
        return intent.getStringExtra(KEY_DIARY_ID)
    }

    private fun saveDiary(){
        val diary = Diary(
            id = UUID.randomUUID().toString(),
            title = binding.tvEditContent.text.toString(),
            content = binding.tvEditContent.text.toString(),
            created = Date()
        )
        DiaryMemory.saveDiary(diary)
    }

    companion object{
        const val KEY_DIARY_ID = "KEY_DIARY_ID"
    }
}
