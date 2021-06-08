package re.sta.rt.aop_part3_chapter10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private val viewPager : ViewPager2 by lazy { findViewById(R.id.viewPager)}
    private val progressBar : ProgressBar by lazy { findViewById(R.id.progressBar) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initData()
        initViews()
    }
    // 페이저 전환효과
    private fun initViews() {
        // -2 < -1 < 0(실제 device에서 보임) > 1 > 2
        viewPager.setPageTransformer { page, position ->
            when{
                position.absoluteValue >= 1F -> {
                    page.alpha = 0F
                }
                position == 0F -> {
                    page.alpha = 1F
                }
                else -> {
                    page.alpha = 1F - (2 * position.absoluteValue)
                }
            }
        }
    }

    private fun initData() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
        )
        // 리모트가 패치가 완료되는 시점.
        remoteConfig.fetchAndActivate().addOnCompleteListener {

            progressBar.visibility = View.GONE

            if(it.isSuccessful) {
                val quotes = parseQuotesJson(remoteConfig.getString("quotes")); //gson으로 파싱하면 좀 더 쉽다.
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")
                displayQuitesPager(quotes,isNameRevealed)

            }
        }
    }
    private fun parseQuotesJson(json:String): List<Quote> {
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()

        for(index in 0 until jsonArray.length()) {
            val jsonObject =  jsonArray.getJSONObject(index)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }
        return jsonList.map {
            Quote(
               quote = it.getString("quote"),
               name = it.getString("name"))
        }
    }

    private fun displayQuitesPager(quotes: List<Quote>, isNameRevealed: Boolean) {

        val adapter = QuotePagerAdapter(
            quotes = quotes,
            isNameRevealed = isNameRevealed
        )
        viewPager.adapter = adapter
        viewPager.setCurrentItem(adapter.itemCount / 2, false)

    }
}

