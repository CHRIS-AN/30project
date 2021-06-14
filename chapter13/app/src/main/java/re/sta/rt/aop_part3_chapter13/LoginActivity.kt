package re.sta.rt.aop_part3_chapter13

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/*
    Email password 를 받아와서, Firebase 에 전달을 해줌으로 써 추가해줘야한다.

 */

class LoginActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth

    // 6. facebook login
    private lateinit var callbackManager : CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // firebase 에서 auth 를 가져온다.
        auth = Firebase.auth    // getInstance 랑 동일하다가 생각.



        // 6. 정상적으로 초기화를 시킨다.
        callbackManager = CallbackManager.Factory.create()

        // login 버튼을 눌렀을 때, event
        initLoginButton()
        // 회원가입 버튼을 눌렀을 때, event
        initSignUpButton()
        // 4. email null 값 잡기.
        initEmailAndPasswordEditText()
        // 6. facebook login
        initFacebookLoginButton()
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

    // 6. init facebook login
    private fun initFacebookLoginButton() {
        val facebookLoginButton = findViewById<LoginButton>(R.id.facebookLoginButton)

        // 6. 이 퍼미션은 실제로 로그인 버튼을 눌렀을 때, 유저에게 받아올 정보, 어떤 권한을 요청을해서? 페이스북에서 어떤 권한을 가져오겠느냐?
        facebookLoginButton.setPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(callbackManager, object:FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                // 로그인 성공적
                // 로그인의 accessToken 을 가져와서 firebase 에 넘기는 과정.
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                // 페이스북에 로그인한 accessToken 을 넘겨주는 방식으로 로그인을 시도.
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if(task.isSuccessful) {
                            finish() //로그인성공
                        }else {
                             // 페이스북 로그인 실패
                        }
                    }
            }

            override fun onCancel() {
                // 로그인 하다가 취소,
            }

            override fun onError(error: FacebookException?) {
                // error
                Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

        })



    }


    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }


    // 6. onActivityResult 에서 받아온다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode,resultCode, data)
    }
}






























