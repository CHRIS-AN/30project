package re.sta.rt.aop_part3_chapter09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


/*
    Firebase 토큰 확인
    일반/확장형/커스텀 알림 확인

    기능>
    Firebase Cloud Messaging
    Notification

 */

class MainActivity : AppCompatActivity() {

    private val resultTextView : TextView by lazy { findViewById(R.id.resultTextView)}
    private val firebaseToken : TextView by lazy { findViewById(R.id.firebaseTokenTextView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}