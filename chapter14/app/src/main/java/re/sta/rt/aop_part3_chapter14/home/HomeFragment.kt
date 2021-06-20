package re.sta.rt.aop_part3_chapter14.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import re.sta.rt.aop_part3_chapter14.DBKey.Companion.DB_ARTICLES
import re.sta.rt.aop_part3_chapter14.R
import re.sta.rt.aop_part3_chapter14.databinding.FragmentHomeBinding


// HomeFragment
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var articleAdapter : ArticleAdapter
    private lateinit var articleDB : DatabaseReference


    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
           // snapshot 하나하나가 article 모델이다
            // 모델 클래스 자체를 받기
            val articleModel = snapshot.getValue(ArticleModel::class.java) // 데이터 맵핑을 하여, article 모델의 인스턴스를 가져올 수 있다.
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }



    // viewbinding
    private var binding : FragmentHomeBinding? = null
    // 로그인이 되어있는 지? 확인이 필요
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // 바인딩한 것 변수에 담아주고, 임시로 지역변수로 선언한 이유는,
        // binding 이란 전역변수가 nullable 이기 때문에 사용할 때마다 null을 풀어주야하기 때문.
        val fragmentHomeBinding  = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding


        articleList.clear()
        articleDB = Firebase.database.reference.child(DB_ARTICLES)
        articleAdapter = ArticleAdapter()

        // 리사이클 뷰 연결시키기
        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context) // getContext 를 하여, Fragment 에 없는 context를 가져온다고 생각
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter


        fragmentHomeBinding.addFloatingButton.setOnClickListener {

            context?.let {

                // seller ID가 필요하다보니? 아이디 로그인 상태, 즉 회원일 경우에만 판매글을 올릴 수 있게 만들기
                if(auth.currentUser != null) {
                    val intent =  Intent(requireContext(), AddArticleActivity::class.java)
                    startActivity(intent)
                }else {
                    Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_LONG).show()
                }

            }

        }


        // db에 event 등록
        // 탭구조 같은 경우에는 fragrment 가 재사용이된다, 따라서, 중복사용 우려가 있다.
        articleDB.addChildEventListener(listener)

    }

    override fun onResume() {
        super.onResume()


        // 다시 보일 때마다, 데이터로 뷰를 다시 그리는 코드.
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        articleDB.removeEventListener(listener)
    }
}