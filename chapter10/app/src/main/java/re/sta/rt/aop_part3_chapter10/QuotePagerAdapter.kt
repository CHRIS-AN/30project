package re.sta.rt.aop_part3_chapter10

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotePagerAdapter(
    val quotes:List<Quote>
):RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
        )


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {

    }

    override fun getItemCount() = quotes.size


    class QuoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView ) {

        private val quoteTextView : TextView = itemView.findViewById(R.id.quoteTextView)
        private val nameTextView : TextView = itemView.findViewById(R.id.nameTextVIew)

        // bind메소드 구현
        fun bind(quote : Quote) {
            quoteTextView.text = quote.quote // 명언을 넣어주기.
            nameTextView.text = quote.name // 명언 사람 넣어주기

        }
    }

}