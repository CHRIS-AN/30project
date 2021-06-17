package re.sta.rt.aop_part3_chapter14.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import re.sta.rt.aop_part3_chapter14.databinding.ItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articleModel: ArticleModel) {

            // data type 바꿔주기
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt)

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price


            // glide
            if(articleModel.imageUrl.isNotEmpty()) {
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .into(binding.thumbnailImageView)
            }

        }
    }

    fun bind(articleModel: ArticleModel) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>() {
            // 현재 노출되고있는 item 과 새로운 item과 같은지? 비교
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
            // 현재 노출하고있는 item들과 새로운 item으로 equals 비교
            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
