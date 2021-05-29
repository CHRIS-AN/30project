package re.sta.rt.aop_part2_chapter07

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


/*
    (기능)
    Request runtime permissions (사생활보호 권한)
    CustomView (시각화)
    MediaRecorder (녹음)

    마이크를 통해 음성 녹음 기능
    녹음한 음성을 재생하는 기능
    음성을 시각화하는 기능
 */

/*
    최초로 진입하면 <녹음 전 상태>,
    녹음을 누르면   <녹음 중 상태>,
    녹음을 정지를 누르면 <녹음 후>상태,
    녹음한 내용을 재생 버튼을 클릭하면, <재생 중> 상태

    녹음 전 -> 녹음 중 -> 녹음 후 -> 재생 중

 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}