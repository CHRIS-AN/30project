package re.sta.rt.aop_part3_chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import re.sta.rt.aop_part3_chapter12.api.BookService
import re.sta.rt.aop_part3_chapter12.model.BestSellerDto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


/*
    도서 리뷰 앱
    -------------------------
    RecyclerView
    View Binding (뷰 레이아웃 과 코틀린 코드 연결)
    Retrofit (API 호출)
    Glide (이미지 로딩)
    Android Room (Local DB)
    Open API
    -------------------------
    인터파크 Open API 를 통해 베스트 셀러 정보를 가져와서 화면에 그릴 수 있음.
    인터파크 Open API 를 통해 검색어에 해당하는 책 목록을 가져와서, 화면에 그릴 수 있음.
    Local DB 를 이용하여 검색 기록을 저장하고 삭제할 수 있음.
    Local DB 를 이용하여 개인 리뷰를 저장할 수 있음.
    -------------------------
    인터파크 API 인증키
    00DA1902E81A1BC00CA35EFADD9B305C188942FC163A7F0C7FC6D870569AEC46


    책검색 URL
    http://book.interpark.com/api/search.api
    베스트셀러 요청 URL
    http://book.interpark.com/api/bestSeller.api
 */

/*
    retrofit 이란?
    Square Open
 */


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // retrofit 구현체 생성하기.
        val retrofit = Retrofit.Builder()
            .baseUrl("http://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)
        bookService.getBestSellerBooks("00DA1902E81A1BC00CA35EFADD9B305C188942FC163A7F0C7FC6D870569AEC46")
            .enqueue(object : Callback<BestSellerDto> {
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    // 성공 했을 시, 성공 처리

                    // 성공 x
                    if(response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG,book.toString())

                        }
                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // 실패 했을 때, 실패처리
                }

            })
    }
    companion object {
        private const val TAG = "MainActivity"
    }

}