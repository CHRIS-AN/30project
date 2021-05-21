package re.sta.rt.aop_part2_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

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

    private val numberTextViewList : List<TextView> by lazy {

        // 6개의 텍스트뷰가 초기화되어, 순차적으로 들어가게 된다.
        listOf<TextView>(
            findViewById<TextView>(R.id.textView1),
            findViewById<TextView>(R.id.textView2),
            findViewById<TextView>(R.id.textView3),
            findViewById<TextView>(R.id.textView4),
            findViewById<TextView>(R.id.textView5),
            findViewById<TextView>(R.id.textView6)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {

            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }

            // 다시 번호가 추가가 될 것이다.
            didRun = false
        }
    }

    private fun initAddButton() {
        addButton.setOnClickListener {

            // 예외처리1
            if(didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 예외처리2
            if(pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 예외처리3
            if(pickNumberSet.contains(numberPicker.value)) {
                //true 일 경우우는, 이미 선택된 번호입니다.
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true // textView가 보이지 않기 때문에 true로 준 다음에 textView가 보이도록 설정해줘야함.
            textView.text = numberPicker.value.toString() // 해당값을 toString으로 변경하여 값을 넣어준다

            setNumberBackground(numberPicker.value, textView)
            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()

            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }
        }
    }

    private fun setNumberBackground(number : Int, textView: TextView) {
        // 숫자에 맞는 색이 나오게끔
        when(number) {
            in 1..6 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            in 7..12 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_purple)
            in 13..18 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 19..24 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 25..32 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45) {
                    if(pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0,6 - pickNumberSet.size)

        return newList.sorted()
    }
}























