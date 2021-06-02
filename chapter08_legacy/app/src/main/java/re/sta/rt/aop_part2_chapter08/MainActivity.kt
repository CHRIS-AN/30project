package re.sta.rt.aop_part2_chapter08

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}