package axl.com.trendchart.mychart

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleEntry

/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/7/20
 * Time: 下午5:31
 */
class MyBarDataSet(yVals: MutableList<BarEntry>, private val candleEntries: ArrayList<CandleEntry>, label: String) : BarDataSet(yVals, label) {
    override fun getEntryIndex(e: BarEntry?): Int {
        return super.getEntryIndex(e)
    }

    override fun getColor(index: Int): Int {
        return if (candleEntries[index].open < candleEntries[index].close)
            mColors[0]
        else
            mColors[1]
    }
}