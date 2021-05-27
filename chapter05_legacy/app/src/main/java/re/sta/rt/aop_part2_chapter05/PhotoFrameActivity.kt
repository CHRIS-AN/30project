package re.sta.rt.aop_part2_chapter05

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    // imageView 2개를 연결
    private val photoImageView : ImageView by lazy{
        findViewById(R.id.photoImageView)
    }
    private val backgroundPhotoImageView : ImageView by lazy{
        findViewById(R.id.backgroundPhotoImageView)
    }
    // 5초에 한 번씩 돌면서, 몇 번째 index까지 돌았는제 position 을 저장하기 위함
    private var currentPosition = 0


    // timer 를 껐다 켰다 (onStop 상태, onCreate 상태를 만들기 위해)
    private var timer : Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()

       // startTimer() 여기서 실행게 된다면, onCreate 에서의 한 번만 실행이 되기 때문에,
        // 만약에 onStop 에서 캡슬을 한다고 했더라도, 다시 onStart 상태가 되었을 때,
        // 다시 starTimer 가 되지 않기 때문에, onStart 쪽으로 이동.
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            //let함수로 null이 아닐 때만 할 수 있게끔
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it)) // 다시 넘어온 string을 uri로 형변환
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 5 * 1000)  {
            runOnUiThread {
                // mainThread로 반환을해주고 mainThread 안에서 작업하기.
                val current = currentPosition

                // nextIndex는 마지막 이미지를 보고있을 때, 다음 이미지를 추가 할 때 ,공간이 없어서
                    // 에러가 날 경우가 있으니, 마지막이미지에서 첫번째 이미지로 돌아갈 수 있게 하기.
                val next = if(photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backgroundPhotoImageView.setImageURI(photoList[current]) // 현재 이미지
                // alpha는 투명도를 의미,
                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next]) // 다음 이미지

                // 애니매이션 주기
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                // 모든 과정이 끝나면?
                currentPosition = next // 다음 사이클을 돌 때, current check
            }

        }
    }
    override fun onStop() {
        super.onStop()

        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()

        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        timer?.cancel() //확인사살.
    }
}


/*

    <안드로이드 lifecycle>

    onCreate 액티비티가 런치가 되었을 때, 첫 번째로 호출되는 함수 (ex.자바의 main)
    onStart
    onResume(앱 이동 시 여기부터 다시 호출.)
    ------------ 여기까지가 사용자가 running이 되고있다고 느낄 수 있다.

    다른 앱으로 이동할 때? onPause 가 호출 되고,
    다시 원 앱으로 이동하게 될 땐, onResume 이 호출된다.

    앱이 더 이상 보지 않을 때, onStop()이 된다.

    더 이상 앱에서 system 에서 사라지게 될 때는 onDestroy

    원 앱을 실행 중에서, 다른 앱을 사용하는 도중에, 다른 앱이 메모리를
    너무 많이 차지하게 되어 메모리가 부족하다면,
    onStop 상태에 있는 앱이 안드로이드에서 App process killed 이라 하게되고
    그럴 경우에는 onCreate 로 다시 들어오게 된다.



 */