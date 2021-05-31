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

    val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = context.getColor(R.color.purple_500)
            strokeWidth = LINE_WIDTH
            strokeCap = Paint.Cap.ROUND
        }
    var drawingWidth : Int = 0
    var drawingHeight : Int = 0
    var drawingAmplitudes : List<Int> = (0..10).map { Random.nextInt(Short.MAX_VALUE.toInt()) }

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

    companion object {
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
    }
}