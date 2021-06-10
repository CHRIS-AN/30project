package re.sta.rt.aop_part3_chapter11

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import java.util.*
import re.sta.rt.aop_part3_chapter11.AlarmDisplayModel as AlarmDisplayModel

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
        // 0. 뷰를 초기화 해주기(온클리리스너)
        initOnOffButton()
        initChangeAlarmTimeButton()
        // 1. 앱을 첫 시작할 때, 쉐어드프리퍼런스 에서 데이터를 가져온다.
        // 2. 가져 온 데이터를 뷰에 그려준다.

    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.onOffButton)
        onOffButton.setOnClickListener {
            // 저장한 데이터를 확인한다.

            // 온? 오프? 에 따라 작업을 처리한다.

            // 오프라면? 알람을 제거

            // 온이라면 알람을 등록

            // 데이터 저장.
        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeButton)
        changeAlarmButton.setOnClickListener {
            // 2.현재 시간을 일단 가져온다.
            val calendar = Calendar.getInstance()
            // 1.TimePickDialog 을 띄어줘서 시간을 설정을 하도록 하게끔 하고, 그 시간을 가져와서
             TimePickerDialog(this, { picker, hour, minute ->
                 // 3.데이터를 저장한다.
                    val model = saveAlarmModel(hour, minute, false)

                    //      뷰를 업데이트 한다.
                    //          기존에 있던 알람을 삭제한다.


                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)

        }
    }

    private fun saveAlarmModel(
        hour : Int,
        minute : Int,
        onOff : Boolean
    ) : AlarmDisplayModel {
            val model = AlarmDisplayModel (
                hour = hour,
                minute = minute,
                onOff = false
                    )

        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)

        // edit mode 로 열기
        with(sharedPreferences.edit()) {
            putString("alarm", model.makeDataForDB())
            putBoolean("onOff", model.onOff)
            commit()
        } // commit 을 해야 저장할 수 있다.

        return model
    }


}