package re.sta.rt.aop_part2_chapter04.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import re.sta.rt.aop_part2_chapter04.model.History

// interface 정리

@Dao
interface HistoryDao {

    // hisory 를 전부 가져오기
    @Query("SELECT * FROM history")
    fun getAll() : List<History>

    @Insert
    fun insertHistory(history: History)

    // 전체삭제기능
    @Query("DELETE FROM history")
    fun deleteAll()

    // 몇 가지 예시 (example)
    /*

    @Delete
    fun delete(history: History)

    @Query("SELECT * FROM history WHERE result LIKE :result")
    fun findByResult(result: String) : List<History>

    @Query("SELECT * FROM history WHERE result LIKE :result LIMIT 1")
    fun findByResultLimit(result: String) : List<History>


     */
}