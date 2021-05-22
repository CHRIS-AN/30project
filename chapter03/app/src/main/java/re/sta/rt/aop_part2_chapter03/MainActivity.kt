package re.sta.rt.aop_part2_chapter03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    // lazy 유닛에서 넘버피커를 이니셜라이징 해주는 과정에서 apply 함수를 통해 초기화

    private val numberPicker1 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    // 오픈 버튼
    private val openButton : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    // 패스워드 변경 버튼
    private val changePasswordButton : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    // 패스워드 변경 중에, 어떠한 기능도 사용할 수 없게 막기.
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if(changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 이 람다식만 하고 나간다고 설정해두는 것.
                                        // 이렇게 return을 하지 않을경우에, setOnClickListener 를 나가게 된다.
            }


            // numberPicker 1,2,3 랑 비교를하기 때문에, 기기에 입력한 번호를 저장해주어야한다.

            //sharedPreference를 가지고 저장되어있는 값을 가져오기로한다.
                            // password라는 preference 라는 파일을 다른 앱과 같이 사용할 수 있게,
                            // share를 하는 것인데, 다른 앱과 공유를하지 않고, 나만 사용할 수 있게 모드를 private을 설정하낟.
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                // 패스워드 성공
                //TODO 다이어리 페이지 작성 후에 넘겨주어야 합니다.
                startActivity(Intent(this, DiaryActivity::class.java))
            }else {
                // 패스워드 실패
                showErrorAlertDialog()
            }
        }


        // 비밀번호를 바꾸는 기능
        changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changePasswordMode) {
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }
                // commit 을 통해 저장이 끝났기 때문에,
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            }else {
                // changePasswordMode 가 활성화 :: 비밀번호가 맞는지를 체크해줘야한다.
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {

                    // 이 곳은 패스워드 변경 모드 !
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()

                    // changePassword 모드가 활성화 되었음을 알려주기 위함.
                    changePasswordButton.setBackgroundColor(Color.RED)
                }else {
                    // 패스워드 실패
                    showErrorAlertDialog()
                }
            }
        }
    }

    // 패스워드 실패 함수.
    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Failed")
            .setMessage("비밀번호가 잘못 되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}