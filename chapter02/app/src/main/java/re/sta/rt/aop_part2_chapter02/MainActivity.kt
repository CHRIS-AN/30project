package re.sta.rt.aop_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val clearButton : Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton : Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton : Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    // 미리 번호 추가하기 버튼을 누르기 전에, 자동생성 시작으로 값이 들어간 상태 일 경우를 대비해
    // 번호를 들어가지 않도록 예외처리를 대비함.
    private var didRun = false
    // 1~45 까지의 수를 중복 되게되지 않도록 해야함
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  
            }
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()

            //Log.d("main", list.toString())
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45) {
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = numberList.subList(0,6)

        return newList.sorted()
    }
}























