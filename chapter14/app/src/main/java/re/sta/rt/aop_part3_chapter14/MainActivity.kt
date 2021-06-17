package re.sta.rt.aop_part3_chapter14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import re.sta.rt.aop_part3_chapter14.chatList.ChatListFragment
import re.sta.rt.aop_part3_chapter14.home.HomeFragment
import re.sta.rt.aop_part3_chapter14.myPage.MyPageFragment


/*
    주요기능
    RecyclerView
    View Binding
    Fragment
    BottomNavigationView
    Firebase Storage
    Firebase Realtime Database
    Firebase Authentication
 */

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 처음에는 아무것도 attach 되어있지 않기 때문에? 초기 설정
        replaceFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                    R.id.chatList -> replaceFragment(chatListFragment)
                R.id.myPage -> replaceFragment(myPageFragment)

            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)  // 우리가 인자로 받아온 fragment 로 뷰를 교체하겠다.
                commit()
            }
    }
}