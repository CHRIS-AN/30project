package re.sta.rt.aop_part3_chapter13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth


/*

    틴더 앱

    Firebase Authentication 사용하기
        - Email Login
        - Facebook Login

    Firebase Realtime Database 사용하기
    yuyakaido / CardStackView 사용하기



 Firebase Authentication 을 통해 이메일 로그인/ 페이스북 로그인을 할 수 있음.
 Firebase Realtime Database 를 이용하여 기록을 저장하고, 불러올 수 있음
 Github 에서 Opensource Library 를 찾아 사용할수 있음.

 */
class MainActivity : AppCompatActivity() {

    // 5. login_activity 연결
    // auth 를 가져온다.
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // 앱이 실행이 되었을 떄?
    override fun onStart() {
        super.onStart()

        // 로그인이 성공이 되었을 때, auth.currentUser 에 login 한 정보가 저장이 된다.
        if(auth.currentUser == null) {
            // 로그인 x -> login_activity 로 이동.
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}



















