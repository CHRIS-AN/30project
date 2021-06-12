package re.sta.rt.aop_part3_chapter12.api


import re.sta.rt.aop_part3_chapter12.model.BestSellerDto
import re.sta.rt.aop_part3_chapter12.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    // 두 개의 api
    /*
        get http 에다가 담아서 (URL),
        post 요청할 때, 새로운 데이터를 만들 때, 바디에다가 담아서보낸다
     */

    @GET("/api/sear ch.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey : String,
        @Query("query") keyword : String
    ) : Call<SearchBookDto>

    @GET("/api/bestSeller.api?output=json&categoryId=100=")
    fun getBestSellerBooks(
        @Query("key") apiKey: String
    ): Call<BestSellerDto>

}