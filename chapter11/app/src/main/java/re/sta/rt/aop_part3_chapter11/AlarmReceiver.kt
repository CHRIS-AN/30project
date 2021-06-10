package re.sta.rt.aop_part3_chapter11

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }

    // 브로드캐스트 리시버에서 실제로 주었던 펜딩인텐트가 수신이 올 경우
    override fun onReceive(context: Context, intent: Intent) {

        // Noti 띄우기, api 26버전 이상 부턴, notificationChanel 필요!

        // 채널이없다면 채널 생성 후, 알람을 설정까지.
        createNotificationChannel(context)
        notifyNotification(context)
    }



    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel (
                "1000",
                "기상 알람",
                NotificationManager.IMPORTANCE_HIGH
                    )
            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {

            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("알람")
                .setContentText("일어날 시간입니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())
        }
    }
}
