package axl.com.trendchart.mychart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import axl.com.trendchart.dip
import axl.com.trendchart.mychart.vo.DataParse
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/9/11
 * Time: 下午6:53
 */
class NewBottomMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val paint: Paint = Paint()

    private var markerText = ""

    private var highlight: Highlight? = null


    var viewHeight = 0

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paint.color = resources.getColor(android.R.color.white)
        paint.strokeWidth = context.dip(0.5f).toFloat()
//        canvas?.drawRect(-10000f, 0f, 300000f, context.dip(0.5f).toFloat(), paint)
//        canvas?.drawLine(0f, -100000f, context.dip(1f).toFloat(), 100000f, paint)

        paint.strokeWidth = 0f
        paint.color = Color.WHITE
        paint.textSize = 28f
        paint.isAntiAlias = true
        paint.typeface = Typeface.DEFAULT_BOLD //设置字体

        val mText = markerText

        val bounds = Rect()

        paint.getTextBounds(mText, 0, mText.length, bounds)
        paint.color = context.resources.getColor(android.R.color.background_dark)
//        canvas?.drawRect(highlight?.xPx!!, highlight?.yPx!!, 100f, 100f, paint)
        canvas?.drawRect(highlight?.xPx!! - (bounds.width() / 2) - context.dip(8f), viewHeight.toFloat() - context.dip(24f).toFloat(), highlight?.xPx!! + (bounds.width() / 2) + context.dip(8f), viewHeight.toFloat(), paint)

        paint.color = context.resources.getColor(android.R.color.white)
        canvas?.drawText(mText, highlight?.xPx!! - (bounds.width() / 2), viewHeight.toFloat() - ((context.dip(24f).toFloat() - bounds.height()) / 2), paint)


    }

    fun refreshContent(e: Entry, highlight: Highlight, dataParse: DataParse) {
        super.refreshContent(e, highlight)
        this.highlight = highlight
        if (dataParse.kLineDatas.size > highlight.x.toInt() && dataParse.kLineDatas[highlight.x.toInt()].date != null) {
            markerText = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(dataParse.kLineDatas[highlight.x.toInt()].date.toString().toLong()))
        }
    }


}