package re.sta.rt.aop_part3_chapter14.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import re.sta.rt.aop_part3_chapter14.R
import re.sta.rt.aop_part3_chapter14.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    // viewbinding
    private var binding : FragmentHomeBinding? = null

    private lateinit var articleAdapter : ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 바인딩한 것 변수에 담아주고, 임시로 지역변수로 선언한 이유는,
        // binding 이란 전역변수가 nullable 이기 때문에 사용할 때마다 null을 풀어주야하기 때문.
        val fragmentHomeBinding  = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        // 임시
        articleAdapter = ArticleAdapter()
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("0", "aaaa",10000,"5000원",""))
            add(ArticleModel("0", "bbbb",20000,"10000원",""))
        })
        // 리사이클 뷰 연결시키기
        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context) // getContext 를 하여, Fragment 에 없는 context를 가져온다고 생각
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter



    }
}