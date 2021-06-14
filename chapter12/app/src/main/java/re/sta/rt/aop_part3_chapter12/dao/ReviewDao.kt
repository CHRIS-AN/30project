package re.sta.rt.aop_part3_chapter12.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import re.sta.rt.aop_part3_chapter12.model.Review


@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE id == :id")
    fun  getOneReview(id:Int) : Review

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review : Review)
}