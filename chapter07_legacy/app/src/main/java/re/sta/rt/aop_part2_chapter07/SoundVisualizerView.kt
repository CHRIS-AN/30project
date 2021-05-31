package re.sta.rt.aop_part2_chapter07

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SoundVisualizerView(
    context: Context,
    attrs : AttributeSet? = null
) : View(context, attrs) {

    var onRequestCurrentAmplitude : (()->Int)? = null

    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = context.getColor(R.color.purple_500)
            strokeWidth = LINE_WIDTH
            strokeCap = Paint.Cap.ROUND
        }
    private var drawingWidth : Int = 0
    private var drawingHeight : Int = 0
    private var drawingAmplitudes : List<Int> = emptyList()



    // animation
    private val visualizeRepeatAction : Runnable = object : Runnable {
        override fun run() {
            // Amplitude , Draw
            val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
            // 들어온 순서 마지막이 첫번째가 되게끔.
            drawingAmplitudes = listOf(currentAmplitude) + drawingAmplitudes // 순서가 시간에 맞춰서 뽑힌다.
            invalidate() // 이것을 추가를 안해주면, 데이터는 계속 추가가되는데 drawing이 안될 것이다.
            handler?.postDelayed(this, ACTION_INTERVAL)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        // 뷰의 센터를 (Y값센터) 지정
        val centerY = drawingHeight / 2f
        // 시작포인트 잡기 (오른쪽부터 하기)
        var offsetX = drawingWidth.toFloat()

        drawingAmplitudes.forEach { amplitude ->
            val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8F
            offsetX -= LINE_SPACE

            // 굉장히 많은 amplitude 가 굉장히 많을 때, 화면에서 사라지고 다시 오른쪽에서 나오게끔
            if(offsetX < 0) return@forEach

            canvas.drawLine(
                offsetX,
                centerY - lineLength / 2F,
                offsetX,
                centerY + lineLength / 2F,
                amplitudePaint
            )
        }
    }

    fun startVisualizing () {
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing () {
        handler?.removeCallbacks(visualizeRepeatAction)
    }

    companion object {
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    }
}