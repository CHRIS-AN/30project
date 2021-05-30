package re.sta.rt.aop_part2_chapter07

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager

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
    private val recordButton : RecordButton by lazy { findViewById(R.id.recordButton) }
    private val requiredPermission = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var state = State.BEFORE_RECORDING //초기


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAudioPermission()
        initViews()
    }

    // 요청한 권한에대해서 결과값을 받는다.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 권한이 부여가 됐다면, 그냥 그대로있고? 만약 권한이 거절당하면 앱종료!

        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        // 만약 recordPermission 권한이 부여가[ 되지 않았다면?
        if(!audioRecordPermissionGranted) {
            finish()
        }
    }


    // 권한이 가게끔 만들기.
    private fun requestAudioPermission() {
        requestPermissions(requiredPermission, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initViews() {
        recordButton.updateIconWithState(state) // 현재상태를 전달해주기 위함.
    }

    companion object {
       private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}