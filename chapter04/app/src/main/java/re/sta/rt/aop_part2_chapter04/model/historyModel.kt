package re.sta.rt.aop_part2_chapter04.model

import androidx.room.Entity

@Entity
data class History (
    val uid : Int?,
    val expression : String?,
    val result : String?
)