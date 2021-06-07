package re.sta.rt.aop_part3_chapter09


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannal();

        val type = remoteMessage.data["type"]
            ?.let { NotificationType.valueOf(it) }

        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        // type 이 null(데이터가 x) 경우.
        type ?: return



        // 알림을 보여지는 것을 확인할 수 있다.
        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, message))
        // type.id 로 type 별로 알림을 보이게한다.
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

            // 이러면 채널 완성 !!
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channal)
        }

    }

    private fun createNotification(
        type: NotificationType, title:String?, message:String?) : Notification {
       return NotificationCompat.Builder(
            this,
            CHANNAL_ID
        ).setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

    }

    companion object {
        private const val CHANNAL_NAME = "Emoji Party"
        private const val CHANNAL_DESCRIPTION = "Emoji Party를 위환 채널"
        private const val CHANNAL_ID = "Channal Id"
    }
}