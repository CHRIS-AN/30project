package re.sta.rt.aop_part3_chapter13

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/*
    Email password 를 받아와서, Firebase 에 전달을 해줌으로 써 추가해줘야한다.

 */

class LoginActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // firebase 에서 auth 를 가져온다.
        auth = Firebase.auth    // getInstance 랑 동일하다가 생각.

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        // login 버튼을 눌렀을 때, event
        initLoginButton()
        // 회원가입 버튼을 눌렀을 때, event
        initSignUpButton()

        // 4. email null 값 잡기.
        initEmailAndPasswordEditText()
    }

    // 로그인
    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

            // 1. 사용자가 직접 친, email 과 password 를 가져온다.
            val email =  getInputEmail()
            val password = getInputPassword()

            // 2. 가져온 이메일과 패스워드를 이용하여, 로그인 및 회원 가입 시키기
            auth.signInWithEmailAndPassword(email, password) // 반환 값은 authResultTask
                .addOnCompleteListener(this) { task ->
                    // 3. task 가 완료가 됐는지, 안됐는지, Firebase 가 검증 후, 이곳으로 return task 를 반환 시켜줄 것이다.
                    if(task.isSuccessful) {
                        finish() // 성공하면, login_activity 를 종료.
                    }else {
                        // 만약 실패했다면? toast message 창 띄우기
                        Toast.makeText(this, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
    // 회원가입
    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {

            // 1. 사용자가 직접 친, email 과 password 를 가져온다.
            val email =  getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "회원가입에 성공했습니다.  로그인 버튼을 눌러 로그인해주세요", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패 했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    // 4. Text 에 변화가 있을 때, 둘 다 null? 이라면 login 과 password 를 비활성화.
    private fun initEmailAndPasswordEditText() {
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // Text가 입력이 될 때마다, 이 Listener 로 event 가 내려오게된다.
        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }

        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable

        }

    }


    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }
}






























