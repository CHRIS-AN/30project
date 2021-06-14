package re.sta.rt.aop_part3_chapter12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import re.sta.rt.aop_part3_chapter12.databinding.ActivityDetailBinding
import re.sta.rt.aop_part3_chapter12.model.Book
import re.sta.rt.aop_part3_chapter12.model.Review

class DetailActivity : AppCompatActivity() {


    // viewBinding
    private lateinit var binding: ActivityDetailBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater) // 이렇게 가져올 수 있다
        setContentView(binding.root)


        // mainActivity 에 있 는 db를 가져와야함
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()

        val model = intent.getParcelableExtra<Book>("bookModel")


        // 클릭 후, 초기화 시키기.
        binding.titleTextView.text = model?.title.orEmpty()
        binding.descriptionTextView.text = model?.description.orEmpty()
        Glide.with(binding.coverImageView.context)
            .load(model?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)


        // view 에 들어왔을 때, Thread 를 열어서 review 를 가져와야한다.
        Thread {
            val review = db.reviewDao().getOneReview(model?.id?.toInt() ?: 0)
            runOnUiThread {
                // db에서 review를 가져와서 있으면 넣어주고 없으면 빈값을 넣어준다.
                binding.reviewEditText.setText(review?.review.orEmpty())
            }
        }.start()


        // 저장하기
        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        model?.id?.toInt() ?: 0,
                        binding.reviewEditText.text.toString())

                )
            }.start()
        }
    }
}