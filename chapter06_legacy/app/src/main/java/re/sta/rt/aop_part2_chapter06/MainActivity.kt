package re.sta.rt.aop_part2_chapter06

import android.annotation.SuppressLint
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView


/*
    SoundPool 이란?

        오디오파일을 메모리에 로드를 한 뒤에, 비교적 빠르게 재생을 할 수 있게 도와줌
        긴 것은 힘들고, 짧은 것만 가능하게끔? 제약이 되어있다 !
 */
class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }
    private val soundPool = SoundPool.Builder().build()


    private var currentCountDownTimer : CountDownTimer? = null

    private var tickingSoundId : Int? = null
    private var bellSoundId : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        // 로드하는 작업
        initSounds()
    }

    // 앱이 보이지 않았다가, 다시 앱으로 돌아올 경우,
    override fun onResume() {
        super.onResume()

        soundPool.autoResume()
    }


    // 앱이 화면에서 보이지 않을 경우,
    override fun onPause() {
        super.onPause()

        soundPool.autoPause()
    }

    // 사운드파일들을 메모리에 올려놓는다면, 비용이 높다(메모리차지가 높다)
    // 따라서, 되도록이면 하지 않을 시에, 메모리에 서 해지를 시켜주어야 한다.
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun initSounds() {
        // 두 개의 파일들을 로드하기 위한 메소드
        // 이렇게하면 로드된 파일 반환하게 된다 ↓
       tickingSoundId =  soundPool.load(this, R.raw.timer_ticking, 1) // 값 1을 넣는 건 호환성이 좋다고 하여 그렇게 함.
       bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun bindViews() {
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
               // @SuppressLint("SetTextI18n")
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // 사용자가 했건 안했건 상관하지 않고, progress
                    //remainMinutesTextView.text = "%02d".format(progress)

                   if(fromUser) {
                    updateRemainTime(progress * 60 * 1000L)
                   }

                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // count다운 도중에, 새로운 카운트를 설정을할 경우에를 막기 위함.
                    currentCountDownTimer?.cancel() // 현재 카운트를 stop
                    currentCountDownTimer = null

                }
                // 이 시점에서 바로 countDown 이 시작된다.
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar ?: return

                    startCountDown();
                }
            }
        )
    }

    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            // countdown 생성 하자마자 바로 실행.
            override fun onTick(millisUntilFinished: Long) {

                // 갱신
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun completeCountDown() {
        updateRemainTime(0)
        updateSeekBar(0)

        // 타이머가 다 갔을 시, 타이머소리를 stop 후, 벨소리를 나게한다.
        soundPool.autoPause()
        bellSoundId?.let {
            soundPool.play(it, 1F,1F,0,0,1F)
        }
    }

    private fun startCountDown() {
        // seeKBar 가 nullable 할 때 나오는 에외처리.
        // 이 경우에는 countdowntimer를 시작하지 못하게 return 시킨다.
        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer?.start()

        // 카운트 다운이 시작함과 동시에,
        tickingSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
        }
    }

    
    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis : Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        // seeBar 는 하나하나가 '분'을 나타낸다.
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}