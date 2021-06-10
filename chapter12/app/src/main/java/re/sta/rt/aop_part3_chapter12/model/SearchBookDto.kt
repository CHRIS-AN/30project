package re.sta.rt.aop_part3_chapter12.model

import com.google.gson.annotations.SerializedName

data class SearchBookDto(
    @SerializedName("title")
    val title : String,

    @SerializedName("item")
    val books : List<Book>
)
