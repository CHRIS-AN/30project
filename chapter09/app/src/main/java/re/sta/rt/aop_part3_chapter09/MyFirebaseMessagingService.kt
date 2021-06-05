package re.sta.rt.aop_part3_chapter09


import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/*
    현재 등록된 토큰.
    그것을 통해서 메세지를 보냄.

    토큰이라는 게 굉장히 자주 변경하다.
    새 기기에서 볼 때, 앱을 재설치할 때, 앱 데이터를 CLEAR 할 때, 토큰들이 갱신을 하게 된다.
    따라서, 변경될 가능성이 있으니?
    현재 토큰을 가져와서 쓰는 코드르 작성하면 안좋다.
    따라서, FirebaseMessagingService 에서 onNewToken을 할 때마다, 서버에서 해당 토큰 갱신!!
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
    // 매니페스트에 등록을 했을 때, Cloud Messaing 에서 수신할 때마다, 이 메서드를 호출하게 된다.
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }


    // 채널 객체생성
    private fun createNotificationChannal() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channal = NotificationChannel(
                CHANNAL_ID,
                CHANNAL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channal.description = CHANNAL_DESCRIPTION
        }

    }

    companion object {
        private const val CHANNAL_NAME = "Emoji Party"
        private const val CHANNAL_DESCRIPTION = "Emoji Party를 위환 채널"
        private const val CHANNAL_ID = "Channal Id"
    }
}