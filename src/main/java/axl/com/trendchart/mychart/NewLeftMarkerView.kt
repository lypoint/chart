package axl.com.trendchart.mychart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import axl.com.trendchart.R
import axl.com.trendchart.dip
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

@SuppressLint("ViewConstructor")
/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/9/11
 * Time: 下午6:53
 */
class NewLeftMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val paint: Paint = Paint()

    private var markerText = ""

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paint.color = resources.getColor(android.R.color.white)
        paint.strokeWidth = context.dip(0.5f).toFloat()
        paint.strokeWidth = 0f
        paint.color = Color.WHITE
        paint.textSize = 28f
        paint.isAntiAlias = true
        paint.typeface = Typeface.DEFAULT_BOLD //设置字体

        val mText = markerText

        val bounds = Rect()

        paint.getTextBounds(mText, 0, mText.length, bounds)
        paint.color = context.resources.getColor(android.R.color.background_dark)
        canvas?.drawRect(0f, bounds.height().times(1f).times(-1), bounds.width().times(1.5).toFloat(), bounds.height().times(1f), paint)

        paint.color = context.resources.getColor(android.R.color.white)
        canvas?.drawText(mText, bounds.width().times(0.25f), bounds.height().times(0.5f), paint) //画出进度百分比

    }


    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        markerText = highlight?.y.toString()
    }


}