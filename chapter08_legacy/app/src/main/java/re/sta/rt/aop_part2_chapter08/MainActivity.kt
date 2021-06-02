package re.sta.rt.aop_part2_chapter08

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

/*
    웹브라우저 앱

    기능(4가지)
        - 웹사이트를 불러올 수 있다.
        - 뒤로가기, 앞으로 가기 기능이 있다
        - 홈 버튼을 눌러 처음으로 돌아갈 수 있다.
        - 웹사이트의 로딩 정도를 확인할 수 있다.

     ConstraintLayout : 브라우저 위에 ui 구성
     EditText : 주소창 입력 후, 키보다 완료 버튼 눌렀을 시, 해딩 주소로 이동
     WebView : webpage 탐색
 */

class MainActivity : AppCompatActivity() {

    private val webView : WebView by lazy { findViewById(R.id.webView) }
    private val addressBar : EditText by lazy { findViewById(R.id.addressBar) }
    private val goHomeButton : ImageButton by lazy { findViewById(R.id.goHomeButton) }
    private val goBackButton : ImageButton by lazy { findViewById(R.id.goBackButton) }
    private val goFowardButton : ImageButton by lazy { findViewById(R.id.goForwardButton) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
        bindViews();

    }

    // 뷰를 초기화하는 init 뷰
    private fun initViews() {

        webView.apply {
            webViewClient =  WebViewClient() // 외부웹 브라우저로 가지 않고, 우리가 지정한 웹뷰를 사용함.
            settings.javaScriptEnabled = true // 자바스크립트를 사용하겠다
            loadUrl("http://www.google.com")
        }
    }

    private fun bindViews() { // 액션이 발생한 뷰(1), 액션아이디 안에는 액션던한 uri?(2) ?(3)
        addressBar.setOnEditorActionListener { v, actionId1, event ->
            if(actionId1 == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
            }// done 한 뒤에, 키보드를 내려야하기 때문에 false를 사용
            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener {
            webView.goBack() // 이전에 있던 history로 돌아갈 수 있다.
        }
        goFowardButton.setOnClickListener {
            webView.goForward()
        }

    }
}