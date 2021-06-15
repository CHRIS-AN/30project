package re.sta.rt.aop_part3_chapter13

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


// Match 된 List 를 불러오는 곳
class MatchedUserActivity : AppCompatActivity() {

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB : DatabaseReference
    private val adapter = MatchedUserAdapter()

    // 카드 아이템 관리
    private val cardItems = mutableListOf<CardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)


        userDB = Firebase.database.reference.child("Users")

        initMatchedUserRecyclerView()
        // 실제로 DB에서 값가져오기
        getMatchUsers()
    }



    private fun initMatchedUserRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.matchedUserRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    private fun getMatchUsers() {
        val matchDB = userDB.child(getCurrentUserID()).child("likeBy").child("match")

        matchDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.key?.isNotEmpty() == true) {
                    // matched 항목의 key 가 존재한다면? userDB 에서 데이터를 가져온다.
                    getUserByKey(snapshot.key.orEmpty())
                }
            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })


    }
    // 데이터가 있을 때? 이 함수가 실행이 될 것이다.
    private fun getUserByKey(userId: String) {
        userDB.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //  실제로 key 을 가지고 조회를해서 name 의 value를 가져온다.
                cardItems.add(CardItem(userId, snapshot.child("name").value.toString()))
                adapter.submitList(cardItems)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getCurrentUserID () : String {

        // 혹시나 로그인이 풀릴 수도 있으니, 예외처리 하기
        if(auth.currentUser == null) {
            Toast.makeText(this,"로그인이 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            finish() // 메인엑티비티로 돌아가기 (로그인을 하기 위해)
        }
        // *
        return auth.currentUser?.uid.orEmpty()
    }

}