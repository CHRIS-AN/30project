package re.sta.rt.aop_part3_chapter12.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History (
    @PrimaryKey val uid : Int?,
    @ColumnInfo(name = "keyword") val keyword : String?
        )