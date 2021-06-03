package re.sta.rt.aop_part2_chapter08

import android.graphics.Bitmap
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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
    private val refreshLayout : SwipeRefreshLayout by lazy { findViewById(R.id.refreshLayout)}
    private val progressBar : ContentLoadingProgressBar by lazy { findViewById(R.id.progressBar)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
        bindViews();

    }

    // Back 버튼을 눌렀을 시, 앱에서 나가지 않고, 뒤로가기가 실행되게끔,
    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        }else {
            super.onBackPressed()
        }
    }

    // 뷰를 초기화하는 init 뷰
    private fun initViews() {

        webView.apply {
            webViewClient =  WebViewClient() // 외부웹 브라우저로 가지 않고, 우리가 지정한 웹뷰를 사용함.
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true // 자바스크립트를 사용하겠다
            loadUrl(DEFAULT_URL)
        }
    }

    private fun bindViews() { // 액션이 발생한 뷰(1), 액션아이디 안에는 액션던한 uri?(2) ?(3)

        goHomeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

        // done
        addressBar.setOnEditorActionListener { v, actionId1, event ->

            // http 를 작성하지 않더라도, http가 붙은 뒤 로딩되게 끔 설정.
            if (actionId1 == EditorInfo.IME_ACTION_DONE) {
                val loadingUrl = v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)) {
                    webView.loadUrl(loadingUrl)
                }
                    webView.loadUrl("http://$loadingUrl")

            }// done 한 뒤에, 키보드를 내려야하기 때문에 false를 사용
            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener {
            webView.goBack() // 이전에 있던 history로 돌아갈 수 있다.
        }
        goFowardButton.setOnClickListener {
            webView.goForward()
        }

        refreshLayout.setOnRefreshListener {
            webView.reload()  // 실제 웹뷰를  refresh ( 다시 실행 )

        }
    }

    // refresh가 reload된 이벤트를 받아서 isRefeshing 처리를 해주어야 한다.
    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            // 시작될 때, 프로그레스 바가 생성.
            progressBar.show()
        }

        // 페이지가 로딩이 끝났을 때,
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            refreshLayout.isRefreshing = false
            // 완료 될 때, 프로그레스 바가 사라지기.
            progressBar.hide()

            // 뒤로 갈 수 있을 땐, 뒤로가기가 되고, 뒤로갈게 없다면, 버튼 비활성화
            goBackButton.isEnabled = webView.canGoBack()
            // 앞으로 갈 수......
            goFowardButton.isEnabled = webView.canGoForward()
            // 우리가 입력한 url 과 실제 url 의 괴리가 있으니, 실제 url 을 보여주게끔하기.
            // ex) www.naver.com 하여도 m.naver.com 로 넘어가게끔 하는..?
            addressBar.setText(url)
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            progressBar.progress = newProgress
        }
    }



    companion object {
        private const val DEFAULT_URL = "http://www.google.com"
    }
}