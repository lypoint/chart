package axl.com.trendchart

import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.chart_header.view.*

class ChartHeaderHolder(itemView: View) : ChartHolder(itemView) {
    init {
        itemView.gridlayout.columnCount = Contants.columnCount
        itemView.gridlayout.rowCount = 1
        var params = itemView.gridlayout.layoutParams
        if (params != null && params is LinearLayout.LayoutParams) {
            params.width = Contants.chartWidth.toInt()
            params.rightMargin = (Contants.rightWidth + Utils.convertDpToPixel(Contants.chartPaddingRightDp) - Contants.columnWidth / 2).toInt()
            params.leftMargin = (Contants.rightWidth - params.rightMargin).toInt()
            itemView.gridlayout.layoutParams = params
        }
        var i = 0
        while (i < Contants.columnCount) {
            i++
            var textView = TextView(itemView.context)
            textView.gravity = Gravity.CENTER
            textView.text = "第" + i + "天"
            textView.textSize = 12f
            textView.setTextColor(itemView.resources.getColor(R.color.color_3))

            var spec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1.0f)
            var lp = GridLayout.LayoutParams(spec, spec)
            itemView.gridlayout.addView(textView, lp)
        }
    }

    override fun loadData() {

    }
}