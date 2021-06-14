package re.sta.rt.aop_part3_chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import re.sta.rt.aop_part3_chapter12.adapter.BookAdapter
import re.sta.rt.aop_part3_chapter12.adapter.HistoryAdapter
import re.sta.rt.aop_part3_chapter12.api.BookService
import re.sta.rt.aop_part3_chapter12.databinding.ActivityMainBinding
import re.sta.rt.aop_part3_chapter12.model.BestSellerDto
import re.sta.rt.aop_part3_chapter12.model.History
import re.sta.rt.aop_part3_chapter12.model.SearchBookDto
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

/*
    스크롤 뷰 vs 리사이클 뷰

    차이는, 스크롤 뷰에 보이는 리스트의 모든 데이터에 대한 뷰가 다 그려지고
    그것을 스크롤이 밑에있어도 그러져 있어, 앱이 죽거나 느려지기 때문에

    리사이클 뷰를 사용하여
    뷰를 올릴 시, 해당 보이려는 데이터만 불러와서 보여주게 끔한다.


    * 리사이클 뷰를 가져와서 사용하려면, '레이아웃매니저' 와, '어댑터' 라는 것이 필요로 하다.
 */


class MainActivity : AppCompatActivity() {

    // 다른 곳에서 뷰 바인딩을 사용하기 위하여,
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : BookAdapter
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var bookService : BookService


    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 리사이클 뷰를 사용하기 위하여, 뷰 바인딩을 가져와 보겠습니다.
        // activity_main.xml 뷰를 바인딩하기 하여.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 리사이클 뷰를 초기화를 시켜준다.
        initBookRecyclerView()
        initHistoryRecyclerView()



        // AppDatabase 형식으로 저장된다.
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()


        // retrofit 구현체 생성하기.
        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interParkAPIkey))
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
                        adapter.submitList(it.books)
                    }

                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // 실패 했을 때, 실패처리

                    Log.d(TAG, t.toString())
                }

            })

         // serach editor 에 key 가 눌린 event 주기
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

    }



    private fun search(keyword : String) {
        bookService.getBooksByName(getString(R.string.interParkAPIkey), keyword)
            .enqueue(object : Callback<SearchBookDto> {

                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    // 성공했을 때, 한번 지워주고?
                    hideHistoryView()

                    // 성공 했을 시, 성공 처리
                    saveSearchKeyword(keyword)
                    // 성공 x
                    if(response.isSuccessful.not()) {
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())
                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    // 실패 했을 때, 실패처리

                    // search 가 실패했을 때도 지워주기
                    hideHistoryView()
                    Log.d(TAG, t.toString())
                }

            })

    }


    private fun initBookRecyclerView() {
        adapter = BookAdapter()
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter


    }



    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun showHistoryView() {
        // DB 에서 데이터를 가져온 다음에 그것을 어댑터에 넣어서 여준다.
        Thread {
             val keywords =  db.historyDao().getAll().reversed()

            // UI 작업
            runOnUiThread {
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty()) // null 처리 잡기
            }
        }


        binding.historyRecyclerView.isVisible = true
    }
    // 키워드 하나를 저장하는 fun
    private fun saveSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }

    // 쓰레드를 열어서 delete 시켜주기
    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().delete(keyword)
            // delete를 한 뒤에는 view 를 갱신을 해주어야한다.
            // todo View 갱신
            showHistoryView()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}