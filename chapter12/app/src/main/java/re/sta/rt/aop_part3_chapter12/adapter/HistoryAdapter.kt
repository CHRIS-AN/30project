package re.sta.rt.aop_part3_chapter12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import re.sta.rt.aop_part3_chapter12.databinding.ItemHistoryBinding
import re.sta.rt.aop_part3_chapter12.model.History

class HistoryAdapter(val historyDeleteClickedListener : (String) -> Unit) : ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil) {


    // ViewBinding 을 활성화를 시켜야한다.
    inner class HistoryItemViewHolder(private val binding : ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(historyModel : History) {
            binding.historyKeywordTextView.text = historyModel.keyword

            binding.historyKeywordDeleteButton.setOnClickListener {

                historyDeleteClickedListener(historyModel.keyword.orEmpty())
            }
        }
    }
    // 미리 만들어진 뷰 홀더가 없을 경우에, 새로 생성하는 함수
    // * View 에도 context 가 있으니, View에서 context를 꺼낸다 -> (from() 인자)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 실제로 이 뷰 홀더가 뷰에 그려지게 됐을 때, 데이터를 바인딩하게 되는 함수
    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<History>() {

            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {

                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.keyword == newItem.keyword
            }

        }
    }
}