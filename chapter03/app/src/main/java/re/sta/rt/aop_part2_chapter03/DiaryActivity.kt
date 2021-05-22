package re.sta.rt.aop_part2_chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    //메인스레드와 연결하는 걸 handler를 이용한다.
    private val handler = Handler(Looper.getMainLooper()) // 메인스레드에 연결.


    private val diaryEditText : EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))

        // 글을 작성하다가 잠깐 멈칫 할 때, 저장을 할 수 있게 thread 기능을 사용
        // text 가 change 될 때마다, 이 람다가 실행이 된다.
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                // 수시로 백그라운드에서 글을 저장하는 기능.
                putString("detail", diaryEditText.text.toString())
            }
            Log.d("DiaryActivity", "SAVE 되었습니다 !!")
        }

        // diaryEditText 가 변경이 될 때마다, 글이 저장하는 기능.
        diaryEditText.addTextChangedListener {
            // handler 사용.
            Log.d("DiaryActivity", "TextChanged :: $it")
            handler.removeCallbacks(runnable) // 이전에있는 runnable 을 지우기,
            handler.postDelayed(runnable, 500) // 0.5 초가 지나도 변동이 없으면
                                // runnable 로 가서, editText가 저장이 된다.
        }


    }
}