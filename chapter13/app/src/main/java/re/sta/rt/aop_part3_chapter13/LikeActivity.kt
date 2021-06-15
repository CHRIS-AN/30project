package re.sta.rt.aop_part3_chapter13

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction


class LikeActivity : AppCompatActivity(), CardStackListener {

    // 7. 이름 저장
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB : DatabaseReference

    // 8. CardStackView Adapter 전역변수로 뺴두기
    private val adapter = CardItemAdapter()
    private val cardItems = mutableListOf<CardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        // 7. 커런트 유저 디비에 이름이 있는지 chk하고 없으면 이름 지정, 있으면 pass
        userDB = Firebase.database.reference.child("Users")

        val currentUserDB = userDB.child(getCurrentUserID())

        // currentUserDB 에서 값 가져오기
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 처음에 리스너를 달았을 때, 데이터가 존재하면 onDataChange 로 넘어온다. 또한 수정되었을 때도 이리로온다.
                // snapshot 는 우리 유저 정보
                if(snapshot.child("name").value == null) {
                    showNameInputPopup()
                    return
                }

                // todo 유저 정보를 갱신해라.
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        initCardStackView()
    }

    private fun initCardStackView() {
        val stackView = findViewById<CardStackView>(R.id.cardStackView)

        stackView.layoutManager = CardStackLayoutManager(this, this)
        stackView.adapter = adapter



    }

    // Alert Dialog
    private fun showNameInputPopup() {
        val editText = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("이름을 입력해주세요.")
            .setView(editText)
            .setPositiveButton("저장") { _, _ ->
                // editText 에서 값을 가져와서 db에 저장을한다.
                if(editText.text.isEmpty()) {
                    showNameInputPopup()
                }else {
                    saveUserName(editText.text.toString())
                }
            }
            .setCancelable(false)
            .show()
    }


    // 유저 아이디 저장.
    private fun saveUserName(name: String) {

        val userId = getCurrentUserID()
        val currentUserDB = userDB.child(userId)
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        user["name"] = name
        currentUserDB.updateChildren(user) // Users > user > userId

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

    override fun onCardDragging(direction: Direction?, ratio: Float) {}
    override fun onCardSwiped(direction: Direction?) {

    }
    override fun onCardRewound() {}
    override fun onCardCanceled() {}
    override fun onCardAppeared(view: View?, position: Int) {}
    override fun onCardDisappeared(view: View?, position: Int) {}

}