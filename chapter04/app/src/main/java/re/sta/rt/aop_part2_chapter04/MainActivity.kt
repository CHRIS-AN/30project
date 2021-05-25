package re.sta.rt.aop_part2_chapter04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private val expressionTextView : TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    private val resultTextView : TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    private val historyLayout : View by lazy {
        findViewById<View>(R.id.historyLayout)
    }

    private val historyLinearLayout : View by lazy {
        findViewById<View>(R.id.historyLinearLayout)
    }

    private var isOperator =  false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    // layout에 있는, android : onClick 과 연결
    fun buttonClicked (v : View) {
        when(v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")

            R.id.buttonPlus -> operatorButtonClicked("+")
            R.id.buttonMinus -> operatorButtonClicked("-")
            R.id.buttonMulti -> operatorButtonClicked("*")
            R.id.buttonDivider -> operatorButtonClicked("/")
            R.id.buttonModulo -> operatorButtonClicked("%")

        }
    }

    // button 들이 눌렸을 때? (숫자는 15자리까지만)
    private fun numberButtonClicked(number: String) {

        if(isOperator) {
            //연산자를 사용하다 왔니?
            expressionTextView.append(" ")
        }
        isOperator = false

        val expressionText =  expressionTextView.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length > 15) {
            Toast.makeText(this, "15 자리까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        expressionTextView.append(number)
        // TODO resultTextView 실시간으로 계산 결과를 넣어야하는 기능
        resultTextView.text = calculateExpression()
    }

    // 연산자 버튼 클릭 시, (연산자가 눌렸을 시, 한 가지 연산자만 사용되게끔)
    private fun operatorButtonClicked(operator : String) {
        //연산자가 먼저 들어오면 안된다.
        if (expressionTextView.text.isEmpty()) {
            return
        }
        // 이미 연산자를 썼는데 또 쓰는 경우
        // 이미 연산자를 썼는데, 수정을 하기 위해서,
        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }

            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }

            // 숫자만 입력하고, 연산자가 한 번도 들어오지 않은 경우. (위의 두가지 경우가 false 일 때,)
            else -> {
                expressionTextView.append(" $operator")
            }
        }
        // 글자를 초록색으로
        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length -1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true
    }

    fun resultButtonClicked (v : View) {
        val expressionTexts = expressionTextView.text.split(" ")

        if(expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            // 연산할 게 없으니, 아무것도 주지 않는다. (예외처리?)
            return
        }

        if (expressionTexts.size != 3 && hasOperator) {
            // 숫자와 연산자까지만 입력이 된 상태 (완성되지 않은 수식)
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        resultTextView.text = ""
        expressionTextView.text = resultText

        // 연산이 한 번 끝났기 때문에, 다시 초기화
        isOperator = false
        hasOperator = false
    }


    private fun calculateExpression() : String {
        // expression 뷰 에서 연산자 숫자를 가져와서 resultTextView에 값을 넣기 위한 반환 함수.
        val expressionTexts = expressionTextView.text.split(" ")

        if(hasOperator.not() || expressionTexts.size != 3) {
            // 예외가 발생하니, 빈 string 값을 넣는다.
            return ""
        }else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not())
            return ""

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when(op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "x" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

    fun historyButtonClicked (v : View) {
        historyLayout.isVisible = true

        // TODO 디비에서 모든 기록 가져오기
        // TODO 뷰에 모든 기록 할당하기

    }

    fun closeHistoryButtonClicked (v :View) {
        historyLayout.isVisible = false
    }

    fun historyClearButtonClicked (v :View) {
        // TODO 디비에서 모든 기록삭제
        // TODO 뷰에서 모든 기록삭제
    }


    fun clearButtonClicked (v : View) {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }


}

// 확장 함수 만들기.
fun String.isNumber(): Boolean {
    return try {
        // BigInteger는 무한의 수를 저장할 수 있다
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}






