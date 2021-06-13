package re.sta.rt.aop_part3_chapter12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import re.sta.rt.aop_part3_chapter12.databinding.ItemBookBinding
import re.sta.rt.aop_part3_chapter12.model.Book


// ListAdapter 를 상속 받는다.

class BookAdapter : ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {


    // ViewBinding 을 활성화를 시켜야한다.
    inner class BookItemViewHolder(private val binding : ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(bookModel :Book) {
            // 뷰를 바인드 시킨다.
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description // 데이터를 추가.


            // 이미지를 인터넷에서 다운받아서 보여주는..(glide 라이브러리)
            // 이렇게 하면,4줄만으로 url 서버에서 url 을 통하여 이미지를 가져올 수 있다.
            // coverImageView 에 이미지가 추가가 된다.
            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)

        }
    }
    // 미리 만들어진 뷰 홀더가 없을 경우에, 새로 생성하는 함수
    // * View 에도 context 가 있으니, View에서 context를 꺼낸다 -> (from() 인자)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 실제로 이 뷰 홀더가 뷰에 그려지게 됐을 때, 데이터를 바인딩하게 되는 함수
    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    // 리사이클 뷰가 실제로 뷰의 포지션이 변경이 되었을 때, 새로운 값을 할당할지 말지를 결정하는 기준이 있는데,
    // 같은 아이템을 올라게 될 때에는, 이미 값이 할당 되었기 때문에 똑같은 값을 할당할 필요가없다.
    // 그것을 결정이나? 판단을 해주는 것이 diffUtil 이다.
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {

            // old 와 new 가 같냐?
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {

                return oldItem == newItem
            }
            // 안에있는 contents 가 같니 ?
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}