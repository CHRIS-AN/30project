package re.sta.rt.aop_part3_chapter12

import androidx.room.Database
import androidx.room.RoomDatabase
import re.sta.rt.aop_part3_chapter12.dao.HistoryDao
import re.sta.rt.aop_part3_chapter12.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao() : HistoryDao
}