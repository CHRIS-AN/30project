package re.sta.rt.aop_part3_chapter10

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotePagerAdapter(
    private val quotes : List<Quote>,
    private val isNameRevealed : Boolean

):RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
        )


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPosition = position % quotes.size

        holder.bind(quotes[actualPosition], isNameRevealed) // 해당 위치의 quote를 옮기기
    }

    override fun getItemCount() = Int.MAX_VALUE
        //quotes.size


    class QuoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView ) {

        private val quoteTextView : TextView = itemView.findViewById(R.id.quoteTextView)
        private val nameTextView : TextView = itemView.findViewById(R.id.nameTextVIew)

        // bind메소드 구현
        @SuppressLint("SetTextI18n")
        fun bind(quote : Quote, isNameRevealed: Boolean) {
            quoteTextView.text = "\"${quote.quote}\"" // 명언을 넣어주기.

            if(isNameRevealed) {
                nameTextView.text = "- ${quote.name}" // 명언 사람 넣어주기
                nameTextView.visibility = View.VISIBLE
            }else {
                nameTextView.visibility = View.GONE
            }

        }
    }

}