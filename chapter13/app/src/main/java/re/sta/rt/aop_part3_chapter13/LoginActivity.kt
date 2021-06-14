package re.sta.rt.aop_part3_chapter13

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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

        val emailEditThread = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        // login 버튼을 눌렀을 때, event
        initLoginButton()
        // 회원가입 버튼을 눌렀을 때, event
        initSignUpButton()
    }



    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

            // 1. 사용자가 직접 친, email 과 password 를 가져온다.
            val email =  getInputEmail()
            val password = getInputPassword()
        }
    }

    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {

            // 1. 사용자가 직접 친, email 과 password 를 가져온다.
            val email =  getInputEmail()
            val password = getInputPassword()

        }
    }

    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }
}






























