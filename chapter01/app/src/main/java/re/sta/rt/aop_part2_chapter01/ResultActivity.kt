package re.sta.rt.aop_part2_chapter01

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {

    // 실행이 되었을 때, 호출해주는 함수이다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getIntExtra("height", 0) // 숫자는 기본 값이다.
        val weight = intent.getIntExtra("weight", 0)


        Log.d("res", "height : $height , weight : $weight")

        val bmi = weight/ (height / 100.0).pow(2.0) // pow 함수를 쓴 코드
        val resultTest = when{
            // when 의 expression
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중정도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

        val resultValueTextView = findViewById<TextView>(R.id.bmiResultTextView)
        val resultStringTextView = findViewById<TextView>(R.id.resultTextView)

        resultValueTextView.text = bmi.toString()
        resultStringTextView.text = resultTest


    }
}