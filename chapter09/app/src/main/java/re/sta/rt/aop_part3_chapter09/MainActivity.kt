package re.sta.rt.aop_part3_chapter09

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging


/*
    Firebase 토큰 확인
    일반/확장형/커스텀 알림 확인

    기능>
    Firebase Cloud Messaging
    Notification

 */


// 클라우드 메시징(FCM)은?
// 무료로 메시지를 안정적으로 전송할 수 있는 교차 플랫폼 메시징 솔루션이다.
/*
    <FCM 아키텍처>
    Message building and targeting
    -> FCM backend
    -> Platform-level message transport
    -> SDK on device
    https://firebase.google.com/docs/cloud-messaging/fcm-architecture?hl=ko

 */
class MainActivity : AppCompatActivity() {

    private val resultTextView : TextView by lazy { findViewById(R.id.resultTextView)}
    private val firebaseToken : TextView by lazy { findViewById(R.id.firebaseTokenTextView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent) // 새로 들어온 intent 로 교체.
        updateResult(true)
    }

    private fun initFirebase() {
        // 등록된 토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val token = task.result // 성공했을 경우. firebase Messaing에 token을 가져온다.
                firebaseToken.text = token // 여기다가 추가해준다.
            }
        }
    }

    private fun updateResult(isNewIntent : Boolean = false) {
        //true = 갱신
        //false = 실행되었다.

        resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") +
        if(isNewIntent) {
            "(으)로 갱신했습니다."
        }else {
            "(으)로 실행되었습니다."
        }
    }
}