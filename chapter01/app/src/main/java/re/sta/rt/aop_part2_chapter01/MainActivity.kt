package re.sta.rt.aop_part2_chapter01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val heightEditText : EditText  아무것도 할당해주지 않았다면? error 발생한다.
        val heightEditText : EditText = findViewById(R.id.heightEditText) // 명시적
        val weightEditText = findViewById<EditText>(R.id.weightEditText) // 추론적
        val resultButton = findViewById<Button>(R.id.resultButton)

    }
}