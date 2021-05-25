package re.sta.rt.aop_part2_chapter04

import androidx.room.Database
import androidx.room.RoomDatabase
import re.sta.rt.aop_part2_chapter04.model.History


// 데이터베이스 만들기

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao() : History
}