package re.sta.rt.aop_part3_chapter12.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// 직렬화.
@Parcelize
data class Book(
    @SerializedName("itemId")
    val id : Long,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("coverSmallUrl")
    val coverSmallUrl : String
): Parcelable
