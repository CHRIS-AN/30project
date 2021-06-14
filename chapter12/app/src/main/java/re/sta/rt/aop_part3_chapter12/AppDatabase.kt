package re.sta.rt.aop_part3_chapter12

import androidx.room.Database
import androidx.room.RoomDatabase
import re.sta.rt.aop_part3_chapter12.dao.HistoryDao
import re.sta.rt.aop_part3_chapter12.dao.ReviewDao
import re.sta.rt.aop_part3_chapter12.model.History
import re.sta.rt.aop_part3_chapter12.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao() : HistoryDao
    abstract fun reviewDao() : ReviewDao
}