package re.sta.rt.aop_part2_chapter05

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val addPhotoButton : Button by lazy {
        findViewById<Button>(R.id.addPhotoButton)
    }

    private val startPhotoFrameModeButton : Button by lazy {
        findViewById<Button>(R.id.startPhotoFrameModeButton)
    }

    // 이미지 뷰 6개를 연결
    private val imageViewList : List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById(R.id.imageView11))
            add(findViewById(R.id.imageView12))
            add(findViewById(R.id.imageView13))
            add(findViewById(R.id.imageView21))
            add(findViewById(R.id.imageView22))
            add(findViewById(R.id.imageView23))

        }
    }

    // Uri 이를 저장 해놓았다가, 다음 activity에 넘겨주어야하니깐, Uri를 저장해놓는 공간
    private val imageUriList : MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton(){
        addPhotoButton.setOnClickListener {
            when {
                // 권한이 잘 부여가 되는 지 확인 !
                ContextCompat.checkSelfPermission (
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 권한이 잘 부여되었을 때, 갤러리에서 사진을 선택하는 기능
                    navigatePhotos()
                }
                 // 부여되어있지 않다면, 교육용 팝업이 필요한지 확인하고?
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //  교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    // 교육용 팝업을 원치 않다면? 권한을 물어봄
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }


    private fun initStartPhotoFrameModeButton() {
        startPhotoFrameModeButton.setOnClickListener {
            val intent = Intent(this, PhotoFrameActivity::class.java)

            // uri를 넘기기 위해, uri 자체를 넘길 수 없으니, string으로 바꿔서 넘겨준다.
            imageUriList.forEachIndexed { index, uri ->
                // 하나하나씩 꺼내온다.
                intent.putExtra("photo$index", uri.toString())
            }
            // list의사이즈를 넘겨주어서 몇 번째 photo index까지 putExtra 를 할 것인지 알려줄지.
            intent.putExtra("photoListSize", imageUriList.size)
            startActivity(intent)
        }
    }












    private fun navigatePhotos() {
        // framework을 통해 사진을 가져오기. (saf)
        val intent = Intent(Intent.ACTION_GET_CONTENT) // contents를 안드로이드 내장에있는 activity를 실행시키게 한다.
        intent.type = "image/*" // 모든 이미지 파일을 가져올 수 있게, 필터링 한다.
        startActivityForResult(intent,2000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 예외처리
        if(resultCode != Activity.RESULT_OK) {
            // 선택을하고 취소를 할 수도있으니?
            return
        }

        when(requestCode) {
            2000 -> {
                val selectedImageUri : Uri? = data?.data
                if (selectedImageUri != null) {
                    // 6개 그림이 넘을 때를 막는 예외처리
                    if (imageUriList.size == 6) {
                        Toast.makeText(this, "이미 사진이 꽉 찼습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    imageUriList.add(selectedImageUri) // 위에서 null check를 해서 not null로 된다.
                    imageViewList[imageUriList.size -1].setImageURI(selectedImageUri)
                }else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }



            }else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 위에서 requestCode를 1000으로 key값을 주었다.
        when(requestCode) {
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // todo 권한이 부여된 것이다.
                    navigatePhotos()
                }
            }else -> {
                Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                // 다른 코드로 정해 놓은 건 없다.
            }
        }

    }


    private fun showPermissionContextPopup() {
        // 알럿다이얼로그, 교육용 팝업에서 권한이 필요한다면, 권한을 물어보는 알럿창.
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }.setNegativeButton("Deny") { _, _ -> }
            .create()
            .show()

    }


}