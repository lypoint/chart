package axl.com.trendchart.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.Log
import axl.com.trendchart.XAXisModel

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

//自定义X轴显示
class MyXAxisRenderer(var xAxisData: HashMap<String, XAXisModel>, viewPortHandler: ViewPortHandler, xAxis: XAxis, trans: Transformer) : XAxisRenderer(viewPortHandler, xAxis, trans) {
    private val selectBottom = 14
    private val unSelectBottom = 15
    //可绘制的总体高度
    private val xAxisHeight = Utils.convertDpToPixel(45f)
    //是否漂浮
    private var isFloat = false
    override fun drawLabels(c: Canvas, pos: Float, anchor: MPPointF) {
        val labelRotationAngleDegrees = mXAxis.labelRotationAngle
        val centeringEnabled = mXAxis.isCenterAxisLabelsEnabled

        val positions = FloatArray(mXAxis.mEntryCount * 2)

        run {
            var i = 0
            while (i < positions.size) {
                // only fill x values
                if (centeringEnabled) {
                    positions[i] = mXAxis.mCenteredEntries[i / 2]
                } else {
                    positions[i] = mXAxis.mEntries[i / 2]
                }
                i += 2
            }
        }

        mTrans.pointValuesToPixel(positions)

        var i = 0
        while (i < positions.size) {

            var x = positions[i]

            if (mViewPortHandler.isInBoundsX(x)) {

                val label = mXAxis.valueFormatter.getFormattedValue(mXAxis.mEntries[i / 2], mXAxis)

                if (mXAxis.isAvoidFirstLastClippingEnabled) {

                    // avoid clipping of the last
                    if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()

                        if (width > mViewPortHandler.offsetRight() * 2 && x + width > mViewPortHandler.chartWidth) x -= width / 2
                        // avoid clipping of the first
                    } else if (i == 0) {
                        val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()
                        x += width / 2
                    }
                }
                drawXAxisValue(c, label, x, pos, mAxisLabelPaint, anchor, labelRotationAngleDegrees)
            }
            i += 2
        }
    }


    fun drawXAxisValue(c: Canvas, text: String, x: Float, y: Float, paint: Paint, anchor: MPPointF, angleDegrees: Float) {

        var valueText = xAxisData[text]
        if (valueText != null) {
            var drawOffsetX = 0f
            var drawOffsetY = 0f
            drawOffsetX -= mDrawTextRectBuffer.left.toFloat()

            drawOffsetY += -mFontMetricsBuffer.ascent

            val originalTextAlign = paint.textAlign
            paint.textAlign = Paint.Align.LEFT

            if (!TextUtils.isEmpty(valueText.month)) { //带月的显示规则
                if (valueText.isSelected) {
                    paint.color = Color.parseColor("#ffffff")
                    drawText(c, paint, valueText.month, 9f, anchor, drawOffsetX, drawOffsetY, x, 31f)
                    drawText(c, paint, valueText.day, 14f, anchor, drawOffsetX, drawOffsetY, x, unSelectBottom.toFloat())
                } else {
                    paint.color = Color.parseColor("#D0C3DB")
                    drawText(c, paint, valueText.month, 9f, anchor, drawOffsetX, drawOffsetY, x, 31f)
                    drawText(c, paint, valueText.day, 11f, anchor, drawOffsetX, drawOffsetY, x, unSelectBottom.toFloat())
                }
            } else if (valueText.isSelected) { //被选中
                paint.color = Color.parseColor("#ffffff")
                paint.textSize = Utils.convertDpToPixel(14f)
                paint.getTextBounds(text, 0, text.length, mDrawTextRectBuffer)
                if (anchor.x != 0f || anchor.y != 0f) {
                    drawOffsetX -= mDrawTextRectBuffer.width() * anchor.x
                }
                drawOffsetX += x
                drawOffsetY = xAxisHeight - Utils.convertDpToPixel(selectBottom.toFloat()) //距离顶部11个dp
                c.drawText(valueText.day, drawOffsetX, drawOffsetY, paint)
            } else { //未被选中
                paint.color = Color.parseColor("#D0C3DB")
                drawText(c, paint, valueText.day, 11f, anchor, drawOffsetX, drawOffsetY, x, unSelectBottom.toFloat())
            }
            paint.strokeWidth = Utils.convertDpToPixel(1f)
            c.drawLine(x, xAxisHeight - Utils.convertDpToPixel(2f), x, xAxisHeight - Utils.convertDpToPixel(10f), paint)
            paint.textAlign = originalTextAlign
        }
    }

    private fun drawText(c: Canvas, paint: Paint, text: String, textsize: Float, anchor: MPPointF, drawOffsetX: Float, drawOffsetY: Float, x: Float, marginBottom: Float) {
        var drawOffsetX = drawOffsetX
        var drawOffsetY = drawOffsetY
        paint.textSize = Utils.convertDpToPixel(textsize)
        paint.getTextBounds(text, 0, text.length, mDrawTextRectBuffer)
        if (anchor.x != 0f || anchor.y != 0f) {
            drawOffsetX -= mDrawTextRectBuffer.width() * anchor.x
        }
        drawOffsetX += x
        drawOffsetY = xAxisHeight - Utils.convertDpToPixel(marginBottom) //距离顶部11个dp
        c.drawText(text, drawOffsetX, drawOffsetY, paint)
    }

    companion object {
        private val mFontMetricsBuffer = Paint.FontMetrics()
        private val mDrawTextRectBuffer = Rect()
    }
}
