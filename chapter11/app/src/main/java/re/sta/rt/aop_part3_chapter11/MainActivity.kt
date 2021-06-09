package re.sta.rt.aop_part3_chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*

    AlarmManager 
    Notification
    Broadcast receiver


    <Background>
        Immediate tasks (즉시 실행해야하는 작업)
            Thread
            Handler
            Kotlin coroutines

        Deferred tasks (지연된 작업)
            - WorkManager
        Exact tasks (정시에 실행해야 하는 작업)
            - AlarmManager

    <AlarmManager>
        Real Time (실제 시간)으로 실행 시키는 방법
        Elapsed Time (기기가 부팅된지부터 얼마나 지났는지) 으로 실행시키는 방법


     앱 설명 :
        지정된 시간에 알람이 울리게 할 수 있음.
        지정된 시간 이후에는 매일 같은 시간이 반복되게 알람이 울리게 할 수 있음.
        



 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}