package axl.com.trendchart.mychart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import axl.com.trendchart.dip
import axl.com.trendchart.mychart.vo.DataParse
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

@SuppressLint("ViewConstructor")
/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/9/11
 * Time: 下午6:53
 */
class NewHorizontalLineMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val paint: Paint = Paint()


    private var highlight: Highlight? = null

    var viewWidth = 0

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        paint.color = resources.getColor(android.R.color.white)
        paint.strokeWidth = context.dip(0.5f).toFloat()
        canvas?.drawRect(0f, highlight!!.yPx, viewWidth.toFloat(), highlight!!.yPx + context.dip(0.5f).toFloat(), paint)
    }

    fun refreshContent(e: Entry, highlight: Highlight, dataParse: DataParse) {
        super.refreshContent(e, highlight)
        this.highlight = highlight
    }


}