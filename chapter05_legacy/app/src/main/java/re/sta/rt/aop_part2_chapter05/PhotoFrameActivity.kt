package re.sta.rt.aop_part2_chapter05

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)
        getPhotoUriFromIntent()
        startTimer()
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
        timer(period = 5 * 1000)  {
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
}