package re.sta.rt.aop_part2_chapter07

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.support.v7.widget.AppCompatImageButton

class RecordButton(
    context : Context,
    attrs : AttributeSet
): AppCompatImageButton(context, attrs){


    init {
        setBackgroundResource(R.drawable.shape_oval_button)
    }

    fun updateIconWithState(state : State) {
        when(state) {
            State.BEFORE_RECORDING -> {
                // 녹음 전,
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                // 녹음 중
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING -> {
                // 녹음 후
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING -> {
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}