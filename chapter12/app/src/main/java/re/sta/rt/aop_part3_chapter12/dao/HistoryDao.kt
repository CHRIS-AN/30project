package re.sta.rt.aop_part3_chapter12.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import re.sta.rt.aop_part3_chapter12.model.History

@Dao
interface HistoryDao {
    //     insert / select / delete 1

    @Query("SELECT * FROM history")
    fun getAll() : List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword : String)
}