package re.sta.rt.aop_part2_chapter01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val heightEditText : EditText  아무것도 할당해주지 않았다면? error 발생한다.
        val heightEditText : EditText = findViewById(R.id.heightEditText) // 명시적
        val weightEditText = findViewById<EditText>(R.id.weightEditText) // 추론적
        val resultButton = findViewById<Button>(R.id.resultButton)


        resultButton.setOnClickListener {
            // 이 곳은 람다함수를 사용하는 공간
            Log.d("Main", "ResultButton 이 클릭 됨")

            if(heightEditText.text.isEmpty() || weightEditText.text.isEmpty()) { // 비어있다면? true
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val height : Int = heightEditText.text.toString().toInt()
            val weight : Int = weightEditText.text.toString().toInt()
           // Log.d("Main","height : $height and weight : $weight")

            val intent = Intent(this, ResultActivity::class.java)

            // intent 에 값을 넣는 방법.
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)

            startActivity(intent) // 메인에서 다음으로 resultActivity 로 실행하게 된다. (또는 위로 올라가게된다)
        }


    }
}