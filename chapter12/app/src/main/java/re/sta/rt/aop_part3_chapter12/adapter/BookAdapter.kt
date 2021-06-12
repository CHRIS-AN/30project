package re.sta.rt.aop_part3_chapter12.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import re.sta.rt.aop_part3_chapter12.databinding.ItemBookBinding
import re.sta.rt.aop_part3_chapter12.model.Book


// ListAdapter 를 상속 받는다.

class BookAdapter : ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {


    // ViewBinding 을 활성화를 시켜야한다.
    inner class BookItemViewHolder(private val binding : ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(bookModel :Book) {
            // 뷰를 바인드 시킨다.
            binding.titleTextView.text = bookModel.title
        }
    }
    // 미리 만들어진 뷰 홀더가 없을 경우에, 새로 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {

    }

    // 실제로 이 뷰 홀더가 뷰에 그려지게 됐을 때, 데이터를 바인딩하게 되는 함수
    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}