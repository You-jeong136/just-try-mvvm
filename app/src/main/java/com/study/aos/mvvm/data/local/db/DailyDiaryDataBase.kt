package com.study.aos.mvvm.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.aos.mvvm.data.local.converter.DateTypeConverter
import com.study.aos.mvvm.data.local.dao.DiariesDao
import com.study.aos.mvvm.data.local.entity.DiaryEntity

@Database(
    entities = [DiaryEntity::class], version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class DailyDiaryDataBase : RoomDatabase() {
    abstract fun getDiariesDao() : DiariesDao

    companion object{
        private var instance : DailyDiaryDataBase? = null

        fun getInstance(context: Context) : DailyDiaryDataBase{
            return instance ?: synchronized<DailyDiaryDataBase>(this){
                newInstance(context).also { instance = it }
            }
        }

        @JvmStatic
        fun newInstance(context : Context) : DailyDiaryDataBase{
            return Room.databaseBuilder(context, DailyDiaryDataBase::class.java,
                "DailyDairyDataBase"
            )//.allowMainThreadQueries() //임시로 붙인코드. 반드시 지워야하는. (딱딱 끊길 것...)
                //원래는 room은 main thread에서 돌릴 수 없음. 돌리면 터지게 됨.
                .build()

        }
    }
}