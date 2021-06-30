package re.sta.rt.aop_part3_chapter15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


/*

    Naver Map API
    ViewPager2
    FrameLayout
    CoordinatorLayout
    BottomSheetBehavior
    Retrofit
    Glide

에어비앤비

설명 : Naver Map API 를 이용해서 지도를 띄우고 활용할 수 있음.
    Mock API 에서 예약가능 숙소 목록을 받아와서 지도에 표시할 수 있음.
    BottomSheetView 를 활용해서 예약 가능 숙소 목록을 인터렉션하게 표시할 수 있음
    ViewPager2 를 활용해서 현재 보고 있는 숙소를 표시할 수 있음.
    숙소버튼을 눌러 현재 보고 있는 숙소를 앱 외부로 공유할 수 있음.
 */

// TEST

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}