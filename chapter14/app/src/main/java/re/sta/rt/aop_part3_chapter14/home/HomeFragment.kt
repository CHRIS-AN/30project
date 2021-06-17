package re.sta.rt.aop_part3_chapter14.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import re.sta.rt.aop_part3_chapter14.R
import re.sta.rt.aop_part3_chapter14.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    // viewbinding
    private var binding : FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 바인딩한 것 변수에 담아주고, 임시로 지역변수로 선언한 이유는,
        // binding 이란 전역변수가 nullable 이기 때문에 사용할 때마다 null을 풀어주야하기 때문.
        val fragmentHomeBinding  = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding



    }
}