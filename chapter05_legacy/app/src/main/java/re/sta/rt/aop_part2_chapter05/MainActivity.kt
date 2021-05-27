package re.sta.rt.aop_part2_chapter05

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val addPhotoButton : Button by lazy {
        findViewById<Button>(R.id.addPhotoButton)
    }

    private val startPhotoFrameModeButton : Button by lazy {
        findViewById<Button>(R.id.startPhotoFrameModeButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton(){
        addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission (
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // TODO 권한이 잘 부여되었을 때, 갤러리에서 사진을 선택하는 기능
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //  교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        // 알럿다이얼로그,
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }.setNegativeButton("Deny") { _, _ -> }
            .create()
            .show()

    }

    private fun initStartPhotoFrameModeButton() {

    }


}