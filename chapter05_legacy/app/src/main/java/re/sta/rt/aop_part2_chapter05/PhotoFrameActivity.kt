package re.sta.rt.aop_part2_chapter05

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class PhotoFrameActivity : AppCompatActivity() {
    private val photoList = mutableListOf<Uri>()
        override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()
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
}